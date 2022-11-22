/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.springTestProj.Controller;

import com.example.springTestProj.Entities.Courses;
import com.example.springTestProj.Repository.CourseRepository;
import com.example.springTestProj.Service.CourseService;
import com.example.springTestProj.Service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * FXML Controller class
 *
 * @author Orames
 */
@Component
@FxmlView("/Main.fxml")
public class MainController implements ControlSwitchScreen {

    private final CourseService courseService;
    private final UserService userService;
    private final FxWeaver fxWeaver;
    CourseRepository courseRepository;
    private Stage stage;

    @FXML
    private VBox mainVbox;
    @FXML
    private Button addTest;
    @FXML
    private Button preview1;
    @FXML
    private Button addCourseButton;
    @FXML
    private ComboBox displayClass;


    public MainController(UserService userService, FxWeaver fxWeaver, CourseService courseService) {
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.courseService = courseService;
    }

    public void getDatabaseCourses() {
        displayClass.getItems()
                .clear();
        List<Courses> dropdownCourseList = courseService.readCourses();
        for (Courses course : dropdownCourseList) {
            String sectionsAsString = course.getSections();
            if (course.getSections() == null) {
                sectionsAsString = "";
            }
            String[] sectionsList = sectionsAsString.split(",");
            ArrayList<String> displayList = new ArrayList<>();
            for (String currentSection : sectionsList) {


                String statementString = course.getCoursesPrimaryKey()
                        .getCourseNum() + " " + currentSection;
                System.out.println(currentSection);
                System.out.println(statementString);

                displayClass.getItems()
                        .addAll(statementString);

            }
        }


    }

    //String test="cs101,cs202,cs303";

    @FXML
    public void initialize() {

        getDatabaseCourses();
        this.addTest.setOnAction(actionEvent -> {
            loadAddTestScreen();
        });
        this.addCourseButton.setOnAction((ActionEvent actionEvent) -> {
            loadAddCourseScreen();
            //getDatabaseCourses();
            initialize();
        });
        this.preview1.setOnAction(actionEvent -> {
            loadpreview("/website.html");
        });


    }

    @Override
    public Stage getCurrentStage() {
        Node node = addTest.getParent(); // cant set this in init bc it could cause a null pointer :-\ probably needs its own method
        Stage currentStage = (Stage) node.getScene()
                .getWindow();
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
     * gets the current stage, sets the scene w the create account control/view (fxweaver), then updates stage w that scene
     */


    public void loadAddTestScreen() {
        Stage currentStage = getCurrentStage();
        FxControllerAndView<CreateTestController, VBox> createTestControllerAndView =
                fxWeaver.load(CreateTestController.class);
        createTestControllerAndView.getController()
                .show(getCurrentStage());
    }

    public void loadpreview(String name) {
        Stage nstage = new Stage();

        WebView browser = new WebView();
        WebEngine engine = browser.getEngine();
        URL url = this.getClass()
                .getResource(name);
        engine.load(url.toString());


        StackPane sp = new StackPane();
        sp.getChildren()
                .add(browser);

        Scene root = new Scene(sp);

        nstage.setScene(root);
        nstage.show();
    }

    public void loadAddCourseScreen() {

        FxControllerAndView<AddCourseController, VBox> addCourseControllerAndView =
                fxWeaver.load(AddCourseController.class);
        addCourseControllerAndView.getController()
                .show(getCurrentStage());


    }
}
