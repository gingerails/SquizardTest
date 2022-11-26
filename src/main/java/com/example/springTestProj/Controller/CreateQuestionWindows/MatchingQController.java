package com.example.springTestProj.Controller.CreateQuestionWindows;

import com.example.springTestProj.Controller.QuestionHTMLHelper;
import com.example.springTestProj.Entities.QuestionEntities.MatchingQuestion;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.QuestionService.MatchingQService;
import com.example.springTestProj.Service.TestService;
import com.example.springTestProj.Service.UserService;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;


@Component
@FxmlView("/matchingQ.fxml")
public class MatchingQController implements ControlDialogBoxes {

    private final UserService userService;
    private final TestService testService;
    private final MatchingQService matchingQService;
    private final QuestionHTMLHelper questionHTMLHelper;
    private final FxWeaver fxWeaver;
    private Stage stage;

    @FXML
    private Button add;
    @FXML
    private Button addRow;
    @FXML
    private VBox mQuestionBox;
    @FXML
    private TableView<MatchingQuestion> table;
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
    @FXML private TableColumn<MatchingQuestion, String> Term;
    @FXML private TableColumn<MatchingQuestion, String> correctAnswer;

    public String path="src\\main\\resources\\generatedTests\\";
    private final ObservableList<MatchingQuestion> data=FXCollections.observableArrayList();

    public MatchingQController(UserService userService, TestService testService, MatchingQService matchingQService, QuestionHTMLHelper questionHTMLHelper, FxWeaver fxWeaver) {
        this.userService = userService;
        this.testService = testService;
        this.matchingQService = matchingQService;
        this.questionHTMLHelper = questionHTMLHelper;
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize () {
        this.stage = new Stage();
        stage.setTitle("Add MatchingQuestion Question");
        stage.setScene(new Scene(mQuestionBox));

        Term.setCellValueFactory(new PropertyValueFactory<>("Term"));
        correctAnswer.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));
        data.clear();
        table.refresh();
        this.addRow.setOnAction(actionEvent -> {
            if (termF.getText().isEmpty() || answerF.getText().isEmpty()) {
                error.setText("ERROR: Term and/or Answer is blank");
            } else {
                MatchingQuestion newQuestion = new MatchingQuestion(termF.getText(), answerF.getText());
                data.add(newQuestion);
                table.setItems(data);
            }

        });
        this.add.setOnAction(actionEvent -> {
            System.out.print("Add question button pressed");

            for (MatchingQuestion question : data){
                createQuestion(question);
            }
            // update HTML
            Test currentTest = getCurrentTestSectionInfo();
            String testFile = currentTest.getTestName();
            try {
                addHTML(path+testFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            stage.close();
        });
    }

    public void createQuestion(MatchingQuestion question){
        String termContent = question.getTerm();
        String correctAnswer = question.getCorrectAnswer();
        MatchingQuestion matchingQuestion = matchingQService.createMatchingQuestion(termContent, correctAnswer);
        checkFieldsAndAddQuestion(matchingQuestion);
    }

    public void checkFieldsAndAddQuestion(MatchingQuestion matchingQuestion){

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
        matchingQService.saveQuestionToRepository(matchingQuestion);
        Test currentTest = getCurrentTestSectionInfo();
        testService.addMatchingQuestion(currentTest, matchingQuestion);// also save to test using test service
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

    public void addHTML(String file) throws IOException {
        questionHTMLHelper.updateSections(file);
    }


}
