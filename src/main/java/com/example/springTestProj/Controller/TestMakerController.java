package com.example.springTestProj.Controller;

import com.example.springTestProj.Controller.CreateQuestionWindows.AddQuestionFromDB.addMController;
import com.example.springTestProj.Controller.CreateQuestionWindows.EssayQuestionController;
import com.example.springTestProj.Controller.CreateQuestionWindows.FillinBlankQController;
import com.example.springTestProj.Controller.CreateQuestionWindows.MatchingQController;
import com.example.springTestProj.Controller.CreateQuestionWindows.MultiChoiceQController;
import com.example.springTestProj.Controller.CreateQuestionWindows.TrueFalseQController;
import com.example.springTestProj.Controller.CreateQuestionWindows.ShortQuestionController;
import com.example.springTestProj.Controller.CreateQuestionWindows.AddQuestionFromDB.addEController;
import com.example.springTestProj.Controller.CreateQuestionWindows.AddQuestionFromDB.addFIBController;

import com.example.springTestProj.Controller.CreateQuestionWindows.AddQuestionFromDB.addMCController;
import com.example.springTestProj.Controller.CreateQuestionWindows.AddQuestionFromDB.addShortAnswerController;
import com.example.springTestProj.Controller.CreateQuestionWindows.AddQuestionFromDB.addTFController;
import com.example.springTestProj.Controller.CreateQuestionWindows.EditQuestion.editEController;
import com.example.springTestProj.Controller.CreateQuestionWindows.EditQuestion.editFIBController;
import com.example.springTestProj.Controller.CreateQuestionWindows.EditQuestion.editMCController;
import com.example.springTestProj.Controller.CreateQuestionWindows.EditQuestion.editMController;
import com.example.springTestProj.Controller.CreateQuestionWindows.EditQuestion.editSAController;
import com.example.springTestProj.Controller.CreateQuestionWindows.EditQuestion.editTFController;
import com.example.springTestProj.Controller.CreateQuestionWindows.questionOrderingController;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.TestService;
import com.example.springTestProj.Service.UserService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * FXML Controller class
 *
 * @author Orames
 */
@Component
@FxmlView("/testMaker.fxml")
public class TestMakerController implements ControlSwitchScreen {

    private final UserService userService;
    private final TestService testService;
    private final FxWeaver fxWeaver;
    private Stage stage;

    @FXML
    private WebView viewer;
    public static WebEngine engine;
    @FXML
    private VBox mainVbox;
    @FXML
    private Button add, addMC, editMC, editM, editFIB, editSA, editE, editTF;
    @FXML
    private Button addTF, addE, addM, addFIB, addSA;
    @FXML
    private Button publish;
    @FXML
    private ComboBox questionType;
    @FXML 
    private MenuBar menuBar;

    @FXML
    private ListView<String> mcList;

    private static final ObservableList<String> mcListArray = FXCollections
            .observableArrayList();

    @FXML
    private ListView<String> fibList;

    private static final ObservableList<String> fibListArray = FXCollections
            .observableArrayList();

    @FXML
    private ListView<String> tfList;

    private static final ObservableList<String> tfListArray = FXCollections
            .observableArrayList();

    @FXML
    private ListView<String> eList;

    private static final ObservableList<String> eListArray = FXCollections
            .observableArrayList();

     @FXML
    private ListView<String> saList;

    private static final ObservableList<String> saListArray = FXCollections
            .observableArrayList();

     @FXML
    private ListView<String> mList;

    private static final ObservableList<String> mListArray = FXCollections
            .observableArrayList();

    public String path = "src\\main\\resources\\generatedTests\\";



    
    public TestMakerController(UserService userService, TestService testService, FxWeaver fxWeaver) {
        this.testService = testService;
        this.fxWeaver = fxWeaver;
        this.userService = userService;
    }

