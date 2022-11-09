package com.example.springTestProj.Controller.CreateQuestionWindows;

import com.example.springTestProj.Entities.TFQuestion;
import com.example.springTestProj.Service.QuestionService.TFQuestionService;
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

@Component
@FxmlView("/tfQuestion.fxml")
public class TfQuestionController implements ControlDialogBoxes {

    private final UserService userService;
    private final FxWeaver fxWeaver;
    private final TFQuestionService tfQuestionService;
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


    public TfQuestionController(UserService userService, FxWeaver fxWeaver, TFQuestionService tfQuestionService) {
        //System.out.println("essay Controller");
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.tfQuestionService = tfQuestionService;
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
           // add();
           // stage.close();
        });

    }


    @Override
    public void show(Stage thisStage) {
//        this.stage = thisStage;
//        stage.setScene(new Scene(mainVbox));
//        System.out.println("Showing essay question screen");
        stage.show();
        this.stage.centerOnScreen();
    }

//    @Override
//    public <T> void add(T t) {
//
//    }

    public void add() {

        System.out.println("Add PRSSEDDDD");
       if (isFalseCheckBox.isSelected() && !questionContent.getText().isBlank()){
           String question = questionContent.getText();
           String correctAnswer = "False";
           TFQuestion tfQuestion = tfQuestionService.createTFQuestion(question, correctAnswer);
           checkAndAddFields(tfQuestion);
           stage.close();
       } else if (isTrueCheckBox.isSelected()&& !questionContent.getText().isBlank()) {
           String question = questionContent.getText();
           String correctAnswer = "True";
           TFQuestion tfQuestion = tfQuestionService.createTFQuestion(question, correctAnswer);
           checkAndAddFields(tfQuestion);
           stage.close();
       }
       else{
           System.out.println("No button sELKECTEDDDD");
           error.setText("Error: Must select true or false!");
       }
    }

    public void checkAndAddFields(TFQuestion tfQuestion){

        if(!referenceMaterial.getText().isBlank()){
            String refMaterial =  referenceMaterial.getText();
            tfQuestion.setReferenceMaterial(refMaterial);
            //tfQuestionService.addReference();
        }
        if(!referenceSection.getText().isBlank()){
            String refSection =  referenceSection.getText();
            tfQuestion.setReferenceMaterial(refSection);
        }
        if(!instructorComment.getText().isBlank()){
            String comment =  instructorComment.getText();
            tfQuestion.setReferenceMaterial(comment);
        }
        if(!gradingInstructions.getText().isBlank()){
            String instructions =  gradingInstructions.getText();
            tfQuestion.setReferenceMaterial(instructions);
        }
        tfQuestionService.saveQuestionToRepository(tfQuestion);

    }

//    public void createTFQuestion(String questionContent, String questionAnswer){
//        TFQuestion tfQuestion = tfQuestionService.createTFQuestion(questionContent, questionAnswer);
//        tfQuestionService.saveQuestionToRepository(tfQuestion);
//    }



}
