/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.springTestProj.Controller;

import com.example.springTestProj.Entities.QuestionEntities.EssayQuestion;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.CourseService;
import com.example.springTestProj.Repository.CourseRepository;
import com.example.springTestProj.Entities.Courses;
import com.example.springTestProj.Service.QuestionService.EssayQuestionService;
import com.example.springTestProj.Service.TestService;
import com.example.springTestProj.Service.UserService;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author Orames
 */
@Component
@FxmlView("/Main.fxml")
public class MainController implements ControlSwitchScreen {

    CourseRepository courseRepository;

    private final CourseService courseService;
    private final UserService userService;
    private final TestService testService;
    private final EssayQuestionService essayQuestionService;
    private final FxWeaver fxWeaver;
    private Stage stage;
    public static String path = "src\\main\\resources";
    public int counter=0;
    @FXML
    private VBox mainVbox;
    @FXML
    private Button addTest;
    @FXML
    private Label name1,name2,name3,name4,name5,name6,name7;
    @FXML
    private Button preview1, preview2, preview3, preview4, preview5, preview6, preview7, preview8;
    @FXML
    private Button recent1, recent2, recent3, recent4, recent5, recent6, recent7, recent8, enter;
    @FXML
    private Button edit1, edit2, edit3, edit4, edit5, edit6, edit7, edit8;
    @FXML
    private Group test1group, test2group, test3group, test4group, test5group, test6group, test7group, test8group;
    @FXML
    private Button addCourseButton;
    @FXML
    private ComboBox displayClass;
    @FXML
    private Pane recentsPane;
    public String getC = "";
    public String getS = "";


    public MainController(UserService userService, FxWeaver fxWeaver, CourseService courseService, TestService testService, EssayQuestionService essayQuestionService) {
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.courseService = courseService;
        this.testService = testService;
        this.essayQuestionService = essayQuestionService;
    }

    public void getDatabaseCourses() {
        displayClass.getItems().clear();
        List<Courses> dropdownCourseList = courseService.readCourses();
        for (Courses course : dropdownCourseList) {
            String sectionsAsString = course.getSections();
            if (course.getSections() == null) {
                sectionsAsString = "";
            }
            String[] sectionsList = sectionsAsString.split(",");
            ArrayList<String> displayList = new ArrayList<>();
            for (String currentSection : sectionsList) {
                String statementString = course.getCoursesPrimaryKey().getCourseNum() + " " + currentSection;
                System.out.println(currentSection);
                System.out.println(statementString);

                displayClass.getItems().addAll(statementString);
            }
        }

    }

    @FXML
    public void initialize() {
         ArrayList<Node> previewGroups = new ArrayList<>(Arrays.asList(this.test1group, this.test2group, this.test3group, this.test4group, this.test5group, this.test6group, this.test7group, this.test8group));
         for (Node node : previewGroups) {
            node.setVisible(false);
        }
        // this.displayClass.setOnIn
         this.enter.setOnAction(actionEvent -> {
            setClassandSectionn();
            showExistingTests(userService.returnCurrentUserID());
        });
        getDatabaseCourses();
        //showExistingTests(userService.returnCurrentUserID());
        this.preview1.setOnAction(actionEvent -> {
            setClassandSectionn();
            showExistingTests(userService.returnCurrentUserID());
        });
        this.preview2.setOnAction(actionEvent -> {
            setClassandSectionn();
            showExistingTests(userService.returnCurrentUserID());
        });
        this.preview3.setOnAction(actionEvent -> {
            setClassandSectionn();
            showExistingTests(userService.returnCurrentUserID());
        });
        this.preview4.setOnAction(actionEvent -> {
            setClassandSectionn();
            showExistingTests(userService.returnCurrentUserID());
        });
        this.preview5.setOnAction(actionEvent -> {
            setClassandSectionn();
            showExistingTests(userService.returnCurrentUserID());
        });
        this.preview6.setOnAction(actionEvent -> {
            setClassandSectionn();
            showExistingTests(userService.returnCurrentUserID());
        });
        this.preview7.setOnAction(actionEvent -> {
            setClassandSectionn();
            showExistingTests(userService.returnCurrentUserID());
        });
        this.preview8.setOnAction(actionEvent -> {
            setClassandSectionn();
            showExistingTests(userService.returnCurrentUserID());
        });
        this.addTest.setOnAction(actionEvent -> {
            loadAddTestScreen();
        });
        this.addCourseButton.setOnAction((ActionEvent actionEvent) -> {
            loadAddCourseScreen();
            //getDatabaseCourses();
            initialize();
        });


    }

