package com.example.springTestProj.Controller.CreateQuestionWindows;

import com.example.springTestProj.Entities.Question;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.QuestionService;
import com.example.springTestProj.Service.TestService;
import com.example.springTestProj.Service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/matchingQ.fxml")
public class MatchingQController implements ControlDialogBoxes {

    private final UserService userService;
    private final TestService testService;
    private final QuestionService questionService;
    private final FxWeaver fxWeaver;
    public String path = "src\\main\\resources\\";
    private Stage stage;
    @FXML
    private Button add;
    @FXML
    private Button addRow;
    @FXML
    private VBox mQuestionBox;
    @FXML
    private TableView<Question> table;
    @FXML
    private TextField termF;
    @FXML
    private TextField answerF;

    @FXML
    private TextField referenceSection;
    @FXML
    private TextField referenceMaterial;
    @FXML
    private TextField instructorComment;
    @FXML
    private TextField gradingInstructions;
    @FXML
    private Label error;
    @FXML private TableColumn<Question, String> termCol;
    @FXML private TableColumn<Question, String> correctAnswer;
    private final ObservableList<Question> data= FXCollections.observableArrayList();


    public MatchingQController(UserService userService, TestService testService, QuestionService questionService, FxWeaver fxWeaver) {
        this.userService = userService;
        this.testService = testService;
        this.questionService = questionService;
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize () {
        this.stage = new Stage();
        stage.setTitle("Add MatchingQuestion Question");
        stage.setScene(new Scene(mQuestionBox));

        termCol.setCellValueFactory(new PropertyValueFactory<>("termCol"));
        correctAnswer.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));

        this.addRow.setOnAction(actionEvent -> {
            if (termF.getText().isEmpty() || answerF.getText().isEmpty()) {
                error.setText("ERROR: Term and/or Answer is blank");
            } else {
                Question newQuestion = new Question(termF.getText(), answerF.getText());
                data.add(newQuestion);
                table.setItems(data);
            }

        });
        this.add.setOnAction(actionEvent -> {
            System.out.print("Add question button pressed");

            for (Question question : data){
                createQuestion(question);
            }

            stage.close();
            //add(path+"test.html");
        });
    }

    public void createQuestion(Question question){
        String termContent = question.getQuestionContent();
        String correctAnswer = question.getCorrectAnswer();
        Question matchingQuestion = questionService.createQuestion(termContent, correctAnswer);
        checkFieldsAndAddQuestion(matchingQuestion);
    }

    public void checkFieldsAndAddQuestion(Question matchingQuestion){

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
        questionService.saveQuestionToRepository(matchingQuestion);
        Test currentTest = getCurrentTestSectionInfo();
      //  testService.addQuestion(currentTest, matchingQuestion);// also save to test using test service
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


    @Override
    public void show(Stage thisStage) {
        stage.show();
        this.stage.centerOnScreen();
    }


//
//    @Override
//    public <T> void add(T t) {
//
//    }
       /* public void add(String file) {

        // gets the current stage, sets the scene w the create account control/view (fxweaver), then updates stage w that scene

        try ( FileWriter f = new FileWriter(file, true);  BufferedWriter b = new BufferedWriter(f);  PrintWriter p = new PrintWriter(b);) {

            p.println("<hr />" + "\n"
                     +"<p><strong>Match the following terms:</strong></p>"+"\n"

                     +"<p>"+"term"+"&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;"+ "Bananna"+"</p>"+"\n"




            );
            b.close();
            p.close();
            f.close();
            //engine.reload();
        } catch (IOException i) {
            i.printStackTrace();
        }

    }*/
}
