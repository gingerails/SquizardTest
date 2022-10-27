package com.example.springTestProj.Controller;

import com.example.springTestProj.Entities.User;
import com.example.springTestProj.Service.UserService;
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
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@FxmlView("/createAccount.fxml")
public class CreateAccountController {

//    @Autowired
//    UserService userService;
//    public UserService userService;
  //  public UserService userService = new UserService();

    private final UserService userService;
    private Stage stage;

    private Scene scene;

    private final FxControllerAndView<LoginController, VBox> loginControllerAndView;

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
    VBox test;

//    public CreateAccountController(UserService userService) {
//        this.userService = userService;
//    }

    public CreateAccountController(UserService userService,  FxControllerAndView<LoginController, VBox> loginControllerAndView) {
        System.out.println("**********");
        System.out.println("Create Account Controller");
        this.userService = userService;
        this.loginControllerAndView = loginControllerAndView;
    }

    @FXML
    public void initialize () {

        //button.getScene().getWindow();
        System.out.println("Initialized Create Account Controller");
        this.existingAccountLink.setOnAction(actionEvent -> {
            System.out.print("Link clicked");
//            try {
//                changeScene("/login.fxml");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
            //    verify();
        });
        this.button.setOnAction(actionEvent -> {
            System.out.println("CReating User");
            createUser();
         //   verify();
        });
    }

    public void show(Stage thisStage) {
       // Parent root = FXMLLoader.load(getClass().getResource(fxml));
        this.stage = thisStage;
        stage.setScene(new Scene(test));
        //  System.out.println();
        System.out.println("SHOW Create Account Controller");
      //  this.stage = (Stage) node.getScene().getWindow();

      //  this.scene = new Scene(node);

        stage.show();
    }


    public void changeScene(Node node) {
        //  System.out.println();
        System.out.println("SHOW Create Account Controller");
        this.stage = (Stage) node.getScene().getWindow();  // current stage


       //   this.scene = new Scene(node);

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
     * saves new user. YOU FORGOT THE IDs DUMMYYYYYYYY FIXITFIXITFIXIT
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
