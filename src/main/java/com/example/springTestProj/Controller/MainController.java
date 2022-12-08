
package com.example.springTestProj.Controller;

import com.example.springTestProj.Entities.Section;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.CourseService;
import com.example.springTestProj.Repository.CourseRepository;
import com.example.springTestProj.Entities.Courses;
import com.example.springTestProj.Service.QuestionService.EssayQuestionService;
import com.example.springTestProj.Service.SectionService;
import com.example.springTestProj.Service.TestService;
import com.example.springTestProj.Service.UserService;

import java.io.File;
import java.util.*;

import javafx.event.ActionEvent;
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

//This class controls the main screen and opens add tests and edit tests screens
@Component
@FxmlView("/Main.fxml")
public class MainController implements ControlSwitchScreen {
    //initializing Services, FX Weaver, and all interactive GUI items
    CourseRepository courseRepository;
    private final CourseService courseService;
    private final SectionService sectionService;
    private final UserService userService;
    private final TestService testService;
    private final EssayQuestionService essayQuestionService;
    private final FxWeaver fxWeaver;
    private Stage stage;
    public static String path = "src\\main\\resources";
    private Section selectedSection;
    private Courses selectedCourse;
    @FXML
    private VBox mainVbox;
    @FXML
    private Button addTest;
    @FXML
    private Label name1, name2, name3, name4, name5, name6, name7, name8;
    @FXML
    private Button preview1, preview2, preview3, preview4, preview5, preview6, preview7, preview8;
    @FXML
    private Button recent1, recent2, recent3, recent4, recent5, recent6, recent7, recent8;
    @FXML
    private Button edit1, edit2, edit3, edit4, edit5, edit6, edit7, edit8;
    @FXML
    private Group test1group, test2group, test3group, test4group, test5group, test6group, test7group, test8group;
    @FXML
    private Button addCourseButton,searchB,temp1,temp2,temp3;
    @FXML
    private ComboBox displayClass,bar;
    @FXML
    private Pane recentsPane;
    
    public String getC = "";
    public String getS = "";
    public static boolean template1 = false;
    public static boolean template2 = false;
    public static boolean template3 = false;
    
    //contructor
    public MainController(UserService userService, FxWeaver fxWeaver, CourseService courseService, TestService testService, EssayQuestionService essayQuestionService,SectionService sectionService) {
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.courseService = courseService;
        this.testService = testService;
        this.essayQuestionService = essayQuestionService;
        this.sectionService=sectionService;
    }

    //gets the courses and disply them to the dropdown
    public void getDatabaseCourses() {
        //clear droopdown
        displayClass.getItems().clear();
        List<Courses> dropdownCourseList = courseService.readCourses();
        //add courses to dropdown
        for (Courses course : dropdownCourseList) {
            String sectionsAsString = course.getSections();
            if (course.getSections() == null) {
                sectionsAsString = "";
            }
            String[] sectionsList = sectionsAsString.split(",");
            
            for (String currentSection : sectionsList) {
                String statementString = course.getCoursesPrimaryKey().getCourseNum() + " " + currentSection;
                System.out.println(currentSection);
                System.out.println(statementString);

                displayClass.getItems().addAll(statementString);
            }
        }

    }
    
    //exits application
    public void exit()
    {
        
        stage.close();
    }
    
