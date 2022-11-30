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

import java.io.BufferedReader;

import java.io.File;

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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
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
    public static WebEngine engine;
    public static int refresh = 0;
    @FXML
    private WebView viewer;
    @FXML
    private VBox mainVbox;
    @FXML
    private Button add, addMC, editMC, editM, editFIB, editSA, editE, editTF;
    @FXML
    private Button addTF, addE, addM, addFIB, addSA;
    @FXML
    private Button publish;
    @FXML
    private TextField mcP, mP, tfP, eP, saP, fibP;
    @FXML
    private ComboBox questionType;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Label error;

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
    public String path = "src\\main\\resources\\";
    public String pathTo = "";


    public TestMakerController(UserService userService, TestService testService, FxWeaver fxWeaver, MultiChoiceQService multiChoiceQService, MatchingQService matchingQService, EssayQuestionService essayQuestionService, ShortAnswerQService shortAnswerQService, TrueFalseQService trueFalseQService) {
        this.testService = testService;
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.multiChoiceQService = multiChoiceQService;
        this.matchingQService = matchingQService;
        this.essayQuestionService = essayQuestionService;
        this.shortAnswerQService = shortAnswerQService;
        this.trueFalseQService = trueFalseQService;
    }

    public void refresh() {

        engine.reload(); //  reload html

        // This checks to see if the current test even has
        // questions of those types.
        // Avoids unnecessary code execution
        Test thisTest = testService.returnThisTest();
        if (thisTest.getEssayQ() != null) {
            addEText();
        }
        if (thisTest.getShortAnswerQ() != null) {
            addSAText();
        }
        if (thisTest.getTrueFalseQ() != null) {
            addTFText();
        }
        if (thisTest.getMatchingQ() != null) {
            addMText();
        }
        if (thisTest.getMultiChoiceQ() != null) {
            addMCText();
        }
//        if(thisTest.getFIBQ() != null){
//            addFIBText();
//        }
    }

    @FXML
    public void initialize() throws IOException {
        Test currentTest = testService.returnThisTest();
        String testName = currentTest.getTestName();
        String cSection = "";
        String cClass = "";
        int count = 0;
        //need to check current section and class
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "temp.txt"));
            String line = reader.readLine();
            while (line != null) {

                System.out.println(line);
                // read next line
                if (count == 0) {
                    cClass = line;
                }
                if (count == 1) {
                    cSection = line;
                }
                line = reader.readLine();
                count++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        path = "src\\main\\resources\\";
        //Files.deleteIfExists(Paths.get("temp.txt"));
        System.out.println(cClass + " " + cSection);

        path = path + "\\" + cClass + "\\" + cSection + "\\";

        File g = new File(path + testName);
      //  if()

        File f = QuestionHTMLHelper.createNewFile(testName);

        //createTest(path + testName, testName);
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
                pickQuestion();
            } catch (IOException ex) {
                Logger.getLogger(TestMakerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        this.publish.setOnAction(actionEvent -> {

            try {
                setPoints();
                goToMainScreen();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        this.addMC.setOnAction(actionEvent -> {
            addMCScene();
        });
        this.editMC.setOnAction(actionEvent -> {
            editMCScene();
        });
        this.editM.setOnAction(actionEvent -> {
            editMScene();
        });
        this.editFIB.setOnAction(actionEvent -> {
            editFIBScene();
        });
        this.editTF.setOnAction(actionEvent -> {
            editTFScene();
        });
        this.editE.setOnAction(actionEvent -> {
            editEScene();
        });
        this.editSA.setOnAction(actionEvent -> {
            editSAScene();
        });
        this.addM.setOnAction(actionEvent -> {
            addMScene();
        });
        this.addTF.setOnAction(actionEvent -> {
            addTFScene();
        });
        this.addSA.setOnAction(actionEvent -> {
            addSAScene();
        });
        this.addE.setOnAction(actionEvent -> {
            addEScene();
        });
        this.addFIB.setOnAction(actionEvent -> {
            addFIBScene();
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

    /**
     * sets points in HTML by replacing divs with points
     *
     * @throws IOException
     */
    public void setPoints() throws IOException {
        Test thisTest = testService.returnThisTest();
        String testFile = thisTest.getTestName();

        String mcPoints = mcP.getText();
        String matchPoints = mP.getText();
        // String fibPoints = fibP.getText();
        String essayPoints = eP.getText();
        String shortAnsPoints = saP.getText();
        String trueFalsePoints = tfP.getText();

        if (thisTest.getEssayQ() != null) {
            if (!"".equals(essayPoints) && essayPoints.matches("(0|[1-9]\\d*)")) {
                addHTML(testFile, "essayPoints", essayPoints);
            }
        }
        if (thisTest.getShortAnswerQ() != null) {
            if (!"".equals(shortAnsPoints) && (shortAnsPoints.matches("(0|[1-9]\\d*)"))) {
                addHTML(testFile, "saPoints", shortAnsPoints);
            }
        }
        if (thisTest.getTrueFalseQ() != null) {
            if (!"".equals(trueFalsePoints) && trueFalsePoints.matches("(0|[1-9]\\d*)")) {
                addHTML(testFile, "tfPoints", trueFalsePoints);
            }
        }
        if (thisTest.getMatchingQ() != null) {
            if (!"".equals(matchPoints) && matchPoints.matches("(0|[1-9]\\d*)")) {
                addHTML(testFile, "matchPoints", matchPoints);
            }
        }
        if (thisTest.getMultiChoiceQ() != null) {
            if (!"".equals(mcPoints) && mcPoints.matches("(0|[1-9]\\d*)")) {
                addHTML(testFile, "mcPoints", mcPoints);
            }
        }
    }

    public void addHTML(String file, String divID, String points) throws IOException {
        File templateFile = new File(path + file);
        File newFile = new File(path + file);
        if(!newFile.exists()){
            Files.copy(templateFile.toPath(), newFile.toPath()); // copy current version of file
        }

        File keyTemplateFile = new File(path + "KEY_" + file);
        File newKeyFile = new File(path + "KEY_" + file);
        if(!newKeyFile.exists()){
            Files.copy(keyTemplateFile.toPath(), newKeyFile.toPath()); // copy current version of file
        }

        // String addPointsSect
        String keyHtmlString = Files.readString(newKeyFile.toPath());
        String testHtmlString = Files.readString(newFile.toPath());
        String startSect = "<div id = \"" + divID + "\">";
        String endSect = "</div>";
        int sectLength = startSect.length();
        String addedHTML = "(" + points + " points per question)";
        getReplacement(newFile, newKeyFile, addedHTML, testHtmlString, startSect, endSect, sectLength, keyHtmlString);
    }

    public void getReplacement(File newFile, File newKeyFile, String addHTML, String htmlString, String startSection, String endMCSect, int sectLength, String keyHtmlString) throws FileNotFoundException {
        int startIndex = htmlString.indexOf(startSection);
        int endIndex = htmlString.indexOf(endMCSect, startIndex);
        String replaceHTML = htmlString.substring(startIndex + sectLength, endIndex);  // inbetween section tags. need to be copied and appended to

        htmlString = htmlString.replace(replaceHTML, addHTML);
        PrintWriter printWriter = new PrintWriter(newFile);
        printWriter.println(htmlString);
        printWriter.close();

        int keyStartIndex = keyHtmlString.indexOf(startSection);
        int keyEndIndex = keyHtmlString.indexOf(endMCSect, keyStartIndex);
        String keyReplaceHTML = keyHtmlString.substring(keyStartIndex + sectLength, keyEndIndex);  // inbetween section tags. need to be copied and appended to

        keyHtmlString = keyHtmlString.replace(keyReplaceHTML, addHTML);
        PrintWriter keyPrintWriter = new PrintWriter(newKeyFile);
        keyPrintWriter.println(keyHtmlString);
        keyPrintWriter.close();

        engine.reload(); //  reload html
    }

    public void goToMainScreen() {
        FxControllerAndView<MainController, VBox> mainControllerAndView
                = fxWeaver.load(MainController.class);
        mainControllerAndView.getController().show(getCurrentStage());
    }

    public void Qorder() {
        System.out.println("Qorder");
        FxControllerAndView<questionOrderingController, VBox> QuestionOrderingControllerAndView
                = fxWeaver.load(questionOrderingController.class);
        QuestionOrderingControllerAndView.getController().show(getCurrentStage());
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
    public void addMCText() {
        try {
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
                mcListArray.addAll("Question: " + questionContent + "\n"
                        + "Correct Answer: " + questionCorrect + "\n"
                        + "Wrong Answers: " + questionFalse + "\n"
                );
            }

            //leftList.addAll("Multiple Choice","Fill in the Blank","Matching","True/False","Short Answer","Essay");

            mcList.setItems(mcListArray);

        } catch (NullPointerException e) {

        }
    }

    public void addFIBText() {

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

    public void addMText() {
        try {
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
                mListArray.addAll("Term: " + questionContent
                        + " Answer: " + questionCorrect + "\n"

                );
            }
            //leftList.addAll("Multiple Choice","Fill in the Blank","Matching","True/False","Short Answer","Essay");

            mList.setItems(mListArray);
        } catch (NullPointerException e) {

        }
    }

    public void addEText() {
        try {
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
                eListArray.addAll("Question: " + questionContent + "\n"
                        + "Answer: " + questionCorrect + "\n"

                );
            }

            //leftList.addAll("Multiple Choice","Fill in the Blank","Matching","True/False","Short Answer","Essay");

            eList.setItems(eListArray);

        } catch (NullPointerException e) {

        }
    }

    public void addSAText() {
        try {
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
                saListArray.addAll("Question: " + questionContent + "\n"
                        + "Answer: " + questionCorrect + "\n"

                );
            }

            //leftList.addAll("Multiple Choice","Fill in the Blank","Matching","True/False","Short Answer","Essay");

            saList.setItems(saListArray);

        } catch (NullPointerException e) {

        }
    }

    public void addTFText() {
        try {
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
                tfListArray.addAll("Question: " + questionContent + "\n"
                        + "Answer: " + questionCorrect + "\n"

                );
            }

            //leftList.addAll("Multiple Choice","Fill in the Blank","Matching","True/False","Short Answer","Essay");

            tfList.setItems(tfListArray);

        } catch (NullPointerException e) {

        }
    }

    /**
     * Add question from database
     */
    public void addMCScene() {
        FxControllerAndView<addMCController, VBox> AddMCControllerAndView
                = fxWeaver.load(addMCController.class);
        AddMCControllerAndView.getController().show(getCurrentStage());

    }

    public void editMCScene() {
        FxControllerAndView<editMCController, VBox> EditMCControllerAndView
                = fxWeaver.load(editMCController.class);
        EditMCControllerAndView.getController().show(getCurrentStage());
    }

    public void editSAScene() {
        FxControllerAndView<editSAController, VBox> EditSAControllerAndView
                = fxWeaver.load(editSAController.class);
        EditSAControllerAndView.getController().show(getCurrentStage());
    }

    public void editTFScene() {
        FxControllerAndView<editTFController, VBox> EditTFControllerAndView
                = fxWeaver.load(editTFController.class);
        EditTFControllerAndView.getController().show(getCurrentStage());
    }

    public void editFIBScene() {
        FxControllerAndView<editFIBController, VBox> EditFIBControllerAndView
                = fxWeaver.load(editFIBController.class);
        EditFIBControllerAndView.getController().show(getCurrentStage());
    }

    public void editMScene() {
        FxControllerAndView<editMController, VBox> EditMControllerAndView
                = fxWeaver.load(editMController.class);
        EditMControllerAndView.getController().show(getCurrentStage());
    }

    public void editEScene() {
        FxControllerAndView<editEController, VBox> EditEControllerAndView
                = fxWeaver.load(editEController.class);
        EditEControllerAndView.getController().show(getCurrentStage());
    }

    public void addEScene() {
        FxControllerAndView<addEController, VBox> AddEControllerAndView
                = fxWeaver.load(addEController.class);
        AddEControllerAndView.getController().show(getCurrentStage());
    }

    public void addSAScene() {
        FxControllerAndView<addShortAnswerController, VBox> AddSAControllerAndView
                = fxWeaver.load(addShortAnswerController.class);
        AddSAControllerAndView.getController().show(getCurrentStage());
    }

    public void addTFScene() {
        FxControllerAndView<addTFController, VBox> AddTFControllerAndView
                = fxWeaver.load(addTFController.class);
        AddTFControllerAndView.getController().show(getCurrentStage());
    }

    public void addFIBScene() {
        FxControllerAndView<addFIBController, VBox> AddFIBControllerAndView
                = fxWeaver.load(addFIBController.class);
        AddFIBControllerAndView.getController().show(getCurrentStage());
    }

    public void addMScene() {
        FxControllerAndView<addMController, VBox> AddMontrollerAndView
                = fxWeaver.load(addMController.class);
        AddMontrollerAndView.getController().show(getCurrentStage());
    }


}
