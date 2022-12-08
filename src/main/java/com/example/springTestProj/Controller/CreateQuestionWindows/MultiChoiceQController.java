package com.example.springTestProj.Controller.CreateQuestionWindows;

import static com.example.springTestProj.Controller.CreateQuestionWindows.MatchingQController.path;
import static com.example.springTestProj.Controller.CreateQuestionWindows.MatchingQController.pathTo;
import com.example.springTestProj.Controller.QuestionHTMLHelper;
import com.example.springTestProj.Controller.TestMakerController;
import com.example.springTestProj.Entities.QuestionEntities.MultiChoiceQuestion;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.QuestionService.MultiChoiceQService;
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
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;

//This class controls the multichoice question screen and adds it to db
@Component
@FxmlView("/multiChoiceQ.fxml")
public class MultiChoiceQController implements ControlDialogBoxes {
    //initializing Services, FX Weaver, and all interactive GUI items
    private final UserService userService;
    private final FxWeaver fxWeaver;
    private final TestService testService;
    private final MultiChoiceQService multiChoiceQService;
    private final TestMakerController testMakerController;
    private final QuestionHTMLHelper questionHTMLHelper;
    private Stage stage;

    @FXML
    private Button add;
    @FXML
    private VBox mcQuestionBox;
    @FXML
    private TextField referenceSection,choice1Field,answerTextField,choice4Field,choice3Field,choice2Field,questionContent,referenceMaterial,instructorComment,gradingInstructions;
    @FXML
    private Label error,gL;

    public static String path = "src\\main\\resources\\";
    public static String pathTo = "";
    public File ag=null;
    public File qg=null;
    String cSection="";
    String cClass="";
    
    //constructor
    public MultiChoiceQController(UserService userService, FxWeaver fxWeaver, TestService testService, MultiChoiceQService multiChoiceQService, TestMakerController testMakerController, QuestionHTMLHelper questionHTMLHelper) {
        this.testService = testService;
        this.fxWeaver = fxWeaver;
        this.userService = userService;
        this.multiChoiceQService = multiChoiceQService;
        this.testMakerController = testMakerController;
        this.questionHTMLHelper = questionHTMLHelper;
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
        stage.setTitle("Add Multiple Choice Question");
        stage.setScene(new Scene(mcQuestionBox));
        
        //controls add button
        this.add.setOnAction(actionEvent -> {
            System.out.print("Add question button pressed");
            //stage.close();
            try {
                createQuestion();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        
       
                
    }

    //shows stage and center window on screen
    @Override
    public void show(Stage thisStage) {
        stage.show();
        this.stage.centerOnScreen();
    }

    //checks if attachment already exists
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
        if (choice1Field.getText().isBlank() ||
                choice2Field.getText().isBlank() ||
                choice3Field.getText().isBlank() ||
                choice4Field.getText().isBlank() ||
                answerTextField.getText().isBlank() ||
                questionContent.getText().isBlank()) {
            System.out.println("SOMETHING WAS LEFT BLANK");
            error.setText("Error: Must fill out each choice, question and answer!");
        }
        else if (choice1Field.getText().equals(answerTextField.getText()) == false && choice2Field.getText().equals(answerTextField.getText()) == false && choice3Field.getText().equals(answerTextField.getText()) == false && choice4Field.getText().equals(answerTextField.getText()) == false) {
            error.setText("Error: Answer is not a choice");
        } else {
            String question = questionContent.getText();
            String correctAnswer = answerTextField.getText();
            String[] falseAnswers = {choice1Field.getText(), choice2Field.getText(), choice3Field.getText(), choice4Field.getText()};
            String falseAnswer = Arrays.toString(falseAnswers);

            MultiChoiceQuestion multiChoiceQuestion = multiChoiceQService.createMCQuestion(question, correctAnswer, falseAnswer);
            checkFieldsAndAddQuestion(multiChoiceQuestion);
            Test currentTest = getCurrentTestSectionInfo();
            String testFile = currentTest.getTestName();
            addHTML(pathTo + testFile, pathTo + "KEY_" + testFile);
            testMakerController.refresh();
            stage.close();
        }
    }

    //grabs field items and adds to database
    public void checkFieldsAndAddQuestion(MultiChoiceQuestion multiChoiceQuestion) {

        if (!referenceMaterial.getText().isBlank()) {
            String refMaterial = referenceMaterial.getText();
            multiChoiceQuestion.setReferenceMaterial(refMaterial);
        }
        if (!referenceSection.getText().isBlank()) {
            String refSection = referenceSection.getText();
            multiChoiceQuestion.setTextReferenceSection(refSection);
        }
        if (!instructorComment.getText().isBlank()) {
            String comment = instructorComment.getText();
            multiChoiceQuestion.setInstructorComment(comment);
        }
        if (!gradingInstructions.getText().isBlank()) {
            String instructions = gradingInstructions.getText();
            multiChoiceQuestion.setGradingInstruction(instructions);
        }
        multiChoiceQuestion.setCreatorId(userService.returnCurrentUserID());
        multiChoiceQService.saveQuestionToRepository(multiChoiceQuestion);
        Test currentTest = getCurrentTestSectionInfo();
        testService.addMCQuestion(currentTest, multiChoiceQuestion);// also save to test using test service
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

    //refreshes html
    public void addHTML(String file, String keyFile) throws IOException {
        questionHTMLHelper.updateSections(file, keyFile);
    }
}
