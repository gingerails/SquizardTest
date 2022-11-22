/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.springTestProj.Controller;

import com.example.springTestProj.Entities.Course;
import com.example.springTestProj.Entities.Section;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.CourseService;
import com.example.springTestProj.Service.SectionService;
import com.example.springTestProj.Service.TestService;
import com.example.springTestProj.Service.UserService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

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
    private final TestService testService;
    private final SectionService sectionService;
    private Stage stage;
    @FXML
    private VBox mainVbox;
    @FXML
    private Button createTest;
    @FXML
    private ComboBox section;
    @FXML
    private ComboBox classes;
    @FXML
    private TextField name;
    @FXML
    private CheckBox analytic;
    @FXML
    private Label error;


    public CreateTestController(UserService userService, FxWeaver fxWeaver, CourseService courseService, TestService testService, SectionService sectionService) {
        //System.out.println("Create Test Controller");
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.courseService = courseService;
        this.testService = testService;
        this.sectionService = sectionService;
    }


    @FXML
    public void initialize() {
        getCoursesInfo();

        this.createTest.setOnAction(actionEvent -> {
            //System.out.print("create button pressed");

            if (name.getText() != ".html" && classes.getValue() != null && section.getValue() != null) {
                saveTest();
                loadTestMaker();
            } else {
                error.setText("ERROR: One or more items not selected.");
            }
        });
        this.classes.setOnAction(actionEvent -> {
            getSectionInfo();
        });
//
    }

    @Override
    public Stage getCurrentStage() {
        Node node = createTest.getParent(); // cant set this in init bc it could cause a null pointer :-\ probably needs its own method
        Stage currentStage = (Stage) node.getScene()
                .getWindow();
        return currentStage;
    }

    @Override
    public void show(Stage thisStage) {
        this.stage = thisStage;
        stage.setScene(new Scene(mainVbox));
        //System.out.println("Showing create test screen");
        stage.show();
        this.stage.centerOnScreen();
    }


    public void loadTestMaker() {
        Stage currentStage = getCurrentStage();
        FxControllerAndView<TestMakerController, VBox> testMakerControllerAndView
                = fxWeaver.load(TestMakerController.class);
        testMakerControllerAndView.getController()
                .show(getCurrentStage());
    }

    public void getCoursesInfo() {
        classes.getItems()
                .clear();
        List<Course> dropdownCourseList = courseService.readCourses();
        for (Course course : dropdownCourseList) {

            String statementString = course.getCourseID()
                    .getNumber();
            //System.out.println(statementString);

            classes.getItems()
                    .addAll(statementString);
        }
    }

    public void saveTest() {
        String fileName = name.getText() + ".html";
        String className = (String) classes.getValue();
        String sectionName = (String) section.getValue();

        Course selectedCourse = courseService.returnCourseByCourseNum(className);
        String courseID = selectedCourse.getCourseID()
                .getUuid();

        if (sectionName == "ALL") {
            List<Section> allSections = sectionService.findCourseSections(courseID);
        } else {
            Section testSection = sectionService.returnSectionBySectionAndCourseID(sectionName, courseID);
            String sectionUUID = testSection.getSectionID()
                    .getUuid();

            // save test
            Test newTest = testService.createTest(fileName, sectionUUID);
            testService.saveTestToRepository(newTest);

            // save section w test
            String currentTests = testSection.getTest();
            String addedTests = currentTests + "," + newTest.getTestUUID();
            testSection.setTest(addedTests);
            sectionService.addTestToSection(testSection);
        }

    }

    public void getSectionInfo() {
        section.getItems()
                .clear();
        section.getItems()
                .addAll("ALL");

        List<Course> dropdownCourseList = courseService.readCourses();
        for (Course course : dropdownCourseList) {
            String sectionsAsString = course.getSections();
            if (course.getSections() == null) {
                sectionsAsString = "";
            }
            String[] sectionsList = sectionsAsString.split(",");
            ArrayList<String> displayList = new ArrayList<>();
            for (String currentSection : sectionsList) {

                //System.out.println("1."+course.getCoursesPrimaryKey().getCourseNum());
                //System.out.println("2."+classes.getValue());
                if (course.getCourseID()
                        .getNumber()
                        .equals(classes.getValue())) {
                    String statementString = currentSection;
                    //System.out.println(currentSection);
                    //System.out.println(statementString);
                    section.getItems()
                            .addAll(statementString);
                    //System.out.println(course.getCoursesPrimaryKey().getCourseNum());
                }

            }

        }
    }
}


