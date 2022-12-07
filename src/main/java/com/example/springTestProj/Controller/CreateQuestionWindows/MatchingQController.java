package com.example.springTestProj.Controller.CreateQuestionWindows;

import static com.example.springTestProj.Controller.CreateQuestionWindows.EssayQuestionController.path;
import static com.example.springTestProj.Controller.CreateQuestionWindows.EssayQuestionController.pathTo;
import com.example.springTestProj.Controller.QuestionHTMLHelper;
import com.example.springTestProj.Controller.TestMakerController;
import com.example.springTestProj.Entities.QuestionEntities.MatchingQuestion;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.QuestionService.MatchingQService;
import com.example.springTestProj.Service.TestService;
import com.example.springTestProj.Service.UserService;
import java.io.BufferedReader;
import java.io.FileReader;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;


//This class controls the matching question screen and adds it to db
@Component
@FxmlView("/matchingQ.fxml")
public class MatchingQController implements ControlDialogBoxes {
    //initializing Services, FX Weaver, and all interactive GUI items
    private final UserService userService;
    private final TestService testService;
    private final MatchingQService matchingQService;
    private final QuestionHTMLHelper questionHTMLHelper;
    private final TestMakerController testMakerController;
    private final FxWeaver fxWeaver;
    private Stage stage;

    @FXML
    private Button add,addRow;
    @FXML
    private VBox mQuestionBox;
    @FXML
    private TableView<MatchingQuestion> table;
    @FXML
    private TextField termF,answerF,referenceSection,gradingInstructions,referenceMaterial,instructorComment;
    @FXML
    private Label error;
    @FXML private TableColumn<MatchingQuestion, String> Term;
    @FXML private TableColumn<MatchingQuestion, String> correctAnswer;

    public static String path = "src\\main\\resources\\";
    public static String pathTo = "";
    private final ObservableList<MatchingQuestion> data=FXCollections.observableArrayList();

    //constructor
    public MatchingQController(UserService userService, TestService testService, MatchingQService matchingQService, QuestionHTMLHelper questionHTMLHelper, TestMakerController testMakerController, FxWeaver fxWeaver) {
        this.userService = userService;
        this.testService = testService;
        this.matchingQService = matchingQService;
        this.questionHTMLHelper = questionHTMLHelper;
        this.testMakerController = testMakerController;
        this.fxWeaver = fxWeaver;
    }

    //initialize is automatically called and this is where we store button action events
    @FXML
    public void initialize () {
        String cSection="";
        String cClass="";
        int count =0;
        //need to check current section and class
        BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(
					"temp.txt"));
			String line = reader.readLine();
			while (line != null) {
                            
				System.out.println(line);
				// read next line
                                if(count==0)
                                {
                                    cClass=line;
                                }
                                if(count==1)
                                {
                                    cSection=line;
                                }
				line = reader.readLine();
                                count++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        pathTo = path+cClass+"\\" +cSection+"\\";

        //setup for window
        this.stage = new Stage();
        stage.setTitle("Add MatchingQuestion Question");
        stage.setScene(new Scene(mQuestionBox));

        //initialize columns and clears them and refreshes them
        Term.setCellValueFactory(new PropertyValueFactory<>("Term"));
        correctAnswer.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));
        data.clear();
        table.refresh();
        
        //controls the add row button
        this.addRow.setOnAction(actionEvent -> {
            if (termF.getText().isEmpty() || answerF.getText().isEmpty()) {
                error.setText("ERROR: Term and/or Answer is blank");
            } else {
                MatchingQuestion newQuestion = new MatchingQuestion(termF.getText(), answerF.getText());
                data.add(newQuestion);
                table.setItems(data);
            }

        });
        
        //controls the add button
        this.add.setOnAction(actionEvent -> {
            System.out.print("Add question button pressed");

            for (MatchingQuestion question : data){
                createQuestion(question);
            }
            
            // update HTML
            Test currentTest = getCurrentTestSectionInfo();
            String testFile = currentTest.getTestName();
            try {
                addHTML(pathTo + testFile, pathTo + "KEY_" + testFile);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            stage.close();
        });
    }

   // create question adn puts in HTML files. it grabs all textfield data to put in HTML
    public void createQuestion(MatchingQuestion question){
        String termContent = question.getTerm();
        String correctAnswer = question.getCorrectAnswer();
        MatchingQuestion matchingQuestion = matchingQService.createMatchingQuestion(termContent, correctAnswer);
        checkFieldsAndAddQuestion(matchingQuestion);
    }
    
    //grabs field items and adds to database
    public void checkFieldsAndAddQuestion(MatchingQuestion matchingQuestion){

        if(!referenceMaterial.getText().isBlank()){
            String refMaterial =  referenceMaterial.getText();
            matchingQuestion.setReferenceMaterial(refMaterial);
        }
        if(!referenceSection.getText().isBlank()){
            String refSection =  referenceSection.getText();
            matchingQuestion.setTextReferenceSection(refSection);
        }
        if(!instructorComment.getText().isBlank()){
            String comment =  instructorComment.getText();
            matchingQuestion.setInstructorComment(comment);
        }
        if(!gradingInstructions.getText().isBlank()){
            String instructions =  gradingInstructions.getText();
            matchingQuestion.setGradingInstruction(instructions);
        }
        matchingQuestion.setCreatorId(userService.returnCurrentUserID());
        matchingQService.saveQuestionToRepository(matchingQuestion);
        Test currentTest = getCurrentTestSectionInfo();
        testService.addMatchingQuestion(currentTest, matchingQuestion);// also save to test using test service
    }


    /**
     * Get currentTest obj from TestService, which contains a copy of the test being edited
     * @return
     */
    public Test getCurrentTestSectionInfo(){
        Test currentTest = testService.returnThisTest();
        System.out.println(currentTest.getTestUUID());

        return currentTest;
    }

    //shows stage and center window on screen
    @Override
    public void show(Stage thisStage) {
        stage.show();
        this.stage.centerOnScreen();
    }

    //refreshes html
    public void addHTML(String file, String keyFile) throws IOException {
        questionHTMLHelper.updateSections(file, keyFile);
    }


}
