
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

//This class controls the essay question screen and adds it to db
@Component
@FxmlView("/essayQuestion.fxml")
public class EssayQuestionController implements ControlDialogBoxes {
    //initializing Services, FX Weaver, and all interactive GUI items
    private final UserService userService;
    private final TestService testService;
    private final EssayQuestionService essayQuestionService;
    private final QuestionHTMLHelper questionHTMLHelper;
    private final TestMakerController testMakerController;
    private Stage stage;

    @FXML
    private Button add,answerGraphicButton,questionGraphicButton;
    @FXML
    private VBox essayQuestionBox;
    @FXML
    private TextField answerTextField,sectionsTextField,MaterialTextField,instructionTextField,commentTextField,questionTextField;
    @FXML
    private Label error,aG,qG;
    private final FxWeaver fxWeaver;
    public static String path = "src\\main\\resources\\";
    public static String pathTo = "";
    public File ag=null;
    public File qg=null;
    String cSection="";
    String cClass="";
    
    //constructor
    public EssayQuestionController(UserService userService, TestService testService, EssayQuestionService essayQuestionService, QuestionHTMLHelper questionHTMLHelper, TestMakerController testMakerController, FxWeaver fxWeaver) {
        this.testService = testService;
        this.essayQuestionService = essayQuestionService;
        this.questionHTMLHelper = questionHTMLHelper;
        this.testMakerController = testMakerController;
        this.fxWeaver = fxWeaver;
        this.userService = userService;
    }

    //initialize is automatically called and this is where we store button action events
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
	
                      
        pathTo = path+cClass+"\\" +cSection+"\\";
        
        //setup for window
        this.stage = new Stage();
        stage.setTitle("Add Essay Question");
        stage.setScene(new Scene(essayQuestionBox));
        
        //controls add button 
        this.add.setOnAction(actionEvent -> {
            
            try {
                createQuestion();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        
        //controls add graphic button
        this.answerGraphicButton.setOnAction(actionEvent -> {
            try {
                copyandFile();
            } catch (IOException ex) {
                Logger.getLogger(EssayQuestionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                getG(ag,aG);
            } catch (IOException ex) {
                Logger.getLogger(EssayQuestionController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
      
    }
    
    //checks if attachment file exists
   public void checkAttachmentFile()
   {
       Path pathA=Paths.get(path +"\\" +cClass + "\\" + cSection+"\\"+testService.returnThisTest());
       if(Files.exists(pathA))
       {
       
       }
       else
       {
           new File(pathA.toString()).mkdirs();
       }

   }
   
   //get files with popup file selection window
   public File getFiles()
   {
        FileChooser file = new FileChooser();  
        file.setTitle("Open");  
        Stage fStage = new Stage();
        File file1 = file.showOpenDialog(fStage);  
        System.out.println(file1);  
        return file1;
       
   }
   
   //copy contents  of section and course to temp file
   public void copyandFile() throws FileNotFoundException, IOException
   {
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

      
   }
   
   //checks duplication on attachment files
   public void getG(File ga, Label l) throws IOException
   {
      ga=getFiles();
      Path src=Paths.get(ga.toString());
      File f = new File(ga.getName());
      String v=f.getName();
      
      Path dest=Paths.get(path +"\\" +cClass + "\\" + cSection+"\\"+testService.returnThisTest()+"\\"+v);
      
     
       if(Files.exists(dest))
       {
           error.setText("Error: Cant have a duplicate attachment on test");
       }
       else
       {
           l.setText(v);
           checkAttachmentFile();
           //copy file and relocate
           Files.copy(src,dest);
       }
   }
   
   // create question adn puts in HTML files. it grabs all textfield data to put in HTML
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
    
    //grabs field items and adds to database
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

    //shows stage and center window on screen
    @Override
    public void show(Stage thisStage) {
        stage.show();
        this.stage.centerOnScreen();
    }

  //refreshes html
  public void addHTML( String file, String keyFile) throws IOException {
    questionHTMLHelper.updateSections(file, keyFile);
  }
}
