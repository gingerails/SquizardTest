package com.example.springTestProj.Controller.CreateQuestionWindows;


import com.example.springTestProj.Controller.QuestionHTMLHelper;
import com.example.springTestProj.Controller.TestMakerController;
import com.example.springTestProj.Entities.QuestionEntities.ShortAnswerQuestion;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.QuestionService.ShortAnswerQService;
import com.example.springTestProj.Service.TestService;
import com.example.springTestProj.Service.UserService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;

//This class controls the short answer question screen and adds it to db
@Component
@FxmlView("/shortAnswerQ.fxml")
public class ShortQuestionController implements ControlDialogBoxes {
    //initializing Services, FX Weaver, and all interactive GUI items
    private final UserService userService;
    private final FxWeaver fxWeaver;
    private final ShortAnswerQService shortAnswerQService;
    private final QuestionHTMLHelper questionHTMLHelper;
    private final TestMakerController testMakerController;
    private final TestService testService;
    private Stage stage;

    @FXML
    private Button add;
    @FXML
    private TextField questionField,answerField,gradingInstructions,instructorComment,referenceMaterial,questionContent,referenceSection;
    @FXML
    private VBox shortQuestionBox;
    @FXML
    private Label error,aL;

    public static String path = "src\\main\\resources\\";
    public static String pathTo = "";
    public File ag=null;
    public File qg=null;
    String cSection="";
    String cClass="";
    
    //constructor
    public ShortQuestionController(UserService userService, FxWeaver fxWeaver, ShortAnswerQService shortAnswerQService, QuestionHTMLHelper questionHTMLHelper, TestMakerController testMakerController, TestService testService) {
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.shortAnswerQService = shortAnswerQService;
        this.questionHTMLHelper = questionHTMLHelper;
        this.testMakerController = testMakerController;
        this.testService = testService;
    }

    //initialize is automatically called and this is where we store button action events
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
	
                      
        pathTo = path+cClass+"\\" +cSection+"\\";
        
        //setup window
        this.stage = new Stage();
        stage.setTitle("Add Short Answer Question");
        stage.setScene(new Scene(shortQuestionBox));
        
        //controls add button
        this.add.setOnAction(actionEvent -> {
            try {
                createQuestion();
            } catch (IOException e) {
                throw new RuntimeException(e);
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
                //System.out.println(pic.getId());
                Stage fStage = new Stage();
        File file1 = file.showOpenDialog(fStage);  
        System.out.println(file1);  
        return file1;
       
   }
   
   //copy contents  of section and course to temp file
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
           Files.copy(src,dest);
       }
   }
   
    // create question adn puts in HTML files. it grabs all textfield data to put in HTML
    public void createQuestion() throws IOException {
        // gets the current stage, sets the scene w the create account control/view (fxweaver), then updates stage w that scene
        System.out.println("Add PRSSEDDDD");
        if (answerField.getText().isBlank() || questionContent.getText().isBlank()) {
            System.out.println("SOMETHING WAS LEFT BLANK");
            error.setText("Error: Must fill out each choice, question and answer!");
        } else {
            String question = questionContent.getText();
            String correctAnswer = answerField.getText();
            ShortAnswerQuestion shortAnswerQuestion = shortAnswerQService.createSQuestion(question, correctAnswer);
            checkFieldsAndAddQuestion(shortAnswerQuestion);
            Test currentTest = getCurrentTestSectionInfo();
            String testFile = currentTest.getTestName();
            addHTML(pathTo + testFile, pathTo + "KEY_" + testFile);
            testMakerController.refresh();
            stage.close();
        }
    }

    //grabs field items and adds to database
    public void checkFieldsAndAddQuestion(ShortAnswerQuestion shortAnswerQuestion) {

        if (!referenceMaterial.getText().isBlank()) {
            String refMaterial = referenceMaterial.getText();
            shortAnswerQuestion.setReferenceMaterial(refMaterial);
        }
        if (!referenceSection.getText().isBlank()) {
            String refSection = referenceSection.getText();
            shortAnswerQuestion.setTextReferenceSection(refSection);
        }
        if (!instructorComment.getText().isBlank()) {
            String comment = instructorComment.getText();
            shortAnswerQuestion.setInstructorComment(comment);
        }
        if (!gradingInstructions.getText().isBlank()) {
            String instructions = gradingInstructions.getText();
            shortAnswerQuestion.setGradingInstruction(instructions);
        }
        shortAnswerQuestion.setCreatorId(userService.returnCurrentUserID());
        shortAnswerQService.saveQuestionToRepository(shortAnswerQuestion);
        Test currentTest = getCurrentTestSectionInfo();
        testService.addShortAnswerQ(currentTest, shortAnswerQuestion);

    }

    //refreshes html
    public void addHTML(String file, String keyFile) throws IOException {
        questionHTMLHelper.updateSections(file, keyFile);
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
}

