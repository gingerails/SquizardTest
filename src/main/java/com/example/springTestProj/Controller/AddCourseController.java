package com.example.springTestProj.Controller;

import com.example.springTestProj.Entities.Course;
import com.example.springTestProj.Service.CourseService;
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

    public AddCourseController(CourseService courseService, FxWeaver fxWeaver) {
        this.courseService = courseService;//
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


    /**
     * saves new user.
     */
    public void createCourseAndSection(){

        String course = courseNum.getText();
        String section = courseSection.getText();
       // System.out.println(userService.returnUserByUsername(username));
        if(courseService.returnCourseByCourseName(course) == null) // there mustn't be a duplicate course name and section
        {
            // create course object in courseService. save to repo.
            Course newCourseAndSection = courseService.createCourse(course, section);
            courseService.saveCourseToRepository(newCourseAndSection);
        }
        else{
            System.out.println("Error: Username taken");
        }
    }

    public void addSection(){
        String course = courseNum.getText();
        String section = courseSection.getText();
    }

   

}