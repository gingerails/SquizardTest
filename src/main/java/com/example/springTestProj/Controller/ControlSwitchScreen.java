package com.example.springTestProj.Controller;


import com.example.springTestProj.Controller.CreateQuestionWindows.ControlDialogBoxes;
import javafx.stage.Stage;


/**
 * Attempt at abstraction
 */
public interface ControlSwitchScreen extends ControlDialogBoxes {

    Stage getCurrentStage();

    void show(Stage thisStage);


}
