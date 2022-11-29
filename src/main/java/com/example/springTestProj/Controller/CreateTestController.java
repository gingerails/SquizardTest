/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.springTestProj.Controller;

import com.example.springTestProj.Entities.Courses;
import com.example.springTestProj.Entities.Section;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.CourseService;
import com.example.springTestProj.Service.SectionService;
import com.example.springTestProj.Service.TestService;
import com.example.springTestProj.Service.UserService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    private Button createTest, backButton;
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

        //System.out.println("Test SECTIONS: " +testUUIDs);
        this.createTest.setOnAction(actionEvent -> {
            //System.out.print("create button pressed");

            int check = 0;
            String tName=name.getText();
            if(tName.equals("")==false)
                {
            if (classes.getValue() != null && section.getValue() != null) {

                if (new File("src\\main\\resources\\" + (String) classes.getValue() + "\\" + (String) section.getValue() + "\\" + name.getText() + ".html").exists() == true) {
                    error.setText("ERROR: Test already exists");
                } else {
                    try {
                        saveTest();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(CreateTestController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    loadTestMaker();
                }
            } else {
                error.setText("ERROR: One or more items not selected");
            }
                }
            else
                {
                    error.setText("ERROR: Name can not be blank");
                }
        });

        this.classes.setOnAction(actionEvent -> {
            getSectionInfo();
        });
        this.backButton.setOnAction(actionEvent -> {
            loadMainScreen();
        });
//
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
        //System.out.println("Showing create test screen");
        stage.show();
        this.stage.centerOnScreen();
    }


    public void loadTestMaker() {
        Stage currentStage = getCurrentStage();
        FxControllerAndView<TestMakerController, VBox> testMakerControllerAndView
                = fxWeaver.load(TestMakerController.class);
        testMakerControllerAndView.getController().show(getCurrentStage());
    }
    public void loadMainScreen() {
        Stage currentStage = getCurrentStage();
        FxControllerAndView<MainController, VBox> mainControllerAndView
                = fxWeaver.load(MainController.class);
        mainControllerAndView.getController().show(getCurrentStage());
    }

    public void getCoursesInfo() {
        classes.getItems().clear();
        List<Courses> dropdownCourseList = courseService.readCourses();
        for (Courses course : dropdownCourseList) {

            String statementString = course.getCoursesPrimaryKey().getCourseNum();
            //System.out.println(statementString);

            classes.getItems().addAll(statementString);
        }
    }

    public void saveTest() throws FileNotFoundException {
        String fileName = name.getText() + ".html";
        String className = (String) classes.getValue();
        String sectionName = (String) section.getValue();

        Courses selectedCourse = courseService.returnCourseByCourseNum(className);
        String courseID = selectedCourse.getCoursesPrimaryKey().getCoursesUUID();

        if (sectionName == "ALL") {
            List<Section> allSections = sectionService.findCourseSections(courseID);
        } else {
            Section testSection = sectionService.returnSectionBySectionAndCourseID(sectionName, courseID);
            String sectionUUID = testSection.getSectionPrimaryKey().getSectionUUID();

            // save test
            Test newTest = testService.createTest(fileName, sectionUUID);
            testService.saveTestToRepository(newTest);

            // save section w test
            String currentTests = testSection.getTest();
            String addedTests = currentTests + "," + newTest.getTestUUID();
            testSection.setTest(addedTests);
            sectionService.addTestToSection(testSection);
        }
         //make a temp file to get data later
        // Open the file.
        PrintWriter out = new PrintWriter("temp.txt"); // Step 2

        // Write the name of four oceans to the file
        out.println((String) classes.getValue());   // Step 3
        out.println((String) section.getValue());

        // Close the file.
        out.close();  // Step 4*/

    }

    public void getSectionInfo() {
        section.getItems().clear();
        section.getItems().addAll("ALL");

        List<Courses> dropdownCourseList = courseService.readCourses();
        for (Courses course : dropdownCourseList) {
            String sectionsAsString = course.getSections();
            if (course.getSections() == null) {
                sectionsAsString = "";
            }
            String[] sectionsList = sectionsAsString.split(",");
            ArrayList<String> displayList = new ArrayList<>();
            for (String currentSection : sectionsList) {

                //System.out.println("1."+course.getCoursesPrimaryKey().getCourseNum());
                //System.out.println("2."+classes.getValue());
                if (course.getCoursesPrimaryKey().getCourseNum().equals(classes.getValue())) {
                    String statementString = currentSection;
                    //System.out.println(currentSection);
                    //System.out.println(statementString);
                    section.getItems().addAll(statementString);
                    //System.out.println(course.getCoursesPrimaryKey().getCourseNum());
                }

            }

        }
    }
}


