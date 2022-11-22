package com.example.springTestProj.Controller.CreateQuestionWindows;

import com.example.springTestProj.Service.UserService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import static javafx.scene.control.cell.ProgressBarTableCell.forTableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.persistence.Column;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import com.example.springTestProj.Controller.CreateQuestionWindows.Matching;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


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
    private TableView<Matching> table;
    @FXML
    private TextField termF;
    @FXML
    private TextField answerF;
    @FXML
    private Label error;
    @FXML private TableColumn<Matching, String> Term;
    @FXML private TableColumn<Matching, String> Answer;

    public String path="src\\main\\resources\\";
    private final ObservableList<Matching> data=FXCollections.observableArrayList();

    public MatchingQController(UserService userService, FxWeaver fxWeaver) {
        this.userService = userService;
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize () {
        
        
        
        
        
        this.stage = new Stage();
        stage.setTitle("Add Matching Question");
        stage.setScene(new Scene(mQuestionBox));
        
        
      
      Term.setCellValueFactory(new PropertyValueFactory<>("Term"));
      Answer.setCellValueFactory(new PropertyValueFactory<>("Answer"));
      
        this.addRow.setOnAction(actionEvent -> {
            if (termF.getText().isEmpty() || answerF.getText().isEmpty()) {
                error.setText("ERROR: Term and/or Answer is blank");
            } else {
                data.add(new Matching(termF.getText(), answerF.getText()));

                table.setItems(data);
            }
                
            
        });
        this.add.setOnAction(actionEvent -> {
            System.out.print("Add question button pressed");
            stage.close();
            //add(path+"test.html");
           
        });
    }

    @Override
    public void show(Stage thisStage) {
        stage.show();
        this.stage.centerOnScreen();
    }
    

}