    @Override
    public Stage getCurrentStage() {
        Node node = addTest.getParent(); // cant set this in init bc it could cause a null pointer :-\ probably needs its own method
        Stage currentStage = (Stage) node.getScene().getWindow();
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

    public void setClassandSectionn()
    {
        int token=0;
//        System.out.println("Test stuff:"+displayClass.getValue().toString());
        if(displayClass.getValue().equals(null) || displayClass.getValue().toString().equals("")==false)
        {
        String getDis = (String) displayClass.getValue();
        String parts[] = getDis.split(" ");
        for(String part: parts) {
            if(token==0)
            {
                getC=part;
                token++;
            }
            if(token==1)
            {
                getS=part;
            }
        }
        }
        System.out.println("cc:"+getC+" "+getS);
    }
    public void loadAddTestScreen() {
        Stage currentStage = getCurrentStage();
        FxControllerAndView<CreateTestController, VBox> createTestControllerAndView =
                fxWeaver.load(CreateTestController.class);
        createTestControllerAndView.getController().show(getCurrentStage());
    }

    public void loadpreview(String name) {
        Stage nstage = new Stage();

        WebView browser = new WebView();
        WebEngine engine = browser.getEngine();
        File f = new File(name);
//        URL url = this.getClass().getResource(name);
        engine.load(f.toURI().toString());

//        engine.load(url.toString());


        StackPane sp = new StackPane();
        sp.getChildren().add(browser);

        Scene root = new Scene(sp);

        nstage.setScene(root);
        nstage.show();
    }

    public void loadAddCourseScreen() {
        FxControllerAndView<AddCourseController, VBox> addCourseControllerAndView =
                fxWeaver.load(AddCourseController.class);
        addCourseControllerAndView.getController().show(getCurrentStage());
    }

    /**
     * for the test preview - go through the existing tests
     * get their htmls
     */
    public void showExistingTests(String userID) {
        
        int token =0;

        List<Test> allTestsByUser = testService.findAllTestsByUser(userID);
        ArrayList<Node> previewGroups = new ArrayList<>(Arrays.asList(this.test1group, this.test2group, this.test3group, this.test4group, this.test5group, this.test6group, this.test7group, this.test8group));
        ArrayList<Button> previewButtons = new ArrayList<>(Arrays.asList(this.preview1, this.preview2, this.preview3, this.preview4, this.preview5, this.preview6, this.preview7, this.preview8));
        ArrayList<Button> editButtons = new ArrayList<>(Arrays.asList(this.edit1, this.edit2, this.edit3, this.edit4, this.edit5, this.edit6, this.edit7, this.edit8));

        for (Node node : previewGroups) {
            node.setVisible(false);
            
        }
        ArrayList<Long> testsByTime = new ArrayList<Long>();
        allTestsByUser.sort(Comparator.comparing(Test::getDateCreated));    // sort tests by creation date. oldest to youngest
        Collections.reverse(allTestsByUser);        // most recent tests first now
        int childNodeCount = 0;
        // go through the first 7 tests and make button for them
        for (Test test : allTestsByUser) {
            Node thisGroup = previewGroups.get(childNodeCount);
            Button thisPreviewButton = previewButtons.get(childNodeCount);
            Button thisEditButton = editButtons.get(childNodeCount);
            String testName = test.getTestName();
            String tempName=testName.replace(".html","");
            if(testName.equals(".html")==false)
            {
            thisGroup.setVisible(true);
            
           thisPreviewButton.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent actionEvent) {
                    
                    loadpreview(path+"\\"+getC+"\\"+getS+"\\"+ testName);
                    
                }
            });
                    if(counter==0)
                    {
                        name1.setText(tempName);
                    }
                    if(counter==1)
                    {
                        name2.setText(tempName);
                    }
                    if(counter==3)
                    {
                        name3.setText(tempName);
                    }
                    if(counter==4)
                    {
                        name4.setText(tempName);
                    }
                    if(counter==5)
                    {
                        name5.setText(tempName);
                    }
                    if(counter==6)
                    {
                        name6.setText(tempName);
                    }
                    if(counter==7)
                    {
                        name7.setText(tempName);
                    }
                    
                    //loadpreview(path+"\\"+getC+"\\"+getS+"\\"+ testName);
                    counter=counter +1;
            Long dateCreated = (test.getDateCreated().getTime() / 1000);
            childNodeCount++;
            if (childNodeCount >= 7) {
                break;
            }
            }
        }

//        Node thisGroup = previewGroups.get(childNodeCount);

//

    }

}
