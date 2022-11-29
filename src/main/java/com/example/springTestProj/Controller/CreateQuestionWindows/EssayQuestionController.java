/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.springTestProj.Controller.CreateQuestionWindows;

import com.example.springTestProj.Controller.QuestionHTMLHelper;
import static com.example.springTestProj.Controller.QuestionHTMLHelper.path;
import static com.example.springTestProj.Controller.QuestionHTMLHelper.pathTo;
import com.example.springTestProj.Entities.QuestionEntities.EssayQuestion;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.QuestionService.EssayQuestionService;
import com.example.springTestProj.Service.TestService;
import com.example.springTestProj.Controller.TestMakerController;
import com.example.springTestProj.Service.UserService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import javafx.stage.FileChooser;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author Orames
 */
@Component
@FxmlView("/essayQuestion.fxml")
public class EssayQuestionController implements ControlDialogBoxes {
    private Parent root;
    private final UserService userService;
    private final TestService testService;
    private final EssayQuestionService essayQuestionService;
    private final QuestionHTMLHelper questionHTMLHelper;
    private final TestMakerController testMakerController;

    private final FxWeaver fxWeaver;
    private Stage stage;

    @FXML
    private Button add;
    @FXML
    private Button answerGraphicButton;
    @FXML
    private Button questionGraphicButton;
    @FXML
    private VBox essayQuestionBox;
    @FXML
    private TextField answerTextField;
    @FXML
    private TextField sectionsTextField;
    @FXML
    private TextField MaterialTextField;
    @FXML
    private TextField questionTextField;
    @FXML
    private TextField commentTextField;
    @FXML
    private TextField instructionTextField;
    @FXML
    private Label error,aG,qG;

     public static String path = "src\\main\\resources\\";
    public static String pathTo = "";
    public File gF=null;
    
   String cSection="";
   String cClass="";
    public EssayQuestionController(UserService userService, TestService testService, EssayQuestionService essayQuestionService, QuestionHTMLHelper questionHTMLHelper, TestMakerController testMakerController, FxWeaver fxWeaver) {
        this.testService = testService;
        this.essayQuestionService = essayQuestionService;
        this.questionHTMLHelper = questionHTMLHelper;
        this.testMakerController = testMakerController;
        this.fxWeaver = fxWeaver;
        this.userService = userService;
    }

    @FXML
    public void initialize () {
        
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
        stage.setTitle("Add Essay Question");
        stage.setScene(new Scene(essayQuestionBox));
        this.add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.print("Add question button pressed");
                try {
                    createQuestion();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                
                
            }
        });
        this.answerGraphicButton.setOnAction(actionEvent -> {
            try {
                copyandFile();
            } catch (IOException ex) {
                Logger.getLogger(EssayQuestionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            aG.setText(gF.getName());

        });
        this.questionGraphicButton.setOnAction(actionEvent -> {
            try {
                copyandFile();
            } catch (IOException ex) {
                Logger.getLogger(EssayQuestionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            qG.setText(gF.getName());
        });

    }
   public File getFiles()
   {
       FileChooser file = new FileChooser();  
        file.setTitle("Open");  
                //System.out.println(pic.getId());
                Stage fStage = new Stage();
        File file1 = file.showOpenDialog(fStage);  
        System.out.println(file1);  
        return file1;
       
   }
   public void copyandFile() throws FileNotFoundException, IOException
   {
       
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

       //String src = getFiles().toString();
       //String dest = path +"\\" +cClass + "\\" + cSection;
        gF = getFiles();

        try {
            Path src = Paths.get(gF.toString());
            File f = new File(gF.getName());
            String v = f.getName();
            Path dest = Paths.get(path + "\\" + cClass + "\\" + cSection + "\\" + v);
            Files.copy(src, dest);
        } catch (IOException ex) {
            Logger.getLogger(EssayQuestionController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void createQuestion() throws IOException {
        if(!questionTextField.getText().isBlank() && !answerTextField.getText().isBlank()){
            String question = questionTextField.getText();
            String answer = answerTextField.getText();
            EssayQuestion essayQuestion = essayQuestionService.createEssayQuestion(question, answer);
            checkFieldsAndAddQuestion(essayQuestion);
            Test currentTest = getCurrentTestSectionInfo();
            String testName = currentTest.getTestName();
            addHTML(pathTo + testName, pathTo + "KEY_" + testName);
            testMakerController.refresh();
            stage.close();
        } else{
            error.setText("Error: Must have question and answer!");
        }

    }

    /**
     *
     * @param essayQuestion
     */
    public void checkFieldsAndAddQuestion(EssayQuestion essayQuestion){

        if(!MaterialTextField.getText().isBlank()){
            String refMaterial =  MaterialTextField.getText();
            essayQuestion.setReferenceMaterial(refMaterial);
        }
        if(!sectionsTextField.getText().isBlank()){
            String refSection =  sectionsTextField.getText();
            essayQuestion.setTextReferenceSection(refSection);
        }
        if(!commentTextField.getText().isBlank()){
            String comment =  commentTextField.getText();
            essayQuestion.setInstructorComment(comment);
        }
        if(!instructionTextField.getText().isBlank()){
            String instructions =  instructionTextField.getText();
            essayQuestion.setGradingInstruction(instructions);
        }
        essayQuestion.setCreatorId(userService.returnCurrentUserID());
        essayQuestionService.saveQuestionToRepository(essayQuestion);
        Test currentTest = getCurrentTestSectionInfo();
        testService.addEQuestion(currentTest, essayQuestion);// also save to test using test service
    }

    /**
     * Get currentTest obj from TestService, which contains a copy of the test being edited
     * @return
     */
    public Test getCurrentTestSectionInfo(){
        Test currentTest = testService.returnThisTest();
        System.out.println(currentTest.getTestUUID());

        return currentTest;
    }


    @Override
    public void show(Stage thisStage) {
        stage.show();
        this.stage.centerOnScreen();
    }


  public void addHTML( String file, String keyFile) throws IOException {
    questionHTMLHelper.updateSections(file, keyFile);
  }
}
