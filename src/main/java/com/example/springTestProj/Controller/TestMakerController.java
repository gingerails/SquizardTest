package com.example.springTestProj.Controller;

import com.example.springTestProj.Controller.CreateQuestionWindows.EssayQuestionController;
import com.example.springTestProj.Controller.CreateQuestionWindows.FibQuestionController;
import com.example.springTestProj.Controller.CreateQuestionWindows.MQuestionController;
import com.example.springTestProj.Controller.CreateQuestionWindows.McQuestionController;
import com.example.springTestProj.Controller.CreateQuestionWindows.ShortQuestionController;
import com.example.springTestProj.Controller.CreateQuestionWindows.TfQuestionController;
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
import java.lang.reflect.Field;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private final FxWeaver fxWeaver;
    private Stage stage;

    @FXML
    private WebView viewer;
    private WebEngine engine;
    @FXML
    private VBox mainVbox;
    @FXML
    private Button add;
    @FXML
    private Button publish;
    @FXML
    private ComboBox questionType;

    public String path = "src\\main\\resources\\";

    File f = new File(path + "test.html");

    public TestMakerController(UserService userService, FxWeaver fxWeaver) {
        //System.out.println("Test maker Controller");
        this.fxWeaver = fxWeaver;
        this.userService = userService;
    }

    @FXML
    public void initialize() throws IOException {

        createTest(path + "test.html", "Test Name");
        //webviewer
        engine = viewer.getEngine();
        engine.load(f.toURI().toString());

        questionType.getItems().addAll(
                "Essay",
                "Multiple Choice",
                "Matching",
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
        this.publish.setOnAction(actionEvent -> {

            publish(path + "test.html");

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
            FxControllerAndView<McQuestionController, VBox> McQuestionControllerAndView
                    = fxWeaver.load(McQuestionController.class);
            McQuestionControllerAndView.getController().show(getCurrentStage());

        }
        if ("Matching".equals(qType) == true) {
            System.out.println("Matching");
            FxControllerAndView<MQuestionController, VBox> mQuestionControllerAndView
                    = fxWeaver.load(MQuestionController.class);
            mQuestionControllerAndView.getController().show(getCurrentStage());
        }
        if ("Fill in Blank".equals(qType) == true) {
            System.out.println("FIB");
            FxControllerAndView<FibQuestionController, VBox> fibQuestionControllerAndView
                    = fxWeaver.load(FibQuestionController.class);
            fibQuestionControllerAndView.getController().show(getCurrentStage());
        }
        if ("True/False".equals(qType) == true) {
            System.out.println("T/F");
            FxControllerAndView<TfQuestionController, VBox> tfQuestionControllerAndView
                    = fxWeaver.load(TfQuestionController.class);
            tfQuestionControllerAndView.getController().show(getCurrentStage());
        }
        if ("Short Answer".equals(qType) == true) {
            System.out.println("Short");
            FxControllerAndView<ShortQuestionController, VBox> shortQuestionControllerAndView
                    = fxWeaver.load(ShortQuestionController.class);
            shortQuestionControllerAndView.getController().show(getCurrentStage());
        }
    }
}
