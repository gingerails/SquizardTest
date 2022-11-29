package com.example.springTestProj.Controller.CreateQuestionWindows;

import static com.example.springTestProj.Controller.QuestionHTMLHelper.path;
import static com.example.springTestProj.Controller.QuestionHTMLHelper.pathTo;
import com.example.springTestProj.Controller.TestMakerController;
import com.example.springTestProj.Service.UserService;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("/fillinBlankQ.fxml")
public class FillinBlankQController implements ControlDialogBoxes {
    private final UserService userService;
    private final FxWeaver fxWeaver;
    private Stage stage;
    private final TestMakerController testMakerController;
    @FXML
    private Button add;

    @FXML
    private Button addGraphicButton;
    @FXML
    private VBox fillInBlankBox;
    @FXML
    private TextField questionField;

     public static String path = "src\\main\\resources\\";
    public static String pathTo = "";
    //public FibQuestionController(UserService userService, FxWeaver fxWeaver) {

    public FillinBlankQController(UserService userService, FxWeaver fxWeaver,TestMakerController testMakerController) {
        this.userService = userService;
        this.fxWeaver = fxWeaver;
        this.testMakerController = testMakerController;
    }

    @FXML
    public void initialize() {
        String cSection="";
        String cClass="";
        int count =0;
        //need to check current section and class
        BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(
					"temp.txt"));
			String line = reader.readLine();
			while (line != null) {
                            
				System.out.println(line);
				// read next line
                                if(count==0)
                                {
                                    cClass=line;
                                }
                                if(count==1)
                                {
                                    cSection=line;
                                }
				line = reader.readLine();
                                count++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
                
                //Files.deleteIfExists(Paths.get("temp.txt"));
        System.out.println(cClass+" "+cSection);
        
        pathTo = path+cClass+"\\" +cSection+"\\";
        
        
        
        this.stage = new Stage();
        stage.setTitle("Add Fill-in-Blank Question");
        stage.setScene(new Scene(fillInBlankBox));

        this.add.setOnAction(actionEvent -> {


        });
    }

    @Override
    public void show(Stage thisStage) {
        stage.show();
        this.stage.centerOnScreen();
        this.add.setOnAction(actionEvent -> {
            //System.out.print("Add question button pressed");
            stage.close();
            addHTML(pathTo+"test.html");
        });
    }


    public void addHTML(String file) {
        String question=questionField.getText();
        String rs=question.replace("/?/"," __________________ ");
        // gets the current stage, sets the scene w the create account control/view (fxweaver), then updates stage w that scene

        try ( FileWriter f = new FileWriter(file, true);  BufferedWriter b = new BufferedWriter(f);  PrintWriter p = new PrintWriter(b);) {

            p.println("<hr />" + "\n"
                   +"<p><strong>Fill in the Blanks</strong></p>"+"\n"

                   +"<p>"+rs+"</p>"+"\n"
            );
            b.close();
            p.close();
            f.close();
            TestMakerController.engine.reload();
        } catch (IOException i) {
            i.printStackTrace();
        }

    }

}
