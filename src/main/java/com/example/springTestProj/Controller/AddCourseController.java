package com.example.springTestProj.Controller;

import com.example.springTestProj.Entities.Courses;
import com.example.springTestProj.Entities.Feedback;
import com.example.springTestProj.Entities.Section;
import com.example.springTestProj.Service.CourseService;
import com.example.springTestProj.Service.FeedbackService;
import com.example.springTestProj.Service.SectionService;
import com.example.springTestProj.Controller.MainController;
import java.io.File;
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
import java.util.List;
import java.util.stream.Collectors;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Modality;

//This class controls the add course screen and adds courses to db
@Component
@FxmlView("/addCourse.fxml")
public class AddCourseController implements ControlSwitchScreen {
    //initializing Services, FX Weaver, and all interactive GUI items
    private final CourseService courseService;
    private final SectionService sectionService;
    private final MainController mainController;
    private final FeedbackService feedbackService;
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
    VBox addCourseVbox;

    //constructer
    public AddCourseController(CourseService courseService, SectionService sectionService, FeedbackService feedbackService, MainController mainController, FxWeaver fxWeaver) {
        this.courseService = courseService;//
        this.sectionService = sectionService;
        this.feedbackService = feedbackService;
        this.mainController = mainController;
        this.fxWeaver = fxWeaver;

    }
    
    //initialize is automatically called and this is where we store button action events
    @FXML
    public void initialize() {
        //initialize add course screen
        this.stage = new Stage();
        stage.setTitle("Add Course/Section");
        stage.setScene(new Scene(addCourseVbox));

        //add Button
        this.add.setOnAction(actionEvent -> {
            
            //check if course number is blank error if blank
            if (courseNum.getText().isBlank()) {
                error.setText("Error: Course left blank");
                
            //check if course section is blank error if blank
            } else if (courseSection.getText().isBlank()) {
                String courseNumText = courseNum.getText();
                String course = courseNum.getText();
               
                // check if updating existing course
                if (courseService.existsByCourseNum(String.valueOf(course))) 
                {
                    error.setText("Error: Duplicated Course!");
                } else {
                    createCourse(courseNumText);
                    mainController.initialize();
                    stage.close();
                }
                
            } else {
                createCourseAndSection();
                
                //call main
                mainController.initialize();
                stage.close();
            }

        });

    }

    //gets the current stage
    @Override
    public Stage getCurrentStage() {
        Node node = add.getParent(); 
        Stage currentStage = (Stage) node.getScene().getWindow();
        return currentStage;
    }

    //shows stage and center window on screen
    @Override
    public void show(Stage thisStage) {
        stage.show();
        this.stage.centerOnScreen();
    }

    //create and save course to database
    public void createCourse(String courseNum) {
        Courses newCourse = courseService.createCourse(String.valueOf(courseNum));
        courseService.saveCourseToRepository(newCourse);
    }

    //creates the course and section and adds to db and checks if they are already made
    public void createCourseAndSection() {
        String course = courseNum.getText();
        String section = courseSection.getText();
        String[] sectionsList = section.split(",");
        ArrayList<String> sectionsArrayList = new ArrayList<>(
                Arrays.asList(sectionsList));
        if (courseService.existsByCourseNum(String.valueOf(course))) // check if updating existing course
        {
            updateExistingCourse(course, sectionsArrayList);
        } // ********** Course does not exist in repository ******************
        else {       // otherwise, we are creating an entirely new course with sections
            Courses newCourse = courseService.createCourse(course); // returns new course w rand uuid
            for (String s : sectionsList) {
                if (sectionService.existsByCourseSection(newCourse.getCoursesPrimaryKey().getCoursesUUID(), s)) {         // checks for sections here just incase user adds a duplicate entry
                    System.out.println("Error: Section already exists. Did not add duplicate section.");
                } else {
                    Section newSection = sectionService.createNewSection(newCourse.getCoursesPrimaryKey().getCoursesUUID(), s);
                    sectionService.saveSectionToRepository(newSection);
                }
            }
            // now sections repo has all the new sections, we just need to update the stored course repo.
            String commaSeparatedUsingCollect = sectionsArrayList.stream().collect(Collectors.joining(","));
            newCourse.setSections(commaSeparatedUsingCollect);
            courseService.saveCourseToRepository(newCourse);
        }
        String[] arr = null;
        arr = section.split(",");
        for (int i = 0; i < arr.length; i++) {
            new File("src/main/resources/" + course + "/" + arr[i]).mkdirs();
        }

    }
    //function to add sectiona to an already made course
    public void updateExistingCourse(String course, ArrayList<String> sectionsList) {
        Courses existingCourse = courseService.returnCourseByCourseNum(course);
        String courseUUID = existingCourse.getCoursesPrimaryKey().getCoursesUUID();                // get existing course's id
        
        for (String s : sectionsList) {
            if (sectionService.existsByCourseSection(courseUUID, s)) {         // for each section in list, check if that course id + section exists
                System.out.println("Error: Section already exists. Did not add duplicate section.");
                
            } else {
                Section newSection = sectionService.createNewSection(courseUUID, s);     // if that courseID + section combo doesnt exits, make it
                sectionService.saveSectionToRepository(newSection);
                
            }
        }
        // now sections repo has all the new sections, we just need to update the stored course repo.
        String commaSeparatedUsingCollect = sectionsList.stream().collect(Collectors.joining(","));
        existingCourse.setSections(commaSeparatedUsingCollect);
        courseService.addSectionsToExistingCourseAndSave(existingCourse);
    }

    public void addSection() {
        String course = courseNum.getText();
        String section = courseSection.getText();
    }

}
