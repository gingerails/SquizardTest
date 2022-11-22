package com.example.springTestProj.Controller.CreateQuestionWindows;

import com.example.springTestProj.Entities.QuestionEntities.MatchingQuestion;
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


@Component
@FxmlView("/matchingQ.fxml")
public class MatchingQController implements ControlDialogBoxes {

    private final UserService userService;
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
    private Label error;
    @FXML private TableColumn<MatchingQuestion, String> Term;
    @FXML private TableColumn<MatchingQuestion, String> Answer;

    public String path="src\\main\\resources\\";
    private final ObservableList<MatchingQuestion> data=FXCollections.observableArrayList();

    public MatchingQController(UserService userService, FxWeaver fxWeaver) {
        this.userService = userService;
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize () {
        this.stage = new Stage();
        stage.setTitle("Add MatchingQuestion Question");
        stage.setScene(new Scene(mQuestionBox));

        Term.setCellValueFactory(new PropertyValueFactory<>("Term"));
        Answer.setCellValueFactory(new PropertyValueFactory<>("Answer"));
      
        this.addRow.setOnAction(actionEvent -> {
            if (termF.getText().isEmpty() || answerF.getText().isEmpty()) {
                error.setText("ERROR: Term and/or Answer is blank");
            } else {

               // data.add(new MatchingQuestion(termF.getText(), answerF.getText()));
                //table.setItems(data);
            }

        });
        this.add.setOnAction(actionEvent -> {
            System.out.print("Add question button pressed");
            stage.close();
            //add(path+"test.html");
        });
    }

    public void createQuestion(){
        if (termF.getText().isEmpty() || answerF.getText().isEmpty()) {
            error.setText("ERROR: Term and/or Answer is blank");
        } else {
            String termContent = termF.getText();
            String correctAnsweeer = answerF.getText();

         //   MatchingQuestion matchingQuestion = matching
        }
    }

    @Override
    public void show(Stage thisStage) {
        stage.show();
        this.stage.centerOnScreen();
    }
    

}
