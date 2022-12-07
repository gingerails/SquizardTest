package com.example.springTestProj.Application;

import com.example.springTestProj.Controller.LoginController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PrimaryStageInitializer implements ApplicationListener<StageReadyEvent> {

    private final FxWeaver fxWeaver;
    //This  function initializes fx weaver for the login screen
    //FX Weaver is a Supportive tooling for using JavaFX together with Spring
    @Autowired
    public PrimaryStageInitializer(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }
    //This  function iniitilizing and loads the login screen
    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.stage;
        Scene scene = new Scene(fxWeaver.loadView(LoginController.class));
        stage.setScene(scene);
        stage.show();
    }
}
