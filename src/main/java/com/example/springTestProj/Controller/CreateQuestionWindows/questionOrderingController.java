package com.example.springTestProj.Controller.CreateQuestionWindows;

import com.example.springTestProj.Controller.MainController;
import com.example.springTestProj.Controller.QuestionHTMLHelper;
import com.example.springTestProj.Controller.TestMakerController;
import com.example.springTestProj.Entities.QuestionEntities.ShortAnswerQuestion;
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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

//creates a draggable interface for ordering
@Component
@FxmlView("/questionOrdering.fxml")
public class questionOrderingController implements ControlDialogBoxes {

    //initialize variables
    private final UserService userService;
    private final TestService testService;
    private final MultiChoiceQService multiChoiceQService;
    private final MatchingQService matchingQService;
    private final EssayQuestionService essayQuestionService;
    private final ShortAnswerQService shortAnswerQService;
    private final TrueFalseQService trueFalseQService;
    private final FillinBlankQService fillinBlankQService;
    private final TestMakerController testMakerController;
    
    private final MainController mainController;
    private final FxWeaver fxWeaver;
    private final QuestionHTMLHelper questionHTMLHelper;
    private Stage stage;
    public static String ordering="";

    @FXML
    private VBox questionOrderingBox;
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

    private static final ObservableList<String> leftList = FXCollections
            .observableArrayList();

    private static final ObservableList<String> rightList = FXCollections
            .observableArrayList();

    public String path = "src\\main\\resources\\";
    
    public String types;

    //constructor
    public questionOrderingController(MainController mainController,UserService userService,TestMakerController testMakerController,FillinBlankQService fillinBlankQService, FxWeaver fxWeaver,MatchingQService matchingQService,EssayQuestionService essayQuestionService,TrueFalseQService trueFalseQService, MultiChoiceQService multiChoiceQService,ShortAnswerQService shortAnswerQService, TestService testService,QuestionHTMLHelper questionHTMLHelper) {
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.shortAnswerQService = shortAnswerQService;
        this.testService = testService;
        this.questionHTMLHelper = questionHTMLHelper;
        this.multiChoiceQService = multiChoiceQService;
        this.matchingQService = matchingQService;
        this.essayQuestionService = essayQuestionService;
        this.trueFalseQService = trueFalseQService;
        this.fillinBlankQService = fillinBlankQService;
        this.testMakerController = testMakerController;
        this.mainController = mainController;
    }

    //initialize is automatically called and this is where we store button action events
    @FXML
    public void initialize() {
        //makes window listen for drag and drops
        initializeListeners();
        
        //resets window
        this.reset.setOnAction(actionEvent -> {
            types();
            repopulateData();

        });
        
        //applies ordering
        this.apply.setOnAction(actionEvent -> {
            
            List<String> numbers = list2.getItems();
            ordering = String.join(",", numbers);
            String[] order=ordering.split("[,]",0);
            
            //counts for 6 variables in drag window
            int counter = 0;
            for (int i = 0; i < order.length; i++) {
                if (order[i] != null) {
                    counter++;
                }
            }
            //checks for 6 variables in window and errors if not
            if(counter!=6)
                {
                    error.setText(" ERROR: Not all elements dragged");
                }
                else{
            Test currentTest = testService.returnThisTest();
            String testName = currentTest.getTestName();
            //file as string
            String data="";
            try
                {
                    data=new String(Files.readAllBytes(Paths.get(QuestionHTMLHelper.pathTo+testName)));
                } catch (IOException ex) {
                Logger.getLogger(questionOrderingController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //holds data for existing imput 
            String Body= StringUtils.substringBetween(data,"<!body>", "</body>");
            String Match = StringUtils.substringBetween(data,"<section id=\"Matching\">", "</section>");
            String Tf= StringUtils.substringBetween(data,"<section id=\"TrueFalse\">", "</section>");
            String Mc= StringUtils.substringBetween(data,"<section id=\"MultiChoice\">", "</section>");
            String Fib= StringUtils.substringBetween(data,"<section id=\"FillInBlank\">", "</section>");
            String Short= StringUtils.substringBetween(data,"<section id=\"ShortAnswer\">", "</section>");
            String Essay= StringUtils.substringBetween(data,"<section id=\"Essay\">", "</section>");
            String matchT="<section id=\"Matching\">";
            String tfT="<section id=\"TrueFalse\">";
            String mcT="<section id=\"MultiChoice\">";
            String fibT="<section id=\"FillInBlank\">";
            String shortT="<section id=\"ShortAnswer\">";
            String essayT="<section id=\"Essay\">";
            String end="</section>";
            
            String reorder="<!body>\n";
            
            //reorders data to specified ordering in html
            for (String myStr : order) {
                
                data=data.replace(Body, "");
                //System.out.println(myStr);
                
                if(myStr.equals("Multiple Choice"))
                {
                    reorder=reorder+mcT+Mc+end;
                }
                if(myStr.equals("Fill in the Blank"))
                {
                    reorder=reorder+fibT+Fib+end;
                }
                if(myStr.equals("Matching"))
                {
                    reorder=reorder+matchT+Match+end;
                }
                if(myStr.equals("True/False"))
                {
                    reorder=reorder+tfT+Tf+end;
                }
                if(myStr.equals("Short Answer"))
                {
                    reorder=reorder+shortT+Short+end;
                }
                if(myStr.equals("Essay"))
                {
                    reorder=reorder+essayT+Essay+end;
                }
            }
            
           data=data.replace("<!body>", reorder);
            
            Path pathTo
            = Paths.get(QuestionHTMLHelper.pathTo+testName);
            //code where we edit html
             // Try block to check for exceptions
        try {
            // Now calling Files.writeString() method
            // with path , content & standard charsets
            Files.writeString(pathTo, data,
                              StandardCharsets.UTF_8);
        }
 
        // Catch block to handle the exception
        catch (IOException ex) {
            // Print messqage exception occurred as
            // invalid. directory local path is passed
            System.out.print("Invalid Path");
        }
            
        //refresh webview
        QuestionHTMLHelper gH = new QuestionHTMLHelper(testService, shortAnswerQService, essayQuestionService, multiChoiceQService, matchingQService, trueFalseQService, fillinBlankQService, testMakerController, mainController);
            try {
                gH.updateSections(path + testName, path + "KEY_" + testName);
            } catch (IOException ex) {
                Logger.getLogger(TestMakerController.class.getName()).log(Level.SEVERE, null, ex);
            }    
            TestMakerController.engine.reload();
            stage.close();
            }
        });
        
        //refresh/repopulate data to left listview
        repopulateData();
        
        //setup window
        this.stage = new Stage();
        stage.setTitle("Question Ordering");
        stage.setScene(new Scene(questionOrderingBox));
    }

    @Override
    public void show(Stage thisStage) {
        stage.show();
        this.stage.centerOnScreen();
    }

    //controls what to do for each drag movement
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
    //drag over
    list2.setOnDragOver(new EventHandler<DragEvent>() {
      @Override
      public void handle(DragEvent dragEvent) {
        dragEvent.acceptTransferModes(TransferMode.MOVE);
      }
    });
    //dropped by mouse
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

    //repopulates lists
    private void repopulateData()
    {
        list.getItems().clear();
        list2.getItems().clear();
        leftList.addAll("Multiple Choice","Fill in the Blank","Matching","True/False","Short Answer","Essay");
        
        list.setItems(leftList);
        list2.setItems(rightList);
    }
    
    //changes items to string
    private void types()
    {
        types=list2.getItems().toString();
    }

   
}
