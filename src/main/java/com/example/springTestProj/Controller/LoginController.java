package com.example.springTestProj.Controller;

import com.example.springTestProj.Entities.User;
import com.example.springTestProj.Service.UserService;
import java.awt.Color;
import static java.awt.Color.RED;
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

//This class controls the login screen and lets user create tests after user is verified
@Component
@FxmlView("/login.fxml")
public class LoginController implements ControlSwitchScreen {
    //initializing Services, FX Weaver, and all interactive GUI items
    private final UserService userService;
    private final FxWeaver fxWeaver;
    private Stage stage;
    @FXML
    public Label error,label,confirm;
    @FXML
    public Button button;
    @FXML
    public TextField usrField,passField;
    @FXML
    public Hyperlink createAccountLink;
    @FXML
    VBox loginVbox; 

    //contructor
    public LoginController(UserService userService,FxWeaver fxWeaver) {
        this.userService = userService;
        this.fxWeaver = fxWeaver;    
    }

    //initialize is automatically called and this is where we store button action events
    @FXML
    public void initialize () {
        //controls the create account link
        this.createAccountLink.setOnAction(actionEvent -> {
            // gets the current stage, sets the scene w the create account control/view (fxweaver), then updates stage w that scene
            Stage currentStage = getCurrentStage();
            FxControllerAndView<CreateAccountController, VBox> createAccountControllerAndView =
                    fxWeaver.load(CreateAccountController.class);
            createAccountControllerAndView.getController().show(getCurrentStage());
        });
        //controls the login button and loads test maker
        this.button.setOnAction(actionEvent -> {
            verify();
        });
    }

     //gets the current stage
    @Override
    public Stage getCurrentStage() {
        Node node = button.getParent(); // cant set this in init bc it could cause a null pointer :-\ probably needs its own method
        Stage currentStage = (Stage) node.getScene().getWindow();
        return currentStage;
    }

    //shows stage and center window on screen
    @Override
    public void show(Stage thisStage) {
        this.stage = thisStage;
        stage.setScene(new Scene(loginVbox));
        System.out.println("Showing login screen");
        stage.show();
    }

    //function to verify user with db and display errors if field requirements are not met
    public void verify() {
        //get data from fields
        String username = usrField.getText();
        String password = passField.getText();
        //checks if fields are not null and login  
        if(userService.returnUser(username, password) != null) //needs to talk to database and veify the username and passwords
        {
            System.out.println("Found User!");
            Stage currentStage = getCurrentStage();
            User thisUser = userService.returnUser(username, password);
            userService.setCurrentUser(thisUser);
            FxControllerAndView<MainController, VBox> mainMenuControllerAndView =
                  fxWeaver.load(MainController.class);
            mainMenuControllerAndView.getController().show(currentStage);
        }
        //if fields are null print errors
        else{
            System.out.println("Error: UserName/Password is incorrect! Try Again!");
            
            error.setText("Error: UserName/Password is incorrect! Try Again!");
    }
    }

}