    //initialize is automatically called and this is where we store button action events
    @FXML
    public void initialize() {
        template1 = false;
        template2 = false;
        template3 = false;

        //sets preview windows to invisible
         ArrayList<Node> previewGroups = new ArrayList<>(Arrays.asList(this.test1group, this.test2group, this.test3group, this.test4group, this.test5group, this.test6group, this.test7group, this.test8group));
         for (Node node : previewGroups) {
            node.setVisible(false);
        }

        //sets dropdown to display courses
        getDatabaseCourses();

        //controls and sets values for course dropdown
        this.displayClass.setOnAction(actionEvent -> {
            setClassandSectionn();
            showExistingTests(userService.returnCurrentUserID());
            setSearch();
        });
        
        //controls the add test button
        this.addTest.setOnAction(actionEvent -> {
            loadAddTestScreen();
        });
        
        //controls the search bar 
        this.searchB.setOnAction(actionEvent -> {
            searchEdit();
        });
        
        //controls the template 1 button
        this.temp1.setOnAction(actionEvent -> {
            template1=true;
            loadAddTestScreen();
        });
        
        ///controls template 2 button
        this.temp2.setOnAction(actionEvent -> {
            template2=true;
            loadAddTestScreen();
        });
        
        //controls template 3 button
        this.temp3.setOnAction(actionEvent -> {
            template3=true;
            loadAddTestScreen();
        });
        
        //controls the add course button
        this.addCourseButton.setOnAction((ActionEvent actionEvent) -> {
            loadAddCourseScreen();
            initialize();
        });


    }
    
    //gets the current stage
    @Override
    public Stage getCurrentStage() {
        Node node = addTest.getParent(); // cant set this in init bc it could cause a null pointer :-\ probably needs its own method
        Stage currentStage = (Stage) node.getScene().getWindow();
        return currentStage;
    }
    
