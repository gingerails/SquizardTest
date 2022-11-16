package com.example.springTestProj.Controller.CreateQuestionWindows;

import com.example.springTestProj.Entities.MCQuestion;
import com.example.springTestProj.Service.QuestionService.MCQuestionService;
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
@FxmlView("/mcQuestion.fxml")
public class McQuestionController implements ControlDialogBoxes {

    private final UserService userService;
    private final FxWeaver fxWeaver;
    private final MCQuestionService mcQuestionService;
    private Stage stage;

    @FXML
    private Button add;
    @FXML
    private VBox mcQuestionBox;
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
    private TextField choice1;
    @FXML
    private TextField choice2;
    @FXML
    private TextField choice3;
    @FXML
    private TextField choice4;
    @FXML
    private TextField answer;
    @FXML
    private Label error;

    public McQuestionController(UserService userService, FxWeaver fxWeaver, MCQuestionService mcQuestionService) {
        System.out.println("multChoice Controller");
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.mcQuestionService = mcQuestionService;
    }

    @FXML
    public void initialize () {
        this.stage = new Stage();
        stage.setTitle("Add Multiple Choice Question");
        stage.setScene(new Scene(mcQuestionBox));
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
       if (choice1.getText().isBlank() || 
            choice2.getText().isBlank() ||
            choice3.getText().isBlank() ||
            choice4.getText().isBlank() || 
            answer.getText().isBlank() ||
            questionContent.getText().isBlank()){
           System.out.println("SOMETHING WAS LEFT BLANK");
           error.setText("Error: Must fill out each choice, question and answer!");
       } else{
           String question = questionContent.getText();
           String correctAnswer = answer.getText();
           MCQuestion mcQuestion = mcQuestionService.createMCQuestion(question, correctAnswer);
           checkFieldsAndAddQuestion(mcQuestion);
           stage.close();
       }
    }
    
        public void checkFieldsAndAddQuestion(MCQuestion mcQuestion){

        if(!referenceMaterial.getText().isBlank()){
            String refMaterial =  referenceMaterial.getText();
            mcQuestion.setReferenceMaterial(refMaterial);
        }
        if(!referenceSection.getText().isBlank()){
            String refSection =  referenceSection.getText();
            mcQuestion.setTextReferenceSection(refSection);
        }
        if(!instructorComment.getText().isBlank()){
            String comment =  instructorComment.getText();
            mcQuestion.setInstructorComment(comment);
        }
        if(!gradingInstructions.getText().isBlank()){
            String instructions =  gradingInstructions.getText();
            mcQuestion.setGradingInstruction(instructions);
        }
        mcQuestionService.saveQuestionToRepository(mcQuestion);

    }
}
