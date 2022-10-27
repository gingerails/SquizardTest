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

import java.util.UUID;

@Component
@FxmlView("/createAccount.fxml")
public class CreateAccountController {
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
    public Hyperlink existingAccountLink;

    @FXML
    VBox createAccountVbox;  // fx:id !!!!!


    public CreateAccountController(UserService userService, FxWeaver fxWeaver) {
        System.out.println("Create Account Controller");
        this.fxWeaver = fxWeaver;
        this.userService = userService;
    }

    @FXML
    public void initialize () {
        this.existingAccountLink.setOnAction(actionEvent -> {
            System.out.print("Link clicked");
            Stage currentStage = getCurrentStage();
            FxControllerAndView<LoginController, VBox> loginControllerAndView =
                    fxWeaver.load(LoginController.class);
            loginControllerAndView.getController().show(getCurrentStage());
        });

        this.button.setOnAction(actionEvent -> {
            System.out.println("CReating User");
            createUser();
        });
    }

    public void show(Stage thisStage) {
        this.stage = thisStage;
        stage.setScene(new Scene(createAccountVbox));
        System.out.println("SHOW Create Account Controller");
        stage.show();
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

    /**
     * saves new user.
     */
    public void createUser(){

        System.out.println("Verifying User...");

        String username = usrField.getText();
        String password = passField.getText();
        System.out.println(userService.returnUserByUsername(username));
        if(userService.returnUserByUsername(username) == null) // there mustn't be any users w that username on this device
        {
            // SAVE NEW USER TO REPOSITORY
            User newUser = userService.createUser(username, password);
            userService.saveUserToRepository(newUser);
        }
        else{
            System.out.println("Error: Username taken");
        }
    }
}
