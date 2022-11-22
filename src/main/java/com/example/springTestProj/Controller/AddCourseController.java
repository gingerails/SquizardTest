package com.example.springTestProj.Controller;

import com.example.springTestProj.Entities.Course;
import com.example.springTestProj.Entities.Section;
import com.example.springTestProj.Service.CourseService;
import com.example.springTestProj.Service.FeedbackService;
import com.example.springTestProj.Service.SectionService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;


@Component
@FxmlView("/addCourse.fxml")
public class AddCourseController implements ControlSwitchScreen {
    private final CourseService courseService;
    private final SectionService sectionService;

    private final MainController mainController;

    // for testing. delete later
    private final FeedbackService feedbackService;
    private final FxWeaver fxWeaver;
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
    private Stage stage;

    public AddCourseController(CourseService courseService, SectionService sectionService, FeedbackService feedbackService, MainController mainController, FxWeaver fxWeaver) {
        this.courseService = courseService;//
        this.sectionService = sectionService;
        this.feedbackService = feedbackService;
        this.mainController = mainController;
        this.fxWeaver = fxWeaver;


    }

    /**
     * initialize
     * automatically called
     */
    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setTitle("Add Course/Section");
        stage.setScene(new Scene(addCourseVbox));

        this.add.setOnAction(actionEvent -> {
            System.out.println("Course add button pressed");
            if (courseNum.getText()
                    .isBlank()) {
                System.out.println("Error: Course left blank");
                error.setText("Error: Course left blank");
            } else if (courseSection.getText()
                    .isBlank()) {
                String courseNumText = courseNum.getText();
                String course = courseNum.getText();
                //  if(courseService.existsByCourseNum(courseNumText)
                if (courseService.existsByCourseNum(String.valueOf(course))) // check if updating existing course
                {
                    error.setText("Error: Duplicated Course!");
                } else {
                    createCourse(courseNumText);
                    mainController.initialize();
                    stage.close();
                }
            } else {
                createCourseAndSection();
                mainController.initialize();
                stage.close();
            }

        });

    }

    @Override
    public Stage getCurrentStage() {
        Node node = add.getParent(); // cant set this in init bc it could cause a null pointer :-\ probably needs its own method
        Stage currentStage = (Stage) node.getScene()
                .getWindow();
        return currentStage;
    }

    @Override
    public void show(Stage thisStage) {
        stage.show();
        this.stage.centerOnScreen();
    }

    public void createCourse(String courseNum) {
        Course newCourse = courseService.createCourse(String.valueOf(courseNum));
        courseService.saveCourseToRepository(newCourse);

    }

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
            Course newCourse = courseService.createCourse(course); // returns new course w rand uuid
            for (String s : sectionsList) {
                if (sectionService.existsByCourseSection(newCourse.getCourseID()
                        .getUuid(), s)) {         // checks for sections here just incase user adds a duplicate entry
                    System.out.println("Error: Section already exists. Did not add duplicate section.");
                } else {
                    Section newSection = sectionService.createNewSection(newCourse.getCourseID()
                            .getUuid(), s);
                    sectionService.saveSectionToRepository(newSection);
                }
            }
            // now sections repo has all the new sections, we just need to update the stored course repo.
            String commaSeparatedUsingCollect = sectionsArrayList.stream()
                    .collect(Collectors.joining(","));
            newCourse.setSections(commaSeparatedUsingCollect);
            courseService.saveCourseToRepository(newCourse);
        }

    }

    public void updateExistingCourse(String course, ArrayList<String> sectionsList) {
        Course existingCourse = courseService.returnCourseByCourseNum(course);
        String courseUUID = existingCourse.getCourseID()
                .getUuid();                // get existing course's id
        //  ArrayList<String> addedSectionsList = new ArrayList<String>(); // after each section is added to the repo, save it here. This will be added to the 'sections' column of the course table entry
        for (String s : sectionsList) {
            if (sectionService.existsByCourseSection(courseUUID, s)) {         // for each section in list, check if that course id + section exists
                System.out.println("Error: Section already exists. Did not add duplicate section.");
                //   addedSectionsList.add(s);
            } else {
                Section newSection = sectionService.createNewSection(courseUUID, s);     // if that courseID + section combo doesnt exits, make it
                sectionService.saveSectionToRepository(newSection);
                //  addedSectionsList.add(s);
            }
        }
        // now sections repo has all the new sections, we just need to update the stored course repo.
        String commaSeparatedUsingCollect = sectionsList.stream()
                .collect(Collectors.joining(","));
        existingCourse.setSections(commaSeparatedUsingCollect);
        courseService.addSectionsToExistingCourseAndSave(existingCourse);
    }

    public void addSection() {
        String course = courseNum.getText();
        String section = courseSection.getText();
    }


}