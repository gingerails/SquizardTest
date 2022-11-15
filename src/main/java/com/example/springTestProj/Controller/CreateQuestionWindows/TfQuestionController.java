package com.example.springTestProj.Controller.CreateQuestionWindows;

import com.example.springTestProj.Entities.TFQuestion;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.QuestionService.TFQuestionService;
import com.example.springTestProj.Service.TestService;
import com.example.springTestProj.Service.UserService;
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

import java.util.concurrent.Callable;

@Component
@FxmlView("/tfQuestion.fxml")
public class TfQuestionController implements ControlDialogBoxes {

    private final UserService userService;
    private final FxWeaver fxWeaver;
    private final TFQuestionService tfQuestionService;
    private final TestService testService;
    private Stage stage;

    @FXML
    private Button add;
    @FXML
    private VBox tfQuestionBox;
    @FXML
    private Button addQuestionGraphic;
    @FXML
    private Button addAnswerGraphic;
    @FXML
    private TextField referenceSection;
    @FXML
    private TextField questionContent;
    @FXML
    private TextField referenceMaterial;
    @FXML
    private TextField instructorComment;
    @FXML
    private TextField gradingInstructions;
    @FXML
    private CheckBox isTrueCheckBox;
    @FXML
    private CheckBox isFalseCheckBox;
    @FXML
    private Label error;


    public TfQuestionController(UserService userService, FxWeaver fxWeaver, TFQuestionService tfQuestionService, TestService testService) {
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.tfQuestionService = tfQuestionService;
        this.testService = testService;
    }

    @FXML
    public void initialize () {
        this.stage = new Stage();
        stage.setTitle("Add T/F Question");
        stage.setScene(new Scene(tfQuestionBox));
        this.add.setOnAction(actionEvent -> {
            System.out.print("Add question button pressed");
            add();
        });

        this.addAnswerGraphic.setOnAction(actionEvent -> {
            System.out.print("Add graphic button pushed");
        });

    }


    @Override
    public void show(Stage thisStage) {
        stage.show();
        this.stage.centerOnScreen();
    }


    public void add() {

        System.out.println("Add PRSSEDDDD");
       if (isFalseCheckBox.isSelected() && !questionContent.getText().isBlank()){
           String question = questionContent.getText();
           String correctAnswer = "False";
           TFQuestion tfQuestion = tfQuestionService.createTFQuestion(question, correctAnswer);
           checkFieldsAndAddQuestion(tfQuestion);
           stage.close();
       } else if (isTrueCheckBox.isSelected()&& !questionContent.getText().isBlank()) {
           String question = questionContent.getText();
           String correctAnswer = "True";
           TFQuestion tfQuestion = tfQuestionService.createTFQuestion(question, correctAnswer);
           checkFieldsAndAddQuestion(tfQuestion);
           stage.close();
       }
       else{
           System.out.println("No button sELKECTEDDDD");
           error.setText("Error: Must select true or false!");
       }
    }

    public void checkFieldsAndAddQuestion(TFQuestion tfQuestion){

        if(!referenceMaterial.getText().isBlank()){
            String refMaterial =  referenceMaterial.getText();
            tfQuestion.setReferenceMaterial(refMaterial);
        }
        if(!referenceSection.getText().isBlank()){
            String refSection =  referenceSection.getText();
            tfQuestion.setTextReferenceSection(refSection);
        }
        if(!instructorComment.getText().isBlank()){
            String comment =  instructorComment.getText();
            tfQuestion.setInstructorComment(comment);
        }
        if(!gradingInstructions.getText().isBlank()){
            String instructions =  gradingInstructions.getText();
            tfQuestion.setGradingInstruction(instructions);
        }
        tfQuestionService.saveQuestionToRepository(tfQuestion);

        Test currentTest = getCurrentTestSectionInfo();
       testService.addTFQuestion(currentTest, tfQuestion);// also save to test using test service

       // testService.addQuestionToExistingTestAndSave(currentTest, (Callable) testService.addTFQuestion(currentTest,tfQuestion));// also save to test using test service

       // testService.addTFQuestion();

    }

    // maybe figure out a way to get the current section id. so we can add it to the question
    public Test getCurrentTestSectionInfo(){
        Test currentTest = testService.returnThisTest();
        System.out.println(currentTest.getTestUUID());
        //Test currentTest = new Test();

        return currentTest;
    }

//    public void createTFQuestion(String questionContent, String questionAnswer){
//        TFQuestion tfQuestion = tfQuestionService.createTFQuestion(questionContent, questionAnswer);
//        tfQuestionService.saveQuestionToRepository(tfQuestion);
//    }



}
