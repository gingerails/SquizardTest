package com.example.springTestProj.Controller.CreateQuestionWindows;

import static com.example.springTestProj.Controller.CreateQuestionWindows.MatchingQController.path;
import static com.example.springTestProj.Controller.CreateQuestionWindows.MatchingQController.pathTo;
import com.example.springTestProj.Controller.QuestionHTMLHelper;
import com.example.springTestProj.Controller.TestMakerController;
import com.example.springTestProj.Entities.QuestionEntities.TrueFalseQuestion;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.QuestionService.TrueFalseQService;
import com.example.springTestProj.Service.TestService;
import com.example.springTestProj.Service.UserService;
import java.io.BufferedReader;
import java.io.FileReader;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

//This class controls the true/false question screen and adds it to db
@Component
@FxmlView("/trueFalseQ.fxml")
public class TrueFalseQController implements ControlDialogBoxes {
    //initializing Services, FX Weaver, and all interactive GUI items
    private final UserService userService;
    private final FxWeaver fxWeaver;
    private final TrueFalseQService trueFalseQService;
    private final QuestionHTMLHelper questionHTMLHelper;
    private final TestService testService;
    private final TestMakerController testMakerController;
    private Stage stage;

    @FXML
    private Button add,addQuestionGraphic,addAnswerGraphic;
    @FXML
    private VBox tfQuestionBox;
    @FXML
    private TextField referenceSection,gradingInstructions,questionContent,referenceMaterial,instructorComment;
    @FXML
    private CheckBox isTrueCheckBox,isFalseCheckBox;
    @FXML
    private Label error;

    public static String path = "src\\main\\resources\\";
    public static String pathTo = "";
    public TrueFalseQController(UserService userService, FxWeaver fxWeaver, TrueFalseQService trueFalseQService, QuestionHTMLHelper questionHTMLHelper, TestService testService, TestMakerController testMakerController) {
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.trueFalseQService = trueFalseQService;
        this.questionHTMLHelper = questionHTMLHelper;
        this.testService = testService;
        this.testMakerController = testMakerController;
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

        //setup window
        this.stage = new Stage();
        stage.setTitle("Add T/F Question");
        stage.setScene(new Scene(tfQuestionBox));
        
        //controls add button
        this.add.setOnAction(actionEvent -> {
            System.out.print("Add question button pressed");
            try {
                createQuestion();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        //controls add graphic
        this.addAnswerGraphic.setOnAction(actionEvent -> {
            System.out.print("Add graphic button pushed");
        });

    }

    //shows and centers window
    @Override
    public void show(Stage thisStage) {
        stage.show();
        this.stage.centerOnScreen();
    }

   // create question adn puts in HTML files. it grabs all textfield data to put in HTML
    public void createQuestion() throws IOException {

        System.out.println("Add PRSSEDDDD");
       if (isFalseCheckBox.isSelected() && !questionContent.getText().isBlank()){
           String question = questionContent.getText();
           String correctAnswer = "False";
           TrueFalseQuestion trueFalseQuestion = trueFalseQService.createTFQuestion(question, correctAnswer);
           checkFieldsAndAddQuestion(trueFalseQuestion);
           Test currentTest = getCurrentTestSectionInfo();
           String testFile = currentTest.getTestName();
           addHTML(path + testFile, path + "KEY_" + testFile);

           testMakerController.refresh();
           stage.close();
       } else if (isTrueCheckBox.isSelected()&& !questionContent.getText().isBlank()) {
           String question = questionContent.getText();
           String correctAnswer = "True";
           TrueFalseQuestion trueFalseQuestion = trueFalseQService.createTFQuestion(question, correctAnswer);
           checkFieldsAndAddQuestion(trueFalseQuestion);
           Test currentTest = getCurrentTestSectionInfo();
           String testFile = currentTest.getTestName();
           addHTML(pathTo + testFile, pathTo + "KEY_" + testFile);

           testMakerController.refresh();
           stage.close();
       }
       else{

           error.setText("Error: Must select true or false!");
       }
    }

    //grabs field items and adds to database
    public void checkFieldsAndAddQuestion(TrueFalseQuestion trueFalseQuestion){

        if(!referenceMaterial.getText().isBlank()){
            String refMaterial =  referenceMaterial.getText();
            trueFalseQuestion.setReferenceMaterial(refMaterial);
        }
        if(!referenceSection.getText().isBlank()){
            String refSection =  referenceSection.getText();
            trueFalseQuestion.setTextReferenceSection(refSection);
        }
        if(!instructorComment.getText().isBlank()){
            String comment =  instructorComment.getText();
            trueFalseQuestion.setInstructorComment(comment);
        }
        if(!gradingInstructions.getText().isBlank()){
            String instructions =  gradingInstructions.getText();
            trueFalseQuestion.setGradingInstruction(instructions);
        }

        trueFalseQuestion.setCreatorId(userService.returnCurrentUserID());
        trueFalseQService.saveQuestionToRepository(trueFalseQuestion);
        Test currentTest = getCurrentTestSectionInfo();
        testService.addTFQuestion(currentTest, trueFalseQuestion);// also save to test using test service
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

    //refreshes html
    public void addHTML(String file, String keyFile) throws IOException {
        questionHTMLHelper.updateSections(file, keyFile);
    }

}
