/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.springTestProj.Controller.CreateQuestionWindows;

import com.example.springTestProj.Controller.CreateQuestionWindows.ControlDialogBoxes;
import com.example.springTestProj.Service.UserService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author Orames
 */
@Component
@FxmlView("/essayQuestion.fxml")
public class EssayQuestionController implements ControlDialogBoxes {
    private final UserService userService;
    private final FxWeaver fxWeaver;
    private Stage stage;

    @FXML
    private VBox essayQuestionBox;
    @FXML
    private Button add;
    
   
    public EssayQuestionController(UserService userService, FxWeaver fxWeaver) {
        System.out.println("essay Controller");
        this.fxWeaver = fxWeaver;
        this.userService = userService;
    }
    
    public void initialize () {
        this.stage = new Stage();
        stage.setTitle("Add Essay Question");
        stage.setScene(new Scene(essayQuestionBox));
        this.add.setOnAction(actionEvent -> {
            System.out.print("Add question button pressed");
            add();
        });
    }

    @Override
    public Stage getCurrentStage() {
        Node node = add.getParent();
        Stage currentStage = (Stage) node.getScene().getWindow();
        return currentStage;
    }

    @Override
    public void show(Stage thisStage) {
//        this.stage = thisStage;
//        stage.setScene(new Scene(mainVbox));
//        System.out.println("Showing essay question screen");
        stage.show();
        this.stage.centerOnScreen();
    }

    public void add() {
     // gets the current stage, sets the scene w the create account control/view (fxweaver), then updates stage w that scene
            
    }
}
