package com.example.springTestProj.Controller;

import com.example.springTestProj.Service.UserService;
//import com.example.springTestProj.StageInitializer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/Main.fxml")
public class SimpleController {
    private final UserService userService;
    private final FxWeaver fxWeaver;

    private Stage stage;
    @FXML
    public Label label;

    @FXML
    public Button button;

    @FXML
    VBox mainVbox;

    public SimpleController(UserService userService, FxWeaver fxWeaver) {
        this.userService = userService;
        this.fxWeaver = fxWeaver;
    }


    @FXML
    public void initialize () {
        this.button.setOnAction(actionEvent -> this.label.setText(userService.readUsers().toString())); //this.hostServices.getDocumentBase())
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
        stage.setScene(new Scene(mainVbox));
        System.out.println("SHOW Create Account Controller");
        stage.show();
    }
}
