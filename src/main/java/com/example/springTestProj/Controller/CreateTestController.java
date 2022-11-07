/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.springTestProj.Controller;

import com.example.springTestProj.Service.UserService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author Orames
 */
@Component
@FxmlView("/createTest.fxml")
public class CreateTestController implements ControlSwitchScreen {
    private final UserService userService;
    private final FxWeaver fxWeaver;
    private Stage stage;

    @FXML
    private VBox mainVbox;
    @FXML
    private Button createTest;
    
   
    public CreateTestController(UserService userService, FxWeaver fxWeaver) {
        System.out.println("Create Test Controller");
        this.fxWeaver = fxWeaver;
        this.userService = userService;
    }

    @FXML
    public void initialize () {

        this.createTest.setOnAction(actionEvent -> {
            System.out.print("create button pressed");
            loadTestMaker();
        });
    }

    @Override
    public Stage getCurrentStage() {
        Node node = createTest.getParent(); // cant set this in init bc it could cause a null pointer :-\ probably needs its own method
        Stage currentStage = (Stage) node.getScene().getWindow();
        return currentStage;
    }

    @Override
    public void show(Stage thisStage) {
        this.stage = thisStage;
        stage.setScene(new Scene(mainVbox));
        System.out.println("Showing create test screen");
        stage.show();
        this.stage.centerOnScreen();
    }


    public void loadTestMaker() {
        Stage currentStage = getCurrentStage();
        FxControllerAndView<TestMakerController, VBox> testMakerControllerAndView
                = fxWeaver.load(TestMakerController.class);
        testMakerControllerAndView.getController().show(getCurrentStage());
    }
}
