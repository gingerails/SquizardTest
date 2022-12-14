package com.example.springTestProj.Controller.CreateQuestionWindows.AddQuestionFromDB;

import com.example.springTestProj.Controller.CreateQuestionWindows.ControlDialogBoxes;
import com.example.springTestProj.Controller.TestMakerController;
import com.example.springTestProj.Entities.QuestionEntities.EssayQuestion;
import com.example.springTestProj.Entities.QuestionEntities.MultiChoiceQuestion;
import com.example.springTestProj.Entities.QuestionEntities.ShortAnswerQuestion;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.QuestionService.EssayQuestionService;
import com.example.springTestProj.Service.QuestionService.MultiChoiceQService;
import com.example.springTestProj.Service.QuestionService.ShortAnswerQService;
import com.example.springTestProj.Service.TestService;
import com.example.springTestProj.Service.UserService;

import javafx.scene.paint.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import java.awt.image.ColorModel;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

//controls adding essay window
@Component
@FxmlView("/addE.fxml")
public class addEController implements ControlDialogBoxes {

    private final UserService userService;
    private final FxWeaver fxWeaver;
    private final ShortAnswerQService shortAnswerQService;
    private final EssayQuestionService essayQuestionService;

    private final TestService testService;
    private Stage stage;

    @FXML
    private VBox EVBox;
    @FXML
    private ListView<String> list;
    @FXML
    private Button select;
    @FXML
    private Button apply;
    @FXML
    private Label selection;
   
    private static final ObservableList<String> leftList = FXCollections.observableArrayList();

    public String item;
    public String path = "src\\main\\resources\\";
    
    public String types;

    //contstructor
    public addEController(UserService userService, FxWeaver fxWeaver, ShortAnswerQService shortAnswerQService, TestService testService, EssayQuestionService essayQuestionService) {
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.shortAnswerQService = shortAnswerQService;
        this.testService = testService;
        this.essayQuestionService = essayQuestionService;
    }

    //initialize is automatically called and this is where we store button action events
    @FXML
    public void initialize() {
        //This keeps the selected question from being re-added to the box
        leftList.clear();
        initializeListeners();
        
        //controls select button
        this.select.setOnAction(actionEvent -> {
            item=list.getSelectionModel().getSelectedItem();
            selection.setText("ADD: "+item);
            
        });
        
        //controls apply button
        this.apply.setOnAction(actionEvent -> {
            stage.close();
            
        });
        
        //populates data
        populateData();
        this.stage = new Stage();
        stage.setTitle("Question Ordering");
        stage.setScene(new Scene(EVBox));
    }

    @Override
    public void show(Stage thisStage) {
        stage.show();
        this.stage.centerOnScreen();
    }

    private void initializeListeners() {
    // drag from left to right
    
    }

    
    private void populateData() {
        
        List<EssayQuestion> eQuestions = essayQuestionService.readQuestions();
        for(EssayQuestion q : eQuestions){
            String questionContent = q.getQuestionContent();
            String testN="Test1";
            StringUtils.leftPad(testN,8," ");
            //String questionAnswer = q.getCorrectAnswer();
            System.out.println("Q:    " + questionContent);
            //System.out.println("A:    " + questionAnswer);
            leftList.addAll("Question: "+questionContent + " Testname: " + testN );
        } 
        list.setItems(leftList);
        
    }
     
  
   
   
   
}
