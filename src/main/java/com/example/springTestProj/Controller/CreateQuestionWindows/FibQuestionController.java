package com.example.springTestProj.Controller.CreateQuestionWindows;

import com.example.springTestProj.Controller.CreateQuestionWindows.ControlDialogBoxes;
import com.example.springTestProj.Controller.LoginController;
import com.example.springTestProj.Service.UserService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxWeaver;


public class FibQuestionController implements ControlDialogBoxes {
    private final UserService userService;
    private final FxWeaver fxWeaver;
    private Stage stage;
    @FXML
    private Button add;
    @FXML
    private VBox fillInBlankBox;


    public FibQuestionController(UserService userService, FxWeaver fxWeaver) {
        this.userService = userService;
        this.fxWeaver = fxWeaver;
    }

    @FXML
    public void initialize() {
        this.stage = new Stage();
        stage.setScene(new Scene(fillInBlankBox));
    }

    @Override
    public void show(Stage thisStage) {
        this.stage = thisStage;
        stage.setScene(new Scene(fillInBlankBox));
        System.out.println("SHOW Create Account Controller");
        stage.show();
    }


    @Override
    public Stage getCurrentStage() {
        Node node = add.getParent(); // cant set this in init bc it could cause a null pointer :-\ probably needs its own method
        Stage currentStage = (Stage) node.getScene().getWindow();
        return currentStage;
    }

    public void exitScreen() {
        Stage currentStage = getCurrentStage();
        FxControllerAndView<LoginController, VBox> loginControllerAndView =
                fxWeaver.load(LoginController.class);
        loginControllerAndView.getController().show(getCurrentStage());
    }
}
