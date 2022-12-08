
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

//This class controls the vreate test screen and adds test to db
@Component
@FxmlView("/createTest.fxml")
public class CreateTestController implements ControlSwitchScreen {
    //initializing Services, FX Weaver, and all interactive GUI items
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

    //constructer
    public CreateTestController(UserService userService, FxWeaver fxWeaver, CourseService courseService, TestService testService, SectionService sectionService) {
        //System.out.println("Create Test Controller");
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.courseService = courseService;
        this.testService = testService;
        this.sectionService = sectionService;
    }

    //initialize is aautomatically called and this is where we store button action events
    @FXML
    public void initialize() {
        //get courses
        getCoursesInfo();
        this.createTest.setOnAction(actionEvent -> {
            //get testname from textfield
            String tName=name.getText();
            //checks to see if field is not null
            if(tName.equals("")==false)
                {
            //checks to see if section and course is not null
            if (classes.getValue() != null && section.getValue() != null) {
                //checks to see if file exists and display error if exists
                if (new File("src\\main\\resources\\" + (String) classes.getValue() + "\\" + (String) section.getValue() + "\\" + name.getText() + ".html").exists() == true) {
                    error.setText("ERROR: Test already exists");
                } else {
                    //save test if test does not exist
                    try {
                        saveTest();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(CreateTestController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //load test maker screen
                    loadTestMaker();
                }
            //if section and/or course is not selected show error
            } else {
                error.setText("ERROR: One or more items not selected");
            }
                }
            //if name is blank show error
            else
                {
                    error.setText("ERROR: Name can not be blank");
                }
        });

        //lets user select one course that exists
        this.classes.setOnAction(actionEvent -> {
            getSectionInfo();
        });
        
        //button to go back to previous  main screen
        this.backButton.setOnAction(actionEvent -> {
            loadMainScreen();
        });

    }

    //gets the current stage
    @Override
    public Stage getCurrentStage() {
        Node node = createTest.getParent(); // cant set this in init bc it could cause a null pointer :-\ probably needs its own method
        Stage currentStage = (Stage) node.getScene().getWindow();
        return currentStage;
    }

    //shows stage and center window on screen
    @Override
    public void show(Stage thisStage) {
        this.stage = thisStage;
        stage.setScene(new Scene(mainVbox));
        //System.out.println("Showing create test screen");
        stage.show();
        this.stage.centerOnScreen();
    }

    //loads test Maker screen
    public void loadTestMaker() {
        Stage currentStage = getCurrentStage();
        FxControllerAndView<TestMakerController, VBox> testMakerControllerAndView
                = fxWeaver.load(TestMakerController.class);
        testMakerControllerAndView.getController().show(getCurrentStage());
    }
    
    //load main screen
    public void loadMainScreen() {
       //
       Stage currentStage = getCurrentStage();
        //currentStage.setMaximized(true);
        FxControllerAndView<MainController, VBox> mainControllerAndView
                = fxWeaver.load(MainController.class);
        mainControllerAndView.getController().show(getCurrentStage());
    }

    //get information of courses for dropdown
    public void getCoursesInfo() {
        classes.getItems().clear(); // clear dropdown
        List<Courses> dropdownCourseList = courseService.readCourses();
        //fill dropdown with existing courses
        for (Courses course : dropdownCourseList) {
            String statementString = course.getCoursesPrimaryKey().getCourseNum();
            classes.getItems().addAll(statementString);
        }
    }

    //seave test to database
    public void saveTest() throws FileNotFoundException {
        String fileName = name.getText() + ".html";
        String className = (String) classes.getValue();
        String sectionName = (String) section.getValue();

        Courses selectedCourse = courseService.returnCourseByCourseNum(className);
        String courseID = selectedCourse.getCoursesPrimaryKey().getCoursesUUID();

        //adds to ALL sections of a class
        if (sectionName == "ALL") {
            List<Section> allSections = sectionService.findCourseSections(courseID);
        }
        //adds to only one specified section
        else {
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
        PrintWriter out = new PrintWriter("temp.txt"); 

        // Write the name of four oceans to the file
        out.println((String) classes.getValue());  
        out.println((String) section.getValue());

        // Close the file.
        out.close();  

    }

    //gets section info and adds the sections associated with the selected course to the dropdown
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
            //add exisinng sections to dropdown
            for (String currentSection : sectionsList) {

                if (course.getCoursesPrimaryKey().getCourseNum().equals(classes.getValue())) {
                    String statementString = currentSection;
                   
                    section.getItems().addAll(statementString);
                }

            }

        }
    }
}


