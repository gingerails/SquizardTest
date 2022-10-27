package com.example.springTestProj.Controller;

import com.example.springTestProj.Service.UserService;
//import com.example.springTestProj.StageInitializer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/ui.fxml")
public class SimpleController {
//    @Autowired
//    UserService userService;

    private final UserService userService;
    @FXML
    public Label label;

    @FXML
    public Button button;

    public SimpleController(UserService userService) {
        this.userService = userService;
    }

//

    @FXML
    public void initialize () {
        this.button.setOnAction(actionEvent -> this.label.setText(userService.readUsers().toString())); //this.hostServices.getDocumentBase())
    }
}