    //search for tests and show to searchbar
    public void setSearch()
    {
        bar.getItems().clear();
        File folder = new File(path+"\\"+getC+"\\"+getS);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            
            if (listOfFiles[i].isFile()) {
               // if(StringUtils.contains(, "common task"))
               if(listOfFiles[i].getName().contains("KEY_")==false){
                System.out.println("File " + listOfFiles[i].getName());
                bar.getItems().addAll(listOfFiles[i].getName().replaceAll(".html", ""));
               }
            } else if (listOfFiles[i].isDirectory()) {
                //System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }
    
    //shows stage and center window on screen
    @Override
    public void show(Stage thisStage) {
        this.stage = thisStage;
        stage.setScene(new Scene(mainVbox));
        System.out.println("Showing main screen");
        stage.show();
        this.stage.setMaximized(false);

        this.stage.setMaximized(true);
        this.stage.centerOnScreen();
    }

    //sets the class and setcion for the dropdown bar
    public void setClassandSectionn() {
        int token = 0;
        if (displayClass.getValue().equals(null) || displayClass.getValue().toString().equals("") == false) {
            String getDis = (String) displayClass.getValue();
            String parts[] = getDis.split(" ");
            for (String part : parts) {
                if (token == 0) {
                    getC = part;
                    selectedCourse = courseService.returnCourseByCourseNum(part);
                    token++;
                }
                if (token == 1) {
                    getS = part;
                    selectedSection = sectionService.returnSectionBySectionAndCourseID(getS, selectedCourse.getCoursesPrimaryKey().getCoursesUUID());
                }
            }
        }
       
    }

    //loads test add test screen
    public void loadAddTestScreen() {
        Stage currentStage = getCurrentStage();
        FxControllerAndView<CreateTestController, VBox> createTestControllerAndView =
                fxWeaver.load(CreateTestController.class);
        createTestControllerAndView.getController().show(getCurrentStage());
    }

    //loads previews and sets the previews to correct file andshows buttons asccociated with previews
    public void loadpreview(String name) {
        Stage nstage = new Stage();

        WebView browser = new WebView();
        WebEngine engine = browser.getEngine();
        File f = new File(name);

        //loads file in webview window
        engine.load(f.toURI().toString());

        StackPane sp = new StackPane();
        sp.getChildren().add(browser);

        Scene root = new Scene(sp);

        nstage.setScene(root);
        nstage.show();
    }

    //loads add course screen
    public void loadAddCourseScreen() {
        FxControllerAndView<AddCourseController, VBox> addCourseControllerAndView =
                fxWeaver.load(AddCourseController.class);
        addCourseControllerAndView.getController().show(getCurrentStage());
    }

    //finds all tests and displayes to searchbar for each section and course
    public void searchEdit()
    {
         for (Test test : testService.findAllTestsByUser(userService.returnCurrentUserID())) {
               
                String testName = test.getTestName();
                String tempName = testName.replace(".html", "");

               // thisLabel.setText(tempName);

                if (!testName.equals(".html")&&bar.getValue().equals(tempName)) {
                    
                    
                        testService.setCurrentTest(test);
                        FxControllerAndView<TestMakerController, VBox> testMakerControllerAndView =
                                fxWeaver.load(TestMakerController.class);
                        testMakerControllerAndView.getController().show(getCurrentStage());
                    

                }
            }
    }
    
    //shows existing tests and sets the buttons of the preview and the files that should be linked ot buttons
    public void showExistingTests(String userID) {

        int token = 0;
        ArrayList<Node> previewGroups = new ArrayList<>(Arrays.asList(this.test1group, this.test2group, this.test3group, this.test4group, this.test5group, this.test6group, this.test7group, this.test8group));

        for (Node node : previewGroups) {
            node.setVisible(false);

        }
      
        ArrayList<Test> allTestsBySection = new ArrayList<>();
        String testsBySectionStr = selectedSection.getTest();
        if(testsBySectionStr != null){
            String[] testIDArrStr= testsBySectionStr.split(",");
            String[] testIDs= Arrays.copyOfRange(testIDArrStr, 1, testIDArrStr.length);
            // for each test id, search repo for it and add to the testbyuserandsection list
            for(String testID: testIDs){
                Test thisTest = testService.returnTestByTestID(testID);
                allTestsBySection.add(thisTest);
            }

            ArrayList<Button> previewButtons = new ArrayList<>(Arrays.asList(this.preview1, this.preview2, this.preview3, this.preview4, this.preview5, this.preview6, this.preview7, this.preview8));
            ArrayList<Button> editButtons = new ArrayList<>(Arrays.asList(this.edit1, this.edit2, this.edit3, this.edit4, this.edit5, this.edit6, this.edit7, this.edit8));
            ArrayList<Label> labels = new ArrayList<>(Arrays.asList(this.name1, this.name2, this.name3, this.name4, this.name5, this.name6, this.name7, this.name8));

            ArrayList<Long> testsByTime = new ArrayList<Long>();
            allTestsBySection.sort(Comparator.comparing(Test::getDateCreated));    // sort tests by creation date. oldest to youngest
            Collections.reverse(allTestsBySection);        // most recent tests first now
            int childNodeCount = 0;
            // go through the first 7 tests and make button for them
            for (Test test : allTestsBySection) {
                Node thisGroup = previewGroups.get(childNodeCount);
                Button thisPreviewButton = previewButtons.get(childNodeCount);
                Button thisEditButton = editButtons.get(childNodeCount);
                Label thisLabel = labels.get(childNodeCount);
                String testName = test.getTestName();
                String tempName = testName.replace(".html", "");

                thisLabel.setText(tempName);

                if (!testName.equals(".html")) {
                    thisGroup.setVisible(true);
                    thisEditButton.setOnAction(actionEvent -> {
                        testService.setCurrentTest(test);
                        FxControllerAndView<TestMakerController, VBox> testMakerControllerAndView =
                                fxWeaver.load(TestMakerController.class);
                        testMakerControllerAndView.getController().show(getCurrentStage());
                    });

                    thisPreviewButton.setOnAction(actionEvent -> {
                        loadpreview(path + "\\" + getC + "\\" + getS + "\\" + testName);
                        loadpreview(path + "\\" + getC + "\\" + getS + "\\" + "KEY_" + testName);
                    });

                    Long dateCreated = (test.getDateCreated().getTime() / 1000);
                    childNodeCount++;
                    if (childNodeCount >= 7) {
                        break;
                    }
                }
            }
        }


    }

}
