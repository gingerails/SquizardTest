package com.example.springTestProj.Controller.CreateQuestionWindows.AddQuestionFromDB;

import com.example.springTestProj.Controller.CreateQuestionWindows.ControlDialogBoxes;
import com.example.springTestProj.Controller.TestMakerController;
import com.example.springTestProj.Entities.QuestionEntities.MatchingQuestion;
import com.example.springTestProj.Entities.QuestionEntities.MultiChoiceQuestion;
import com.example.springTestProj.Entities.QuestionEntities.ShortAnswerQuestion;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.QuestionService.MatchingQService;
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

//adds maching from db to test
@Component
@FxmlView("/addM.fxml")
public class addMController implements ControlDialogBoxes {
    //variable setup
    private final UserService userService;
    private final FxWeaver fxWeaver;
    private final ShortAnswerQService shortAnswerQService;
    private final MatchingQService matchingQService;

    private final TestService testService;
    private Stage stage;

    @FXML
    private VBox MVBox;
    @FXML
    private ListView<String> list;
    
    
    @FXML
    private Button select;
    @FXML
    private Button apply;
    @FXML
    private Label selection;
 
    private static final ObservableList<String> leftList = FXCollections
            .observableArrayList();

    public String item;
    public String path = "src\\main\\resources\\";
    
    public String types;
    
    //contrsuctor
    public addMController(UserService userService, FxWeaver fxWeaver, ShortAnswerQService shortAnswerQService, TestService testService, MatchingQService matchingQService) {
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.shortAnswerQService = shortAnswerQService;
        this.testService = testService;
        this.matchingQService = matchingQService;
    }

    //controls btns and setup
    public void initialize() {
       //This keeps the selected question from being re-added to the box
        leftList.clear();
        // repopulateData();
        initializeListeners();
        
        //controls select btn
        this.select.setOnAction(actionEvent -> {
            item=list.getSelectionModel().getSelectedItem();
            selection.setText("ADD: "+item);
            
        });
        
        //controls apply btn
        this.apply.setOnAction(actionEvent -> {
            stage.close();
            
        });
        
        //window setup
        populateData();
        this.stage = new Stage();
        stage.setTitle("Question Ordering");
        stage.setScene(new Scene(MVBox));
    }

    @Override
    public void show(Stage thisStage) {
        stage.show();
        this.stage.centerOnScreen();
    }

    private void initializeListeners() {
    // drag from left to right
    
    }

    //populates data
    private void populateData() {
        List<MatchingQuestion> mQuestions = matchingQService.readQuestions();
        for(MatchingQuestion q : mQuestions){
            String termContent = q.getTerm();
            String answerContent = q.getCorrectAnswer();
           //System.out.println("Q:    " + questionContent);
            //System.out.println("A:    " + questionAnswer);
            leftList.addAll(termContent+" "+answerContent);
        } 
        

        list.setItems(leftList);
        
    }
     
   
   
   
}
