package com.example.springTestProj.Controller.CreateQuestionWindows;

import com.example.springTestProj.Service.UserService;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
@FxmlView("/fibQuestion.fxml")
public class FibQuestionController implements ControlDialogBoxes {
    private final UserService userService;
    private final FxWeaver fxWeaver;
    private Stage stage;
    @FXML
    private Button add;

    @FXML
    private Button addGraphicButton;
    @FXML
    private VBox fillInBlankBox;
    @FXML
    private TextField questionField;

    public String path="src\\main\\resources\\";
    public FibQuestionController(UserService userService, FxWeaver fxWeaver) {
        this.userService = userService;
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setTitle("Add Fill-in-Blank Question");
        stage.setScene(new Scene(fillInBlankBox));

        this.add.setOnAction(actionEvent -> {



        });
    }

    @Override
    public void show(Stage thisStage) {
        stage.show();
        this.stage.centerOnScreen();
        this.add.setOnAction(actionEvent -> {
            //System.out.print("Add question button pressed");
            stage.close();
            add(path+"test.html");
        });
    }

//    @Override
//    public <T> void add(T t) {
//
//    }
    public void add(String file) {
        String question=questionField.getText();
        String rs=question.replace("/?/"," __________________ ");
        // gets the current stage, sets the scene w the create account control/view (fxweaver), then updates stage w that scene
        
        try ( FileWriter f = new FileWriter(file, true);  BufferedWriter b = new BufferedWriter(f);  PrintWriter p = new PrintWriter(b);) {

            p.println("<hr />" + "\n"
                   +"<p><strong>Fill in the Blanks</strong></p>"+"\n"

                   +"<p>"+rs+"</p>"+"\n"
            );
            b.close();
            p.close();
            f.close();
            //engine.reload();  
        } catch (IOException i) {
            i.printStackTrace();
        }
    
    }

}
