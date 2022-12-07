package com.example.springTestProj.Controller;

import com.example.springTestProj.Entities.User;
import com.example.springTestProj.Service.UserService;
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

@Component
@FxmlView("/createAccount.fxml")
public class CreateAccountController implements ControlSwitchScreen {
    //initializing Services, FX Weaver, and all interactive GUI items
    private final UserService userService;
    private final FxWeaver fxWeaver;
    private Stage stage;
    @FXML
    public Label label,confirm,error;
    @FXML
    public Button button;
    @FXML
    public TextField usrField,passField;
    @FXML
    public Hyperlink existingAccountLink;
    @FXML
    VBox createAccountVbox;

    //contructor
    public CreateAccountController(UserService userService, FxWeaver fxWeaver) {
        System.out.println("Create Account Controller");
        this.fxWeaver = fxWeaver;
        this.userService = userService;
    }
    
    //initialize is aautomatically called and this is where we store button action events
    @FXML
    public void initialize () {
        
        //button to goto login screen
        this.existingAccountLink.setOnAction(actionEvent -> {
            loadLoginScreen();
        });

        //button to create user
        this.button.setOnAction(actionEvent -> {
            System.out.println("CReating User");
            createUser();
        });
    }

    //shows stage and center window on screen
    @Override
    public void show(Stage thisStage) {
        this.stage = thisStage;
        stage.setScene(new Scene(createAccountVbox));
        System.out.println("SHOW Create Account Controller");
        stage.show();
    }

    //gets the current stage
    @Override
    public Stage getCurrentStage() {
        Node node = button.getParent(); // cant set this in init bc it could cause a null pointer :-\ probably needs its own method
        Stage currentStage = (Stage) node.getScene().getWindow();
        return currentStage;
    }

    //loads login screen with FXWeaver
    public void loadLoginScreen() {
        Stage currentStage = getCurrentStage();
        FxControllerAndView<LoginController, VBox> loginControllerAndView =
                fxWeaver.load(LoginController.class);
        loginControllerAndView.getController().show(getCurrentStage());
    }

    //saves new user to database
    public void createUser(){
        
        //get text from textfields
        String username = usrField.getText();
        String password = passField.getText();
        
        
        if(userService.returnUserByUsername(username) == null) // there mustn't be any users w that username on this device
        {
            // SAVE NEW USER TO REPOSITORY
            User newUser = userService.createUser(username, password);
            userService.saveUserToRepository(newUser);
        }
        else{ //print error msg
            error.setText(" ERROR: Username Taken");
        }
    }
}
