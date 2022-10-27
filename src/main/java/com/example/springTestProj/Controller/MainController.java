//package com.example.springTestProj.Controller;
//
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import net.rgielen.fxweaver.core.FxControllerAndView;
//import net.rgielen.fxweaver.core.FxWeaver;
//import net.rgielen.fxweaver.core.FxmlView;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//@FxmlView // equal to: @FxmlView("MainController.fxml")
//public class MainController {
//    private Stage stage;
//
//    private final String greeting;
//    private final FxWeaver fxWeaver;
//    private final FxControllerAndView<LoginController, VBox> dialog;
//
//    @FXML
//    public Label label;
//    @FXML
//    public Button helloButton;
//    @FXML
//    public Button openSimpleDialogButton;
//
//
//    public MainController(@Value("${spring.application.demo.greeting}") String greeting,
//                          FxWeaver fxWeaver,
//                          FxControllerAndView<LoginController, VBox> dialog) {
//        this.greeting = greeting;
//        this.fxWeaver = fxWeaver;
//        this.dialog = dialog;
//    }
//
//    @FXML
//    public void initialize() {
//        helloButton.setOnAction(
//                actionEvent -> this.label.setText(greeting)
//        );
///*
//        openSimpleDialogButton.setOnAction(
//                actionEvent -> fxWeaver.loadController(DialogController.class).show()
//        );
//*/
//    }
//    public void show() {
//        stage.show();
//    }
//
//
//
//
//}
