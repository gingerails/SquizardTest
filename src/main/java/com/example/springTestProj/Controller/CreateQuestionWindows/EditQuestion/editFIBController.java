package com.example.springTestProj.Controller.CreateQuestionWindows.EditQuestion;

import com.example.springTestProj.Controller.CreateQuestionWindows.ControlDialogBoxes;
import com.example.springTestProj.Controller.TestMakerController;
import com.example.springTestProj.Entities.QuestionEntities.EssayQuestion;
import com.example.springTestProj.Entities.QuestionEntities.FillinBlankQuestion;
import com.example.springTestProj.Entities.QuestionEntities.MatchingQuestion;
import com.example.springTestProj.Entities.QuestionEntities.MultiChoiceQuestion;
import com.example.springTestProj.Entities.QuestionEntities.ShortAnswerQuestion;
import com.example.springTestProj.Entities.QuestionEntities.TrueFalseQuestion;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.QuestionService.EssayQuestionService;
import com.example.springTestProj.Service.QuestionService.FillinBlankQService;
import com.example.springTestProj.Service.QuestionService.MatchingQService;
import com.example.springTestProj.Service.QuestionService.MultiChoiceQService;
import com.example.springTestProj.Service.QuestionService.ShortAnswerQService;
import com.example.springTestProj.Service.QuestionService.TrueFalseQService;
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
import java.util.Arrays;
import java.util.List;

//SAME functions and functionality as editEController
//look at other file for functionalit and comments for functions

@Component
@FxmlView("/FIBOrdering.fxml")
public class editFIBController implements ControlDialogBoxes {

    private final UserService userService;
    private final MatchingQService matchingQService;
    private final FxWeaver fxWeaver;
    private final ShortAnswerQService shortAnswerQService;
    private final TestService testService;
    private final FillinBlankQService fillinBlankQService;
    
    
    private Stage stage;

    @FXML
    private VBox fibVBox;
    @FXML
    private ListView<String> list;
    @FXML
    private ListView<String> list2;
    @FXML 
    private Label error;
    @FXML
    private Button reset;
    @FXML
    private Button apply;

    //private ObservableList<String> types;
    private static final ObservableList<String> leftList = FXCollections
            .observableArrayList();

    private static final ObservableList<String> rightList = FXCollections
            .observableArrayList();

    public String path = "src\\main\\resources\\";
    
    public String types;

    public editFIBController(UserService userService, FxWeaver fxWeaver, ShortAnswerQService shortAnswerQService, TestService testService,MatchingQService matchingQService,FillinBlankQService fillinBlankQService) {
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.shortAnswerQService = shortAnswerQService;
        this.testService = testService;
        this.matchingQService = matchingQService;
        this.fillinBlankQService = fillinBlankQService;
    }

    @FXML
    public void initialize() {
        //repopulateData();
        initializeListeners();
        
        this.reset.setOnAction(actionEvent -> {
            types();
            repopulateData();
            
            
        });
        this.apply.setOnAction(actionEvent -> {
            stage.close();
            
        });
        repopulateData();
        this.stage = new Stage();
        stage.setTitle("Question Ordering");
        stage.setScene(new Scene(fibVBox));
    }

    @Override
    public void show(Stage thisStage) {
        stage.show();
        this.stage.centerOnScreen();
    }

    private void initializeListeners() {
    // drag from left to right
    list.setOnDragDetected(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (list.getSelectionModel().getSelectedItem() == null) {
          return;
        }

        Dragboard dragBoard = list.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putString(list.getSelectionModel().getSelectedItem());
        dragBoard.setContent(content);
      }
    });

    list2.setOnDragOver(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent dragEvent) {
        dragEvent.acceptTransferModes(TransferMode.MOVE);
      }
    });

    list2.setOnDragDropped(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent dragEvent) {
        String player = dragEvent.getDragboard().getString();
        list2.getItems().addAll(player);
        leftList.remove(player);
        list.setItems(leftList);
        dragEvent.setDropCompleted(true);
      }
    });
    }

     
    private void repopulateData()
    {
         try{
        Test currentTest = testService.returnThisTest();
        String mcQid = currentTest.getMultiChoiceQ();
        
        String[] arrStr = mcQid.split(",");
        String[] arrStrM = Arrays.copyOfRange(arrStr, 1, arrStr.length);
        
        
        list.getItems().clear();
        list2.getItems().clear();
        List<FillinBlankQuestion> mQuestions = fillinBlankQService.readQuestions();
        
        
            for (String id : arrStrM) {

                FillinBlankQuestion eq = fillinBlankQService.findQuestionByID(id);
                String questionContent = eq.getQuestionContent();
                //System.out.println("A:    " + questionAnswer);
                leftList.addAll(questionContent);
            }
        
        //leftList.addAll("Multiple Choice","Fill in the Blank","Matching","True/False","Short Answer","Essay");
        
        list.setItems(leftList);
        list2.setItems(rightList);
        }
        catch(NullPointerException e)
        {
            error.setText("ERROR: No Questions");
        }
    
    }
    
    private void types()
    {
        
        types=list2.getItems().toString();
        System.out.println(types);
    }

   
}
