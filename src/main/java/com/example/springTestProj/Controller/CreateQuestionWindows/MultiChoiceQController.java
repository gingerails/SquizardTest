package com.example.springTestProj.Controller.CreateQuestionWindows;

import com.example.springTestProj.Entities.Question;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.QuestionService;
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

import java.util.Arrays;

@Component
@FxmlView("/multiChoiceQ.fxml")
public class MultiChoiceQController implements ControlDialogBoxes {

    private final UserService userService;
    private final FxWeaver fxWeaver;
    private final TestService testService;
    private final QuestionService questionService;
    private Stage stage;

    @FXML
    private Button add;
    @FXML
    private VBox mcQuestionBox;
    @FXML
    private Button addAnswerGraphic;
    @FXML
    private Button addQuestionGraphicButton;
    @FXML
    private TextField referenceSection;
    @FXML
    private TextField questionContentText;
    @FXML
    private TextField referenceMaterial;
    @FXML
    private TextField instructorComment;
    @FXML
    private TextField gradingInstructions;
    @FXML
    private TextField choice1Field;
    @FXML
    private TextField choice2Field;
    @FXML
    private TextField choice3Field;
    @FXML
    private TextField choice4Field;
    @FXML
    private TextField answerTextField;
    @FXML
    private Label error;

    public MultiChoiceQController(UserService userService, FxWeaver fxWeaver, TestService testService, QuestionService questionService) {
        this.testService = testService;
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.questionService = questionService;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setTitle("Add Multiple Choice Question");
        stage.setScene(new Scene(mcQuestionBox));
        this.add.setOnAction(actionEvent -> {
            System.out.print("Add question button pressed");
            //stage.close();
            createQuestion();
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


    public void createQuestion() {
        // gets the current stage, sets the scene w the create account control/view (fxweaver), then updates stage w that scene
        System.out.println("Add PRSSEDDDD");
        if (choice1Field.getText()
                .isBlank() ||
                choice2Field.getText()
                        .isBlank() ||
                choice3Field.getText()
                        .isBlank() ||
                choice4Field.getText()
                        .isBlank() ||
                answerTextField.getText()
                        .isBlank() ||
                questionContentText.getText()
                        .isBlank()) {
            System.out.println("SOMETHING WAS LEFT BLANK");
            error.setText("Error: Must fill out each choice, question and answer!");
        } else {
            String questionContent = questionContentText.getText();
            String correctAnswer = answerTextField.getText();
            String[] falseAnswers = {choice1Field.getText(), choice2Field.getText(), choice3Field.getText(), choice4Field.getText()};
            String falseAnswer = Arrays.toString(falseAnswers);

            Question question = questionService.createQuestionWithFalseAnswer(questionContent, correctAnswer, falseAnswer);
            checkFieldsAndAddQuestion(question);
            stage.close();
        }
    }

    public void checkFieldsAndAddQuestion(Question question) {

        if (!referenceMaterial.getText()
                .isBlank()) {
            String refMaterial = referenceMaterial.getText();
            question.setReferenceMaterial(refMaterial);
        }
        if (!referenceSection.getText()
                .isBlank()) {
            String refSection = referenceSection.getText();
            question.setTextReferenceSection(refSection);
        }
        if (!instructorComment.getText()
                .isBlank()) {
            String comment = instructorComment.getText();
            question.setInstructorComment(comment);
        }
        if (!gradingInstructions.getText()
                .isBlank()) {
            String instructions = gradingInstructions.getText();
            question.setGradingInstruction(instructions);
        }
        questionService.saveQuestionToRepository(question);
        Test currentTest = getCurrentTestSectionInfo();
     //   testService.addQuestion(currentTest, question);// also save to test using test service
    }


    /**
     * Get currentTest obj from TestService, which contains a copy of the test being edited
     *
     * @return
     */
    public Test getCurrentTestSectionInfo() {
        Test currentTest = testService.returnThisTest();
        System.out.println(currentTest.getTestUUID());

        return currentTest;
    }
}
