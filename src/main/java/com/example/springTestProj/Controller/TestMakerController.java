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
import com.example.springTestProj.Entities.QuestionEntities.EssayQuestion;
import com.example.springTestProj.Entities.QuestionEntities.MatchingQuestion;
import com.example.springTestProj.Entities.QuestionEntities.MultiChoiceQuestion;
import com.example.springTestProj.Entities.QuestionEntities.ShortAnswerQuestion;
import com.example.springTestProj.Entities.QuestionEntities.TrueFalseQuestion;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.QuestionService.EssayQuestionService;
import com.example.springTestProj.Service.QuestionService.MatchingQService;
import com.example.springTestProj.Service.QuestionService.MultiChoiceQService;
import com.example.springTestProj.Service.QuestionService.ShortAnswerQService;
import com.example.springTestProj.Service.QuestionService.TrueFalseQService;
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
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

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
    private final MultiChoiceQService multiChoiceQService;
    private final MatchingQService matchingQService;
     private final EssayQuestionService essayQuestionService;
     private final ShortAnswerQService shortAnswerQService;
     private final TrueFalseQService trueFalseQService;
    private final FxWeaver fxWeaver;
    private Stage stage;

    @FXML
    private WebView viewer;
    public static WebEngine engine;
    public static int refresh=0;
    @FXML
    private VBox mainVbox;
    @FXML
    private Button add;
    @FXML
    private Button addMC;
    @FXML
    private Button editMC;
    @FXML
    private Button editM;
    @FXML
    private Button editFIB;
    @FXML
    private Button editSA;
    @FXML
    private Button editE;
    @FXML
    private Button editTF;
    
    @FXML
    private Button addTF;
    @FXML
    private Button addE;
    @FXML
    private Button addM;
    @FXML
    private Button addFIB;
    @FXML
    private Button addSA;
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



    
    public TestMakerController(UserService userService, TestService testService, FxWeaver fxWeaver,MultiChoiceQService multiChoiceQService,MatchingQService matchingQService,EssayQuestionService essayQuestionService, ShortAnswerQService shortAnswerQService,TrueFalseQService trueFalseQService) {
        this.testService = testService;
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.multiChoiceQService = multiChoiceQService;
        this.matchingQService = matchingQService;
        this.essayQuestionService = essayQuestionService;
        this.shortAnswerQService = shortAnswerQService;
        this.trueFalseQService=trueFalseQService;
        
    }
    public void refresh()
            {
        
                addMCText();
                addTFText();
                addSAText();
                addEText();
                addFIBText();
                addMText();

            }
    @FXML
    public void initialize() throws IOException {
        Test currentTest = testService.returnThisTest();
        String testName = currentTest.getTestName();

        File f = new File(path + testName);
        createTest(path + testName, testName);
        //webviewer
        engine = viewer.getEngine();
        engine.load(f.toURI().toString());

        
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

            publish(path + testName);

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

    public void createTest(String file, String testName) throws IOException {
        String setup = "<!DOCTYPE html>" + "\n"
                + "<html>" + "\n"
                + "<p style='text-align:center'><span style='font-size:36px'><strong>" + testName + "</strong></span></p>" + "\n";
        String Title = "";
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(setup);

        writer.close();

    }



    public void publish(String file) {
        try ( FileWriter f = new FileWriter(file, true);  BufferedWriter b = new BufferedWriter(f);  PrintWriter p = new PrintWriter(b);) {

            p.println("</html>");
            b.close();
            p.close();
            f.close();
            engine.reload();
        } catch (IOException i) {
            i.printStackTrace();
        }

    }
    
    public void Qorder()
    {
        System.out.println("Qorder");
            FxControllerAndView<questionOrderingController, VBox> QuestionOrderingControllerAndView
                    = fxWeaver.load(questionOrderingController.class);
            QuestionOrderingControllerAndView.getController().show(getCurrentStage());
    }
   // public void refresh()
    //{
    //   engine.reload();
    //}
    
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
      
        
        try{
        Test currentTest = testService.returnThisTest();
        String mcQid = currentTest.getMultiChoiceQ();
        
        String[] arrStr = mcQid.split(",");
        String[] arrStrM = Arrays.copyOfRange(arrStr, 1, arrStr.length);
        
        
        mcList.getItems().clear();
        
        List<MultiChoiceQuestion> mQuestions = multiChoiceQService.readQuestions();
        
        
            for (String id : arrStrM) {

                MultiChoiceQuestion eq = multiChoiceQService.findQuestionByID(id);
                String questionContent = eq.getQuestionContent();
                String questionCorrect = eq.getCorrectAnswer();
                String questionFalse = eq.getFalseAnswer();
                //System.out.println("A:    " + questionAnswer);
                mcListArray.addAll("Question: "+questionContent+"\n"
                +"Correct Answer: "+questionCorrect+"\n"
                +"Wrong Answers: "+questionFalse+"\n"
                );
            }
        
        //leftList.addAll("Multiple Choice","Fill in the Blank","Matching","True/False","Short Answer","Essay");
        
        mcList.setItems(mcListArray);
        
        }
        catch(NullPointerException e)
        {
            
        }
    }
     public void addFIBText()
    {
          
        /*try{
        Test currentTest = testService.returnThisTest();
        String mcQid = currentTest.getMultiChoiceQ();
        
        String[] arrStr = mcQid.split(",");
        String[] arrStrM = Arrays.copyOfRange(arrStr, 1, arrStr.length);
        
        
        fibList.getItems().clear();
        
        List<MultiChoiceQuestion> mQuestions = multiChoiceQService.readQuestions();
        
        
            for (String id : arrStrM) {

                MultiChoiceQuestion eq = multiChoiceQService.findQuestionByID(id);
                String questionContent = eq.getQuestionContent();
                String questionCorrect = eq.getCorrectAnswer();
                String questionFalse = eq.getFalseAnswer();
                //System.out.println("A:    " + questionAnswer);
                fibListArray.addAll("Question: "+questionContent+"\n"
                +"Correct Answer: "+questionCorrect+"\n"
                +"Wrong Answers: "+questionFalse+"\n"
                );
            }
        
        //leftList.addAll("Multiple Choice","Fill in the Blank","Matching","True/False","Short Answer","Essay");
        
        fibList.setItems(fibListArray);
        
        }
        catch(NullPointerException e)
        {
            
        }*/
    }
    public void addMText()
    {
       
        
          
        try{
        Test currentTest = testService.returnThisTest();
        String mQid = currentTest.getMatchingQ();
        
        String[] arrStr = mQid.split(",");
        String[] arrStrM = Arrays.copyOfRange(arrStr, 1, arrStr.length);
        
        
        mList.getItems().clear();
        
        List<MatchingQuestion> mQuestions = matchingQService.readQuestions();
        
        
            for (String id : arrStrM) {

                MatchingQuestion eq = matchingQService.findQuestionByID(id);
                String questionContent = eq.getTerm();
                String questionCorrect = eq.getCorrectAnswer();
                
                //System.out.println("A:    " + questionAnswer);
                mListArray.addAll("Term: "+questionContent
                +" Answer: "+questionCorrect+"\n"
                
                );
            }
        
        //leftList.addAll("Multiple Choice","Fill in the Blank","Matching","True/False","Short Answer","Essay");
        
        mList.setItems(mListArray);
        
        }
        catch(NullPointerException e)
        {
            
        }
    }
    public void addEText()
    {
        
        
          
        try{
        Test currentTest = testService.returnThisTest();
        String mQid = currentTest.getEssayQ();
        
        String[] arrStr = mQid.split(",");
        String[] arrStrM = Arrays.copyOfRange(arrStr, 1, arrStr.length);
        
        
        eList.getItems().clear();
        
        List<EssayQuestion> eQuestions = essayQuestionService.readQuestions();
        
        
            for (String id : arrStrM) {

                EssayQuestion eq = essayQuestionService.findQuestionByID(id);
                String questionContent = eq.getQuestionContent();
                String questionCorrect = eq.getCorrectAnswer();
                
                //System.out.println("A:    " + questionAnswer);
                eListArray.addAll("Question: "+questionContent+"\n"
                +"Answer: "+questionCorrect+"\n"
                
                );
            }
        
        //leftList.addAll("Multiple Choice","Fill in the Blank","Matching","True/False","Short Answer","Essay");
        
        eList.setItems(eListArray);
        
        }
        catch(NullPointerException e)
        {
            
        }
    }
    public void addSAText()
    {
        try{
        Test currentTest = testService.returnThisTest();
        String mQid = currentTest.getShortAnswerQ();
        
        String[] arrStr = mQid.split(",");
        String[] arrStrM = Arrays.copyOfRange(arrStr, 1, arrStr.length);
        
        
        saList.getItems().clear();
        
        List<ShortAnswerQuestion> eQuestions = shortAnswerQService.readQuestions();
        
        
            for (String id : arrStrM) {

                ShortAnswerQuestion eq = shortAnswerQService.findQuestionByID(id);
                String questionContent = eq.getQuestionContent();
                String questionCorrect = eq.getCorrectAnswer();
                
                //System.out.println("A:    " + questionAnswer);
                saListArray.addAll("Question: "+questionContent+"\n"
                +"Answer: "+questionCorrect+"\n"
                
                );
            }
        
        //leftList.addAll("Multiple Choice","Fill in the Blank","Matching","True/False","Short Answer","Essay");
        
        saList.setItems(saListArray);
        
        }
        catch(NullPointerException e)
        {
            
        }
    }
    public void addTFText()
    {
        try{
        Test currentTest = testService.returnThisTest();
        String mQid = currentTest.getTrueFalseQ();
        
        String[] arrStr = mQid.split(",");
        String[] arrStrM = Arrays.copyOfRange(arrStr, 1, arrStr.length);
        
        
        tfList.getItems().clear();
        
        List<TrueFalseQuestion> eQuestions = trueFalseQService.readQuestions();
        
        
            for (String id : arrStrM) {

                TrueFalseQuestion eq = trueFalseQService.findQuestionByID(id);
                String questionContent = eq.getQuestionContent();
                String questionCorrect = eq.getCorrectAnswer();
                
                //System.out.println("A:    " + questionAnswer);
                tfListArray.addAll("Question: "+questionContent+"\n"
                +"Answer: "+questionCorrect+"\n"
                
                );
            }
        
        //leftList.addAll("Multiple Choice","Fill in the Blank","Matching","True/False","Short Answer","Essay");
        
        tfList.setItems(tfListArray);
        
        }
        catch(NullPointerException e)
        {
            
        }
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
