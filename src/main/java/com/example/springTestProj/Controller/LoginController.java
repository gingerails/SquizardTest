package com.example.springTestProj.Controller;

import com.example.springTestProj.Service.UserService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import net.rgielen.fxweaver.core.FxmlView;
import java.io.IOException;

@Component
@FxmlView("/login.fxml")
public class LoginController{
    private final UserService userService;
    private final FxWeaver fxWeaver;
    private Stage stage;

    @FXML
    public Label label;

    @FXML
    public Button button;

    @FXML
    public TextField usrField;

    @FXML
    public TextField passField;

    @FXML
    public Label confirm;

    @FXML
    public Hyperlink createAccountLink;

    @FXML
    VBox loginVbox;  // fx:id !!!!!

    public LoginController(UserService userService,
                           FxWeaver fxWeaver) {//
        this.userService = userService;
        this.fxWeaver = fxWeaver;
        System.out.println("**********");
        System.out.println("Login Controller");
    }

    /**
     * initialize
     * automatically called
     */
    @FXML
    public void initialize () {
        this.createAccountLink.setOnAction(actionEvent -> {

            System.out.print("Create Account Link clicked");

            // takes the current stage, sets the scene w the create account control/view (fxweaver), then shows stage
            Stage currentStage = getCurrentStage();
            FxControllerAndView<CreateAccountController, VBox> createAccountControllerAndView =
                    fxWeaver.load(CreateAccountController.class);
            createAccountControllerAndView.getController().show(getCurrentStage());
        });

        this.button.setOnAction(actionEvent -> {
            System.out.print("Login button pressed");
            verify();
        });
    }

    /**
     * duplicated in all controllers (so lets make an interface or controllerfactory or something idk)
     * @return
     */
    public Stage getCurrentStage() {
        Node node = button.getParent(); // cant set this in init bc it could cause a null pointer :-\ probably needs its own method
        Stage currentStage = (Stage) node.getScene().getWindow();
        return currentStage;
    }

    public void show(Stage thisStage) {
        this.stage = thisStage;
        stage.setScene(new Scene(loginVbox));
        System.out.println("Showing login screen");
        stage.show();
    }


    /**
     *
     * verify user exists. if so, switch scene to main menu.
     * probably need to put the scene switching stuff in its own method for the sake of SRP?
     */
    // obviously not going to store sensitive info like this
    public void verify() {
        System.out.println("Verifying User...");
        String Username = usrField.getText();
        String Password = passField.getText();
        if(userService.returnUser(Username, Password) != null) //needs to talk to database and veify the username and passwords
        {
            System.out.println("Found User!");
            Stage currentStage = getCurrentStage();
            FxControllerAndView<CreateAccountController, VBox> createAccountControllerAndView =
                    fxWeaver.load(CreateAccountController.class);
            createAccountControllerAndView.getController().show(currentStage);
        }
        else{
            System.out.println("Error: UserName/Password is incorrect! Try Again!");
        }

    }

}