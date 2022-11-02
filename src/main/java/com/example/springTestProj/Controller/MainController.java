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
@FxmlView("/Main.fxml")
public class MainController implements ControlSwitchScreen {
    private final UserService userService;
    private final FxWeaver fxWeaver;
    private Stage stage;

    @FXML
    private VBox mainVbox;
    @FXML
    private Button addTest;
    @FXML
    private Button addCourseButton;
    
   
    public MainController(UserService userService, FxWeaver fxWeaver) {
        System.out.println("Main Controller");
        this.fxWeaver = fxWeaver;
        this.userService = userService;
    }

    @FXML
    public void initialize () {

        this.addTest.setOnAction(actionEvent -> {
            System.out.print("Add test button pressed");
            loadAddTestScreen();
        });
        this.addCourseButton.setOnAction(actionEvent -> {
            System.out.print("Add course button pressed");
            loadAddCourseScreen();
        });
    }

    @Override
    public Stage getCurrentStage() {
        Node node = addTest.getParent(); // cant set this in init bc it could cause a null pointer :-\ probably needs its own method
        Stage currentStage = (Stage) node.getScene().getWindow();
        return currentStage;
    }

    @Override
    public void show(Stage thisStage) {
        this.stage = thisStage;
        stage.setScene(new Scene(mainVbox));
        System.out.println("Showing main screen");
        stage.show();
        this.stage.setMaximized(true);
        this.stage.centerOnScreen();
    }

    /**
     *  gets the current stage, sets the scene w the create account control/view (fxweaver), then updates stage w that scene
     */
    public void loadAddTestScreen() {
        Stage currentStage = getCurrentStage();
        FxControllerAndView<TestMakerController, VBox> testMakerControllerAndView =
                fxWeaver.load(TestMakerController.class);
        testMakerControllerAndView.getController().show(getCurrentStage());
    }
    public void loadAddCourseScreen() {
        
        FxControllerAndView<AddCourseController, VBox> addCourseControllerAndView =
                fxWeaver.load(AddCourseController.class);
        addCourseControllerAndView.getController().show(getCurrentStage());
    }
//    public void loadAddCourseScreen() {
//
//        FxControllerAndView<AddCourseController, VBox> addCourseControllerAndView =
//                fxWeaver.load(AddCourseController.class);
//        addCourseControllerAndView.getController().show(getCurrentStage());
//    }
//    public void loadAddCourseScreen() {
//
//        FxControllerAndView<AddCourseController, VBox> addCourseControllerAndView =
//                fxWeaver.load(AddCourseController.class);
//        addCourseControllerAndView.getController().show(getCurrentStage());
//    }
}
