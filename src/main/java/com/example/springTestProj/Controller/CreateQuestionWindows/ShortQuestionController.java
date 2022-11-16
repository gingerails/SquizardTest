package com.example.springTestProj.Controller.CreateQuestionWindows;

import com.example.springTestProj.Entities.SQuestion;
import com.example.springTestProj.Service.QuestionService.SQuestionService;
import com.example.springTestProj.Service.UserService;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/sQuestion.fxml")
public class ShortQuestionController implements ControlDialogBoxes {

    private final UserService userService;
    private final FxWeaver fxWeaver;
    private final SQuestionService sQuestionService;
    private Stage stage;

    @FXML
    private Button add;
    @FXML
    private VBox sQuestionBox;
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
    private TextField answer;
    @FXML
    private Label error;

    public ShortQuestionController(UserService userService, FxWeaver fxWeaver, SQuestionService sQuestionService) {
        System.out.println("Short Answer Controller");
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.sQuestionService = sQuestionService;
    }

    @FXML
    public void initialize () {
        this.stage = new Stage();
        stage.setTitle("Add Short Answer Question");
        stage.setScene(new Scene(sQuestionBox));
        this.add.setOnAction(actionEvent -> {
            System.out.print("Add question button pressed");
            //stage.close();
            add();
        });
        
        this.addAnswerGraphic.setOnAction(actionEvent -> {
            System.out.print("Add graphic button pushed");
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
        // gets the current stage, sets the scene w the create account control/view (fxweaver), then updates stage w that scene
        System.out.println("Add PRSSEDDDD");
       if (answer.getText().isBlank() || questionContent.getText().isBlank()){
           System.out.println("SOMETHING WAS LEFT BLANK");
           error.setText("Error: Must fill out each choice, question and answer!");
       } else{
           String question = questionContent.getText();
           String correctAnswer = answer.getText();
           SQuestion sQuestion = sQuestionService.createSQuestion(question, correctAnswer);
           checkFieldsAndAddQuestion(sQuestion);
           stage.close();
       }
    }
    
        public void checkFieldsAndAddQuestion(SQuestion sQuestion){

        if(!referenceMaterial.getText().isBlank()){
            String refMaterial =  referenceMaterial.getText();
            sQuestion.setReferenceMaterial(refMaterial);
        }
        if(!referenceSection.getText().isBlank()){
            String refSection =  referenceSection.getText();
            sQuestion.setTextReferenceSection(refSection);
        }
        if(!instructorComment.getText().isBlank()){
            String comment =  instructorComment.getText();
            sQuestion.setInstructorComment(comment);
        }
        if(!gradingInstructions.getText().isBlank()){
            String instructions =  gradingInstructions.getText();
            sQuestion.setGradingInstruction(instructions);
        }
        sQuestionService.saveQuestionToRepository(sQuestion);

    }
}