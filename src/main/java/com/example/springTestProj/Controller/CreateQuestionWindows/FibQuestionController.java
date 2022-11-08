package com.example.springTestProj.Controller.CreateQuestionWindows;

import com.example.springTestProj.Service.UserService;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        //    add();
        });
    }

//    @Override
//    public <T> void add(T t) {
//
//    }


}
