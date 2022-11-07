package com.example.springTestProj.Controller;

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

@Component
@FxmlView("/login.fxml")
public class LoginController implements ControlSwitchScreen {
    private final UserService userService;
    private final FxWeaver fxWeaver;
    private Stage stage;

    @FXML
    public Label error;
   
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
            // gets the current stage, sets the scene w the create account control/view (fxweaver), then updates stage w that scene
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

    @Override
    public Stage getCurrentStage() {
        Node node = button.getParent(); // cant set this in init bc it could cause a null pointer :-\ probably needs its own method
        Stage currentStage = (Stage) node.getScene().getWindow();
        return currentStage;
    }

    @Override
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
    public void verify() {
        System.out.println("Verifying User...");
        String Username = usrField.getText();
        String Password = passField.getText();
        if(userService.returnUser(Username, Password) != null) //needs to talk to database and veify the username and passwords
        {
            System.out.println("Found User!");
            Stage currentStage = getCurrentStage();
            FxControllerAndView<MainController, VBox> mainMenuControllerAndView =
                  fxWeaver.load(MainController.class);
            mainMenuControllerAndView.getController().show(currentStage);
        }
        else{
            System.out.println("Error: UserName/Password is incorrect! Try Again!");
            
            error.setText("Error: UserName/Password is incorrect! Try Again!");
       

    }
    }

}