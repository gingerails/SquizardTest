package com.example.springTestProj.Controller;

import com.example.springTestProj.Service.UserService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import javafx.scene.control.Label;
import javafx.stage.Modality;


@Component
@FxmlView("/addCourse.fxml")
public class AddCourseController implements ControlSwitchScreen {
    private final UserService userService;
    private final FxWeaver fxWeaver;
    private Stage stage;

 

    @FXML
    TextField courseNum;
    @FXML
    TextField courseSection;
    @FXML
    Label error;

    @FXML
    Button add;
    
    @FXML
    VBox addCourseVbox;  // fx:id !!!!!

    public AddCourseController(UserService userService,
                           FxWeaver fxWeaver) {//
        this.userService = userService;
        this.fxWeaver = fxWeaver;
        
    }

    /**
     * initialize
     * automatically called
     */
    @FXML
    public void initialize () {
        this.stage = new Stage();
        stage.setTitle("Add Course/Section");
        stage.setScene(new Scene(addCourseVbox));


        this.add.setOnAction(actionEvent -> {
            System.out.println("Course add button pressed");
            if(courseNum.getText().isBlank()){
                System.out.println("Error: Course left blank");
                error.setText("Error: Course left blank");
            } else if (courseSection.getText().isBlank()){
                String courseNumText = courseNum.getText();
                createCourse(courseNumText);
                stage.close();
            } else {
                createCourseAndSection();
                stage.close();
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
        stage.show();
        this.stage.centerOnScreen();
    }


   

}