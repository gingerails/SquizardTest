//package com.example.springTestProj.Controller;
//
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//public interface ControllerFactory {
//
//
//     default Stage getCurrentStage() {
//        Node node = button.getParent(); // cant set this in init bc it could cause a null pointer :-\ probably needs its own method
//        Stage currentStage = (Stage) node.getScene().getWindow();
//        return currentStage;
//    }
//
//
//    default void show(Stage thisStage) {
//        this.stage = thisStage;
//        stage.setScene(new Scene(loginVbox));
//        System.out.println("Showing login screen");
//        stage.show();
//    }
//
//
//}
