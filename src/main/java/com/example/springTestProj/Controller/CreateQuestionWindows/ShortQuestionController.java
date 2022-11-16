package com.example.springTestProj.Controller.CreateQuestionWindows;

import com.example.springTestProj.Entities.QuestionEntities.ShortAnswerQuestion;
import com.example.springTestProj.Service.QuestionService.ShortAnswerQService;
import com.example.springTestProj.Service.UserService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/shortAnswerQ.fxml")
public class ShortQuestionController implements ControlDialogBoxes {

    private final UserService userService;
    private final FxWeaver fxWeaver;
    private final ShortAnswerQService shortAnswerQService;
    private Stage stage;

    @FXML
    private Button add;
    @FXML
    private TextField questionField;
    @FXML
    private VBox shortQuestionBox;
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

    public ShortQuestionController(UserService userService, FxWeaver fxWeaver, ShortAnswerQService shortAnswerQService) {
        System.out.println("Short Answer Controller");
    public String path="src\\main\\resources\\";
    public ShortQuestionController(UserService userService, FxWeaver fxWeaver) {
        System.out.println("Short Controller");
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.shortAnswerQService = shortAnswerQService;
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
            stage.close();
            add(path+"test.html");
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
public void add(String file) {
        try ( FileWriter f = new FileWriter(file, true);  BufferedWriter b = new BufferedWriter(f);  PrintWriter p = new PrintWriter(b);) {

    public void add() {
        // gets the current stage, sets the scene w the create account control/view (fxweaver), then updates stage w that scene
        System.out.println("Add PRSSEDDDD");
       if (answer.getText().isBlank() || questionContent.getText().isBlank()){
           System.out.println("SOMETHING WAS LEFT BLANK");
           error.setText("Error: Must fill out each choice, question and answer!");
       } else{
           String question = questionContent.getText();
           String correctAnswer = answer.getText();
           ShortAnswerQuestion shortAnswerQuestion = shortAnswerQService.createSQuestion(question, correctAnswer);
           checkFieldsAndAddQuestion(shortAnswerQuestion);
           stage.close();
       }
    }

        public void checkFieldsAndAddQuestion(ShortAnswerQuestion shortAnswerQuestion){

        if(!referenceMaterial.getText().isBlank()){
            String refMaterial =  referenceMaterial.getText();
            shortAnswerQuestion.setReferenceMaterial(refMaterial);
        }
        if(!referenceSection.getText().isBlank()){
            String refSection =  referenceSection.getText();
            shortAnswerQuestion.setTextReferenceSection(refSection);
        }
        if(!instructorComment.getText().isBlank()){
            String comment =  instructorComment.getText();
            shortAnswerQuestion.setInstructorComment(comment);
        }
        if(!gradingInstructions.getText().isBlank()){
            String instructions =  gradingInstructions.getText();
            shortAnswerQuestion.setGradingInstruction(instructions);
        }
        shortAnswerQService.saveQuestionToRepository(shortAnswerQuestion);

            p.println("<hr />" + "\n"
                    + "<p><strong>Short Answer: " + questionField.getText() + "</strong></p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n");
            b.close();
            p.close();
            f.close();
            //engine.reload();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
