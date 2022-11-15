package com.example.springTestProj.Controller.CreateQuestionWindows;

import com.example.springTestProj.Controller.TestMakerController;
import com.example.springTestProj.Service.UserService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/mcQuestion.fxml")
public class McQuestionController implements ControlDialogBoxes {

    private final UserService userService;
    private final FxWeaver fxWeaver;
    private Stage stage;

    @FXML
    private Button add;
    @FXML
    private TextField questionField;
    @FXML
    private TextField choice1Field;
    @FXML
    private TextField choice2Field;
    @FXML
    private TextField choice3Field;
    @FXML
    private TextField choice4Field;
    @FXML
    private VBox mcQuestionBox;

    public String path="src\\main\\resources\\";
    public McQuestionController(UserService userService, FxWeaver fxWeaver) {
        System.out.println("essay Controller");
        this.fxWeaver = fxWeaver;
        this.userService = userService;
    }

    @FXML
    public void initialize () {
        this.stage = new Stage();
        stage.setTitle("Add Multiple Choice Question");
        stage.setScene(new Scene(mcQuestionBox));
        this.add.setOnAction(actionEvent -> {
            System.out.print("Add question button pressed");
            stage.close();
            add(path+"test.html");
           
        });
    }


    @Override
    public void show(Stage thisStage) {
//        this.stage = thisStage;
//        stage.setScene(new Scene(mainVbox));
//        System.out.println("Showing essay question screen");
        stage.show();
        this.stage.centerOnScreen();
    }

//    @Override
//    public <T> void add(T t) {
//
//    }

    public void add(String file) {
        
        // gets the current stage, sets the scene w the create account control/view (fxweaver), then updates stage w that scene
        
        try ( FileWriter f = new FileWriter(file, true);  BufferedWriter b = new BufferedWriter(f);  PrintWriter p = new PrintWriter(b);) {

            p.println("<hr />" + "\n"
                    + "<p><span style='font-size:16px'><strong>" + questionField.getText() + "</strong></span></p>" + "\n"
                    + "<p><span style='font-size:16px'>a. " + choice1Field.getText() + "</span></p>" + "\n"
                    + "<p><span style='font-size:16px'>b. " + choice1Field.getText() + "</span></p>" + "\n"
                    + "<p><span style='font-size:16px'>c. " + choice1Field.getText() + "</span></p>" + "\n"
                    + "<p><span style='font-size:16px'>d. " + choice1Field.getText() + "</span></p>" + "\n");
            b.close();
            p.close();
            f.close();
            //engine.reload();  
        } catch (IOException i) {
            i.printStackTrace();
        }
    
    }
}
