package com.example.springTestProj.Controller;

import com.example.springTestProj.Entities.Courses;
import com.example.springTestProj.Entities.Feedback;
import com.example.springTestProj.Entities.Section;
import com.example.springTestProj.Service.CourseService;
import com.example.springTestProj.Service.FeedbackService;
import com.example.springTestProj.Service.SectionService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/addCourse.fxml")
public class AddCourseController implements ControlSwitchScreen {
    private final CourseService courseService;
    private final SectionService sectionService;

    // for testing. delete later
    private final FeedbackService feedbackService;
    private final FxWeaver fxWeaver;
    private Stage stage;

    @FXML
    TextField courseNum;
    @FXML
    TextField courseSection;
 
    @FXML
    Button add;
    
    @FXML
    VBox addCourseVbox;  // fx:id !!!!!

    public AddCourseController(CourseService courseService, SectionService sectionService, FeedbackService feedbackService, FxWeaver fxWeaver) {
        this.courseService = courseService;//
        this.sectionService = sectionService;
        this.feedbackService = feedbackService;
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
            } else if (courseSection.getText().isBlank()){
                String courseNumText = courseNum.getText();
              //  Feedback savedBack = feedbackService.createFeedback();
                //feedbackService.saveFeedbackToRepo(savedBack);


                  createCourse(courseNumText);

            } else {
                //feedbackService.createFeedback();
                createCourseAndSection();
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

    public void createCourse(String courseNum){
        Courses newCourse = courseService.createCourse(String.valueOf(courseNum));
        courseService.saveCourseToRepository(newCourse);
//        if(!courseService.existsByCourseNum(String.valueOf(courseNum))) // there mustn't be an existing same course
//        {
//            Courses newCourse = courseService.createCourse(String.valueOf(courseNum));
//        }
//        else{
//            System.out.println("Error: Course already exists");
//        }
    }

    public void createCourseAndSection(){
         String course = courseNum.getText();
         String section = courseSection.getText();
         String[] sectionsList = section.split(",");
        if(courseService.existsByCourseNum(String.valueOf(courseNum))) // check if updating existing course
        {
            Courses existingCourse = courseService.returnCourseByCourseNum(course);
            String courseUUID = existingCourse.getCoursesPrimaryKey().getCoursesUUID();                // get existing course's id
            for (String s:sectionsList) {
                if(sectionService.existsByCourseSection(courseUUID,s)){         // for each section in list, check if that course id + section exists
                    System.out.println("Error: Section already exists. Did not add duplicate section.");
                }else{
                    Section newSection = sectionService.createNewSection(courseUUID,s);     // if that courseID + section combo doesnt exits, make it
                    sectionService.saveSectionToRepository(newSection);
                }
            }
        }
        else{       // otherwise, we are creating an entirely new course with sections
            Courses newCourse = courseService.createCourse(course); // returns course w rand uuid
            for (String s:sectionsList) {
                if(sectionService.existsByCourseSection(newCourse.getCoursesPrimaryKey().getCoursesUUID(), s)){         // checks for sections here just incase user adds a duplicate entry
                    System.out.println("Error: Section already exists. Did not add duplicate section.");
                }else{
                    Section newSection = sectionService.createNewSection(newCourse.getCoursesPrimaryKey().getCoursesUUID(),s);
                    sectionService.saveSectionToRepository(newSection);
                }
            }
        }

    }

    public void addSection(){
        String course = courseNum.getText();
        String section = courseSection.getText();
    }

   

}