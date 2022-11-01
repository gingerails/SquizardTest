/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.springTestProj.Controller;

import com.example.springTestProj.Controller.CreateQuestionWindows.EssayQuestionController;
import com.example.springTestProj.Service.UserService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
@FxmlView("/testMaker.fxml")
public class TestMakerController implements ControlSwitchScreen {
    private final UserService userService;
    private final FxWeaver fxWeaver;
    private Stage stage;

    @FXML
    private VBox mainVbox;
    @FXML
    private Button add;
    @FXML
    private ComboBox questionType;
   
    public TestMakerController(UserService userService, FxWeaver fxWeaver) {
        System.out.println("Test maker Controller");
        this.fxWeaver = fxWeaver;
        this.userService = userService;
    }
    
    public void initialize () {

         questionType.getItems().addAll(
                "Essay",
                "Multiple Choice",
                "Matching",
                "Fill in Blank",
                "True/False"
        );
        this.add.setOnAction(actionEvent -> {
             try {
                 //System.out.print("essay Question");
                 pickQuestion();
             } catch (IOException ex) {
                 Logger.getLogger(TestMakerController.class.getName()).log(Level.SEVERE, null, ex);
             }
        });
      
    }

    @Override
    public Stage getCurrentStage() {
        Node node = add.getParent(); // cant set this in init bc it could cause a null pointer :-\ probably needs its own method
        Stage currentStage = (Stage) node.getScene().getWindow();
        return currentStage;
    }

    @Override
    public void show(Stage thisStage) {
        this.stage = thisStage;
        stage.setScene(new Scene(mainVbox));
        System.out.println("Showing test creation screen");
        this.stage.setMaximized(false);
        stage.show();
        this.stage.setMaximized(true);
        this.stage.centerOnScreen();
    }

    public void pickQuestion() throws IOException
    {
        
        String qType = (String)questionType.getValue();
        System.out.println(qType);
        
        if("Essay".equals(qType)==true)
        {
            System.out.println("essay");
            //changeScene("/essayQuestion.fxml");
           
            
            FxControllerAndView<EssayQuestionController, VBox> essayQuestionControllerAndView =
                    fxWeaver.load(EssayQuestionController.class);
            essayQuestionControllerAndView.getController().show(getCurrentStage());
        }
         if("Multiple Choice".equals(qType)==true)
        {
            System.out.println("Multiple Choice");
            //changeScene("/mcQuestion.fxml");
        }
         if("Matching".equals(qType)==true)
        {
            System.out.println("Matching");
            //changeScene("/mQuestion.fxml");
        }
          if("Fill in Blank".equals(qType)==true)
        {
            System.out.println("FIB");
            //changeScene("/fibQuestion.fxml");
        }
          if("True/False".equals(qType)==true)
        {
            System.out.println("FIB");
            //changeScene("/tfQuestion.fxml");
        }
    }
}
