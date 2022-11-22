package com.example.springTestProj.Controller.CreateQuestionWindows;

import com.example.springTestProj.Controller.TestMakerController;
import com.example.springTestProj.Entities.QuestionEntities.TrueFalseQuestion;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.QuestionService.TrueFalseQService;
import com.example.springTestProj.Service.TestService;
import com.example.springTestProj.Service.UserService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
@FxmlView("/trueFalseQ.fxml")
public class TrueFalseQController implements ControlDialogBoxes {

    private final UserService userService;
    private final FxWeaver fxWeaver;
    private final TrueFalseQService trueFalseQService;
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

    public String path="src\\main\\resources\\";
    public TrueFalseQController(UserService userService, FxWeaver fxWeaver, TrueFalseQService trueFalseQService, TestService testService) {
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.trueFalseQService = trueFalseQService;
        this.testService = testService;
    }

    @FXML
    public void initialize () {
        this.stage = new Stage();
        stage.setTitle("Add T/F Question");
        stage.setScene(new Scene(tfQuestionBox));
        this.add.setOnAction(actionEvent -> {
            System.out.print("Add question button pressed");
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

        System.out.println("Add PRSSEDDDD");
       if (isFalseCheckBox.isSelected() && !questionContent.getText().isBlank()){
           String question = questionContent.getText();
           String correctAnswer = "False";
           TrueFalseQuestion trueFalseQuestion = trueFalseQService.createTFQuestion(question, correctAnswer);
           checkFieldsAndAddQuestion(trueFalseQuestion);
           stage.close();
       } else if (isTrueCheckBox.isSelected()&& !questionContent.getText().isBlank()) {
           String question = questionContent.getText();
           String correctAnswer = "True";
           TrueFalseQuestion trueFalseQuestion = trueFalseQService.createTFQuestion(question, correctAnswer);
           checkFieldsAndAddQuestion(trueFalseQuestion);
           addHTML(path+"test.html");
           stage.close();
       }
       else{
           System.out.println("No button sELKECTEDDDD");
           error.setText("Error: Must select true or false!");
       }
    }

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
     public void addHTML(String file) {
        try ( FileWriter f = new FileWriter(file, true);  BufferedWriter b = new BufferedWriter(f);  PrintWriter p = new PrintWriter(b);) {

            p.println("<hr />" + "\n"
            +"<p><span style='font-size:16px'><strong>T/F: </strong></span></p>"+"\n"
            +"<p>"+questionContent.getText()+" ______</p>"+"\n" 
            );
            b.close();
            p.close();
            f.close();
            TestMakerController.engine.reload();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/testMaker.fxml"));
//            root=loader.load();
//
//            TestMakerController TestMakeController = loader.getController();
//            TestMakeController.initialize();
            //engine.reload();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
