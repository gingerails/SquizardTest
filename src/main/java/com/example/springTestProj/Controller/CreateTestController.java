/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.springTestProj.Controller;

import com.example.springTestProj.Entities.Courses;
import com.example.springTestProj.Service.CourseService;
import com.example.springTestProj.Service.UserService;
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
import com.example.springTestProj.Service.CourseService;
import java.util.ArrayList;
import java.util.List;
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
    private final CourseService courseService;
    private Stage stage;

    @FXML
    private VBox mainVbox;
    @FXML
    private Button createTest;
    @FXML
    private ComboBox section;
    @FXML
    private ComboBox classes;
   
    public CreateTestController(UserService userService, FxWeaver fxWeaver,CourseService courseService) {
        System.out.println("Create Test Controller");
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.courseService=courseService;
    }

    @FXML
    public void initialize () {
        getCoursesInfo();
        getSectionInfo();
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
     public void getCoursesInfo()
    {
       classes.getItems().clear();
      List<Courses> dropdownCourseList = courseService.readCourses();
        for (Courses course : dropdownCourseList) {

                String statementString = course.getCoursesPrimaryKey().getCourseNum();
                System.out.println(statementString);
                
                classes.getItems().addAll(statementString);
        }
    }
     //needs to be completed needs to take data of what is in the class combobox and look for any section numbers
       public void getSectionInfo()
    {
       
                
                section.getItems().addAll("ALL");
    }
}


