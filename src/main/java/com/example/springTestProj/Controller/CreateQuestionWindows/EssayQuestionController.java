/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.springTestProj.Controller.CreateQuestionWindows;

import com.example.springTestProj.Entities.EQuestion;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.QuestionService.EssayQuestionService;
import com.example.springTestProj.Service.TestService;
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

/**
 * FXML Controller class
 *
 * @author Orames
 */
@Component
@FxmlView("/essayQuestion.fxml")
public class EssayQuestionController implements ControlDialogBoxes {
    private final UserService userService;
    private final TestService testService;
    private final EssayQuestionService essayQuestionService;

    private final FxWeaver fxWeaver;
    private Stage stage;
    @FXML
    private Button add;
    @FXML
    private Button answerGraphicButton;
    @FXML
    private Button questionGraphicButton;
    @FXML
    private VBox essayQuestionBox;
    @FXML
    private TextField answerTextField;
    @FXML
    private TextField sectionsTextField;
    @FXML
    private TextField MaterialTextField;
    @FXML
    private TextField questionTextField;
    @FXML
    private TextField commentTextField;
    @FXML
    private TextField instructionTextField;
    @FXML
    private Label error;
    
   
    public EssayQuestionController(UserService userService, TestService testService, EssayQuestionService essayQuestionService, FxWeaver fxWeaver) {
        this.testService = testService;
        this.essayQuestionService = essayQuestionService;
        this.fxWeaver = fxWeaver;
        this.userService = userService;
    }

    @FXML
    public void initialize () {
        this.stage = new Stage();
        stage.setTitle("Add Essay Question");
        stage.setScene(new Scene(essayQuestionBox));
        this.add.setOnAction(actionEvent -> {
            System.out.print("Add question button pressed");
            add();
        });
        this.answerGraphicButton.setOnAction(actionEvent -> {
            System.out.print("Add graphic button pushed");
        });
        this.questionGraphicButton.setOnAction(actionEvent -> {
            System.out.print("Add graphic button pushed");
        });

    }

    public void add(){
        if(!questionTextField.getText().isBlank() && !answerTextField.getText().isBlank()){
            String question = questionTextField.getText();
            String answer = answerTextField.getText();
            EQuestion eQuestion = essayQuestionService.createEssayQuestion(question, answer);
            checkFieldsAndAddQuestion(eQuestion);
            stage.close();
        }else{
            error.setText("Error: Must have question and answer!");
        }

    }

    /**
     *
     * @param eQuestion
     */
    public void checkFieldsAndAddQuestion(EQuestion eQuestion){

        if(!MaterialTextField.getText().isBlank()){
            String refMaterial =  MaterialTextField.getText();
            eQuestion.setReferenceMaterial(refMaterial);
        }
        if(!sectionsTextField.getText().isBlank()){
            String refSection =  sectionsTextField.getText();
            eQuestion.setTextReferenceSection(refSection);
        }
        if(!commentTextField.getText().isBlank()){
            String comment =  commentTextField.getText();
            eQuestion.setInstructorComment(comment);
        }
        if(!instructionTextField.getText().isBlank()){
            String instructions =  instructionTextField.getText();
            eQuestion.setGradingInstruction(instructions);
        }

        essayQuestionService.saveQuestionToRepository(eQuestion);
        Test currentTest = getCurrentTestSectionInfo();
        testService.addEQuestion(currentTest, eQuestion);// also save to test using test service
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
//        this.stage = thisStage;
//        stage.setScene(new Scene(mainVbox));
//        System.out.println("Showing essay question screen");
        stage.show();
        this.stage.centerOnScreen();
    }

}
