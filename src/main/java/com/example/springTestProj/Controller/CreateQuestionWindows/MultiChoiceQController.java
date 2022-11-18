package com.example.springTestProj.Controller.CreateQuestionWindows;

import com.example.springTestProj.Controller.TestMakerController;
import com.example.springTestProj.Entities.QuestionEntities.MultiChoiceQuestion;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.QuestionService.MultiChoiceQService;
import com.example.springTestProj.Service.TestService;
import com.example.springTestProj.Service.UserService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
    private final MultiChoiceQService multiChoiceQService;
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
    private TextField questionContent;
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

    public String path="src\\main\\resources\\";
    public MultiChoiceQController(UserService userService, FxWeaver fxWeaver, TestService testService, MultiChoiceQService multiChoiceQService) {
        this.testService = testService;
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.multiChoiceQService = multiChoiceQService;
    }

    @FXML
    public void initialize () {
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
       if (choice1Field.getText().isBlank() ||
               choice2Field.getText().isBlank() ||
               choice3Field.getText().isBlank() ||
               choice4Field.getText().isBlank() ||
               answerTextField.getText().isBlank() ||
               questionContent.getText().isBlank()){
           System.out.println("SOMETHING WAS LEFT BLANK");
           error.setText("Error: Must fill out each choice, question and answer!");
       } else{
           String question = questionContent.getText();
           String correctAnswer = answerTextField.getText();
           String[] falseAnswers = {choice1Field.getText(),choice2Field.getText(),choice3Field.getText(),choice4Field.getText()};
           String falseAnswer = Arrays.toString(falseAnswers);

           MultiChoiceQuestion multiChoiceQuestion = multiChoiceQService.createMCQuestion(question, correctAnswer, falseAnswer);
           checkFieldsAndAddQuestion(multiChoiceQuestion);
           addHTML(path+"test.html");
           stage.close();
       }
    }
    
    public void checkFieldsAndAddQuestion(MultiChoiceQuestion multiChoiceQuestion){

        if(!referenceMaterial.getText().isBlank()){
            String refMaterial =  referenceMaterial.getText();
            multiChoiceQuestion.setReferenceMaterial(refMaterial);
        }
        if(!referenceSection.getText().isBlank()){
            String refSection =  referenceSection.getText();
            multiChoiceQuestion.setTextReferenceSection(refSection);
        }
        if(!instructorComment.getText().isBlank()){
            String comment =  instructorComment.getText();
            multiChoiceQuestion.setInstructorComment(comment);
        }
        if(!gradingInstructions.getText().isBlank()){
            String instructions =  gradingInstructions.getText();
            multiChoiceQuestion.setGradingInstruction(instructions);
        }
        multiChoiceQService.saveQuestionToRepository(multiChoiceQuestion);
        Test currentTest = getCurrentTestSectionInfo();
        testService.addMCQuestion(currentTest, multiChoiceQuestion);// also save to test using test service
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
                  +"<p><span style='font-size:16px'><strong>"+questionContent.getText()+"</strong></span></p>"+"\n"

                  +"<p><span style='font-size:16px'>a. "+choice1Field.getText()+"</span></p>"+"\n"
                    
                   +"<p><span style='font-size:16px'>b. "+choice2Field.getText()+"</span></p>"+"\n"
                    
                    +"<p><span style='font-size:16px'>c. "+choice3Field.getText()+"</span></p>"+"\n"
                    
                    +"<p><span style='font-size:16px'>d. "+choice4Field.getText()+"</span></p>"+"\n"
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
