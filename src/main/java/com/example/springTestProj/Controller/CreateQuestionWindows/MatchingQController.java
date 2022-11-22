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
import com.example.springTestProj.Controller.TestMakerController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static javafx.collections.FXCollections.observableList;
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
    
    private ObservableList<Matching> data=FXCollections.observableArrayList();
    
    private final List<String> termsArray=new ArrayList<String>();
    private final List<String> answersArray=new ArrayList<String>();

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
      
        this.addRow.setOnAction((var actionEvent) -> {
            
            //Matching match=new Matching(termF.getText(), answerF.getText());
            if (termF.getText().isEmpty() || answerF.getText().isEmpty()) {
                error.setText("ERROR: Term and/or Answer is blank");
            } else {
                
                termsArray.add(termF.getText());
                answersArray.add(answerF.getText());
                
                //data.add(match);
                
                //table.setItems(data);
                table.getItems().add(new Matching(termF.getText(), answerF.getText()));
                termF.clear();
                answerF.clear();
            }
                
            
        });
        this.add.setOnAction(actionEvent -> {
            System.out.print("Add question button pressed");
            stage.close();
            addHTML(path+"test.html");
           
        });
    }

    @Override
    public void show(Stage thisStage) {
        stage.show();
        this.stage.centerOnScreen();
    }
    public void addHTML(String file) {
        try ( FileWriter f = new FileWriter(file, true);  BufferedWriter b = new BufferedWriter(f);  PrintWriter p = new PrintWriter(b);) {

             p.println("<hr />" + "\n"
            +"<p><span style='font-size:16px'><strong>Matching: </strong></span></p>"+"\n");
            
            for(int i=0;i<termsArray.size();i++)
            {
            p.println(termsArray.get(i)+"&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;"+answersArray.get(i)+"</p>"+"\n"
                   
            );
            }
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