    @FXML
    public void initialize() throws IOException {
        Test currentTest = testService.returnThisTest();
        String testName = currentTest.getTestName();
        File f = QuestionHTMLHelper.createNewFile(testName);

      //  File f = new File(path + testName);
       // createTest(path + testName, testName);
        //webviewer
        engine = viewer.getEngine();
        engine.load(f.toURI().toString());

        addMCText();
        addTFText();
        addSAText();
        addEText();
        addFIBText();
        addMText();
        questionType.getItems().addAll(
                "Essay",
                "Multiple Choice",
                "MatchingQuestion",
                "Fill in Blank",
                "True/False",
                "Short Answer"
        );
        this.add.setOnAction(actionEvent -> {
            try {
                //System.out.print("essay Question");
                pickQuestion();

            } catch (IOException ex) {
                Logger.getLogger(TestMakerController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
         this.addMC.setOnAction(actionEvent -> {

                //System.out.print("essay Question");
                addMCScene();


        });
         this.editMC.setOnAction(actionEvent -> {

                //System.out.print("essay Question");
                editMCScene();


        });
          this.editM.setOnAction(actionEvent -> {

                //System.out.print("essay Question");
                editMScene();


        });
            this.editFIB.setOnAction(actionEvent -> {

                //System.out.print("essay Question");
                editFIBScene();


        });
            this.editTF.setOnAction(actionEvent -> {

                //System.out.print("essay Question");
                editTFScene();


        });
         this.editE.setOnAction(actionEvent -> {

                //System.out.print("essay Question");
                editEScene();


        });
         this.editSA.setOnAction(actionEvent -> {

                //System.out.print("essay Question");
                editSAScene();


        });
         this.addM.setOnAction(actionEvent -> {

                //System.out.print("essay Question");
                addMScene();


        });

         this.addTF.setOnAction(actionEvent -> {

                //System.out.print("essay Question");
                addTFScene();


        });
          this.addSA.setOnAction(actionEvent -> {

                //System.out.print("essay Question");
                addSAScene();


        });
          this.addE.setOnAction(actionEvent -> {

                //System.out.print("essay Question");
                addEScene();


        });
          this.addFIB.setOnAction(actionEvent -> {

                //System.out.print("essay Question");
                addFIBScene();


        });
        this.publish.setOnAction(actionEvent -> {

           // publish(path + testName);

        });


    }

    @Override
    public Stage getCurrentStage() {
        Node node = add.getParent(); // cant set this in init bc it could cause a null pointer :-\ probably needs its own method
        Stage currentStage = (Stage) node.getScene().getWindow();
        return currentStage;
    }

    @Override
    public void show(Stage thisStage) {
        this.stage = thisStage;
        stage.setScene(new Scene(mainVbox));

        this.stage.setMaximized(false);
        stage.show();
        this.stage.setMaximized(true);
        this.stage.centerOnScreen();
    }


//    public void createTest(String file, String testName) throws IOException {
//        String setup = "<!DOCTYPE html>" + "\n"
//                + "<html>" + "\n"
//                + "<p style='text-align:center'><span style='font-size:36px'><strong>" + testName + "</strong></span></p>" + "\n";
//        String Title = "";
//        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
//        writer.write(setup);
//
//        writer.close();
//
//    }



//    public void publish(String file) {
//        try ( FileWriter f = new FileWriter(file, true);  BufferedWriter b = new BufferedWriter(f);  PrintWriter p = new PrintWriter(b);) {
//
//            p.println("</html>");
//            b.close();
//            p.close();
//            f.close();
//            engine.reload();
//        } catch (IOException i) {
//            i.printStackTrace();
//        }
//
//    }
    
    public void Qorder()
    {
        System.out.println("Qorder");
            FxControllerAndView<questionOrderingController, VBox> QuestionOrderingControllerAndView
                    = fxWeaver.load(questionOrderingController.class);
            QuestionOrderingControllerAndView.getController().show(getCurrentStage());
    }
    public void refresh()
    {
        engine.reload();
    }
    
    public void pickQuestion() throws IOException {
        String qType = (String) questionType.getValue();
        System.out.println(qType);

        if ("Essay".equals(qType) == true) {
            System.out.println("essay");
            FxControllerAndView<EssayQuestionController, VBox> essayQuestionControllerAndView
                    = fxWeaver.load(EssayQuestionController.class);
            essayQuestionControllerAndView.getController().show(getCurrentStage());

        }
        if ("Multiple Choice".equals(qType) == true) {
            System.out.println("Multiple Choice");
            FxControllerAndView<MultiChoiceQController, VBox> McQuestionControllerAndView =
                    fxWeaver.load(MultiChoiceQController.class);
            McQuestionControllerAndView.getController().show(getCurrentStage());
        }
        if ("MatchingQuestion".equals(qType) == true) {
            System.out.println("MatchingQuestion");
            FxControllerAndView<MatchingQController, VBox> mQuestionControllerAndView =
                    fxWeaver.load(MatchingQController.class);
            mQuestionControllerAndView.getController().show(getCurrentStage());
        }
        if ("Fill in Blank".equals(qType) == true) {
            System.out.println("FIB");
            FxControllerAndView<FillinBlankQController, VBox> fibQuestionControllerAndView =
                    fxWeaver.load(FillinBlankQController.class);
            fibQuestionControllerAndView.getController().show(getCurrentStage());
        }
        if ("True/False".equals(qType) == true) {
            System.out.println("T/F");
            FxControllerAndView<TrueFalseQController, VBox> tfQuestionControllerAndView =
                    fxWeaver.load(TrueFalseQController.class);
            tfQuestionControllerAndView.getController().show(getCurrentStage());
        }
        if ("Short Answer".equals(qType) == true) {
            System.out.println("Short");
            FxControllerAndView<ShortQuestionController, VBox> shortQuestionControllerAndView
                    = fxWeaver.load(ShortQuestionController.class);
            shortQuestionControllerAndView.getController().show(getCurrentStage());
        }
    }

    /**
     *
     */
    public void addMCText()
    {
        mcListArray.add("Question: "+"question"+"\n"
                +"Choice 1: "+"choice"+"\n"
                +"Choice 2: "+"choice"+"\n"
                +"Choice 3: "+"choice"+"\n"
                +"Choice 4: "+"choice"+"\n"
                +"Answer: "+"answer"+"\n"
                +"Sections: "+"sections"+"\n"
                +"Material: "+"material"+"\n"
                +"Comment: "+"comment"+"\n"
                +"Grading Instructions: "+"instructions"+"\n"
        
        );
        mcList.setItems(mcListArray);
    }
     public void addFIBText()
    {
        fibListArray.add("Question: "+"question"+"\n"
                +"Choice 1: "+"choice"+"\n"
                +"Choice 2: "+"choice"+"\n"
                +"Choice 3: "+"choice"+"\n"
                +"Choice 4: "+"choice"+"\n"
                +"Answer: "+"answer"+"\n"
                +"Sections: "+"sections"+"\n"
                +"Material: "+"material"+"\n"
                +"Comment: "+"comment"+"\n"
                +"Grading Instructions: "+"instructions"+"\n"

        );
        fibList.setItems(fibListArray);
    }
    public void addMText()
    {
        mListArray.add("Question: "+"question"+"\n"
                +"Choice 1: "+"choice"+"\n"
                +"Choice 2: "+"choice"+"\n"
                +"Choice 3: "+"choice"+"\n"
                +"Choice 4: "+"choice"+"\n"
                +"Answer: "+"answer"+"\n"
                +"Sections: "+"sections"+"\n"
                +"Material: "+"material"+"\n"
                +"Comment: "+"comment"+"\n"
                +"Grading Instructions: "+"instructions"+"\n"

        );
        mList.setItems(mListArray);
    }
    public void addEText()
    {
        eListArray.add("Question: "+"question"+"\n"
                +"Choice 1: "+"choice"+"\n"
                +"Choice 2: "+"choice"+"\n"
                +"Choice 3: "+"choice"+"\n"
                +"Choice 4: "+"choice"+"\n"
                +"Answer: "+"answer"+"\n"
                +"Sections: "+"sections"+"\n"
                +"Material: "+"material"+"\n"
                +"Comment: "+"comment"+"\n"
                +"Grading Instructions: "+"instructions"+"\n"

        );
        eList.setItems(eListArray);
    }
    public void addSAText()
    {
        saListArray.add("Question: "+"question"+"\n"
                +"Choice 1: "+"choice"+"\n"
                +"Choice 2: "+"choice"+"\n"
                +"Choice 3: "+"choice"+"\n"
                +"Choice 4: "+"choice"+"\n"
                +"Answer: "+"answer"+"\n"
                +"Sections: "+"sections"+"\n"
                +"Material: "+"material"+"\n"
                +"Comment: "+"comment"+"\n"
                +"Grading Instructions: "+"instructions"+"\n"

        );
        saList.setItems(saListArray);
    }
    public void addTFText()
    {
        tfListArray.add("Question: "+"question"+"\n"
                +"Choice 1: "+"choice"+"\n"
                +"Choice 2: "+"choice"+"\n"
                +"Choice 3: "+"choice"+"\n"
                +"Choice 4: "+"choice"+"\n"
                +"Answer: "+"answer"+"\n"
                +"Sections: "+"sections"+"\n"
                +"Material: "+"material"+"\n"
                +"Comment: "+"comment"+"\n"
                +"Grading Instructions: "+"instructions"+"\n"

        );
        tfList.setItems(tfListArray);
    }

    /**
     * Add question from database
     */
    public void addMCScene()
    {
        FxControllerAndView<addMCController, VBox> AddMCControllerAndView
                = fxWeaver.load(addMCController.class);
        AddMCControllerAndView.getController().show(getCurrentStage());

    }
    public void editMCScene()
    {
        FxControllerAndView<editMCController, VBox> EditMCControllerAndView
                = fxWeaver.load(editMCController.class);
        EditMCControllerAndView.getController().show(getCurrentStage());
    }
    public void editSAScene()
    {
        FxControllerAndView<editSAController, VBox> EditSAControllerAndView
                = fxWeaver.load(editSAController.class);
        EditSAControllerAndView.getController().show(getCurrentStage());
    }
    public void editTFScene()
    {
        FxControllerAndView<editTFController, VBox> EditTFControllerAndView
                = fxWeaver.load(editTFController.class);
        EditTFControllerAndView.getController().show(getCurrentStage());
    }
    public void editFIBScene()
    {
        FxControllerAndView<editFIBController, VBox> EditFIBControllerAndView
                = fxWeaver.load(editFIBController.class);
        EditFIBControllerAndView.getController().show(getCurrentStage());
    }
    public void editMScene()
    {
        FxControllerAndView<editMController, VBox> EditMControllerAndView
                = fxWeaver.load(editMController.class);
        EditMControllerAndView.getController().show(getCurrentStage());
    }
    public void editEScene()
    {
        FxControllerAndView<editEController, VBox> EditEControllerAndView
                = fxWeaver.load(editEController.class);
        EditEControllerAndView.getController().show(getCurrentStage());
    }
    public void addEScene()
    {
        FxControllerAndView<addEController, VBox> AddEControllerAndView
                = fxWeaver.load(addEController.class);
        AddEControllerAndView.getController().show(getCurrentStage());
    }
    public void addSAScene()
    {
        FxControllerAndView<addShortAnswerController, VBox> AddSAControllerAndView
                = fxWeaver.load(addShortAnswerController.class);
        AddSAControllerAndView.getController().show(getCurrentStage());
    }
    public void addTFScene()
    {
        FxControllerAndView<addTFController, VBox> AddTFControllerAndView
                = fxWeaver.load(addTFController.class);
        AddTFControllerAndView.getController().show(getCurrentStage());
    }
    public void addFIBScene()
    {
        FxControllerAndView<addFIBController, VBox> AddFIBControllerAndView
                = fxWeaver.load(addFIBController.class);
        AddFIBControllerAndView.getController().show(getCurrentStage());
    }
    public void addMScene()
    {
        FxControllerAndView<addMController, VBox> AddMontrollerAndView
                = fxWeaver.load(addMController.class);
        AddMontrollerAndView.getController().show(getCurrentStage());
    }


}
