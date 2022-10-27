//package com.example.springTestProj;
//
//import com.example.springTestProj.Controller.LoginController;
//import com.example.springTestProj.Controller.SimpleController;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import net.rgielen.fxweaver.core.FxWeaver;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationListener;
//
//import com.example.springTestProj.SquizardApplication.StageReadyEvent;
//
//@Component
//public class StageInitializer implements ApplicationListener<StageReadyEvent>{
//    private final String applicationTitle;
//
//    private final FxWeaver fxWeaver;
//
//    public StageInitializer(@Value("${spring.application.ui.title}") String applicationTitle,
//                            FxWeaver fxWeaver) {
//        this.applicationTitle = applicationTitle;
//        this.fxWeaver = fxWeaver;
//    }
//
//    @Override
//    public void onApplicationEvent(StageReadyEvent event) {
//        Stage stage = event.getStage();
//        stage.setScene(new Scene(fxWeaver.loadView(LoginController.class)));
//        stage.setTitle(applicationTitle);
//        stage.show();
//    }
//
//
//}
