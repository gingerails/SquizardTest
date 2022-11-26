package com.example.springTestProj.Controller;

import com.example.springTestProj.Entities.QuestionEntities.*;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.QuestionService.ShortAnswerQService;
import com.example.springTestProj.Service.TestService;
import javafx.scene.web.WebEngine;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Component
public class QuestionHTMLHelper {

    private final TestService testService;
    private final ShortAnswerQService shortAnswerQService;
    private final TestMakerController testMakerController;

    public static String path = "src\\main\\resources\\generatedTests\\";

    public QuestionHTMLHelper(TestService testService, ShortAnswerQService shortAnswerQService, TestMakerController testMakerController) {
        this.testService = testService;
        this.shortAnswerQService = shortAnswerQService;
        this.testMakerController = testMakerController;
    }

    // public static String getHTML(String )

    public static File createNewFile(String testName) throws IOException {
        File templateFile = new File(path + "template.html");
        File newFile = new File(path + testName);
        Files.copy(templateFile.toPath(), newFile.toPath()); // copy template file

        String htmlString = Files.readString(Path.of(newFile.getPath()));
        String displayName = testName.replace(".html", "");
        htmlString = htmlString.replace("$testName", displayName);
        PrintWriter pwr = new PrintWriter(path + testName);
        pwr.println(htmlString);
        pwr.close();
        return  newFile;
    }


    public void getShortAnswerHTML(String file, String addHTML)  throws IOException{
        File templateFile = new File(file);
        File newFile = new File(file);
        Files.copy(templateFile.toPath(), newFile.toPath()); // copy current version of file

        String htmlString = Files.readString(newFile.toPath());
        String shortAnswerSect = "<section id=\"ShortAnswer\">";
        String endMCSect = "</section>";
        int sectLength = shortAnswerSect.length();
        int startSAIndex = htmlString.indexOf(shortAnswerSect);
        int endSAIndex = htmlString.indexOf(endMCSect);
        String shortAnswerHTML = htmlString.substring(startSAIndex + sectLength , endSAIndex);  // inbetween section tags. need to be copied and appended to

        htmlString = htmlString.replace(shortAnswerHTML, addHTML);
        PrintWriter printWriter = new PrintWriter(newFile);
        printWriter.println(htmlString);
        printWriter.close();

        testMakerController.refresh();
    }

    /**
     * Once a question is added or removed, we will re-read the questions from the repo and put them in the html
     */
    public void updateSections(String file) throws IOException {
        Test thisTest = testService.returnThisTest();
        String shortAQIDs = thisTest.getShortAnswerQ();
        String[] shortAStr = shortAQIDs.split(",");
        String[] shortAnswers = Arrays.copyOfRange(shortAStr, 1, shortAStr.length);

        int shortAnsCount = 0;
        String shortAnsQuestionsHTML = "";
        for(String id : shortAnswers){

            shortAnsCount++;
            ShortAnswerQuestion shortAnswerQuestion = shortAnswerQService.findQuestionByID(id);
            String questionContent = shortAnswerQuestion.getQuestionContent();
            String correctAnswer = shortAnswerQuestion.getCorrectAnswer();
            shortAnsQuestionsHTML = shortAnsQuestionsHTML + (shortAnsCount + "." + "\n"
                    + "<p><strong>Short Answer: " + questionContent + "</strong></p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n");
        }
        getShortAnswerHTML(file, shortAnsQuestionsHTML);

    }

    public void addMultiChoiceHTML(MultiChoiceQuestion multiChoiceQuestion, String file){
        String questionContent = multiChoiceQuestion.getQuestionContent();
        String qAnswers = multiChoiceQuestion.getFalseAnswer();
        String[] answersList = qAnswers
                .replace("[", "")
                .replace("]", "")
                .split(",");

        String answ1 = answersList[0];
        String answ2 = answersList[1];
        String answ3 = answersList[2];
        String answ4 = answersList[3];
        try ( FileWriter f = new FileWriter(file, true);  BufferedWriter b = new BufferedWriter(f);  PrintWriter p = new PrintWriter(b);) {

            p.println("<hr />" + "\n"
                    +"<p><span style='font-size:16px'><strong>"+questionContent+"</strong></span></p>"+"\n"

                    +"<p><span style='font-size:16px'>a. "+answ1+"</span></p>"+"\n"

                    +"<p><span style='font-size:16px'>b. "+answ2+"</span></p>"+"\n"

                    +"<p><span style='font-size:16px'>c. "+answ3+"</span></p>"+"\n"

                    +"<p><span style='font-size:16px'>d. "+answ4+"</span></p>"+"\n"
            );
            b.close();
            p.close();
            f.close();
            TestMakerController.engine.reload();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    public void addEssayHTML(EssayQuestion essayQuestion, String file){
      try (FileWriter f = new FileWriter(file, true);
           BufferedWriter b = new BufferedWriter(f);
           PrintWriter p = new PrintWriter(b)) {

       p.println("<hr />" + "\n"
               + "<p><strong>Essay: " + essayQuestion.getQuestionContent() + "</strong></p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n"
               + "<p>&nbsp;</p>" + "\n");
       b.close();
       p.close();
       f.close();
       TestMakerController.engine.reload();
      } catch (IOException i) {
       i.printStackTrace();
      }

    }
    public void addMatchingHTML(MatchingQuestion matchingQuestion, String file){

    }
    public void addTrueFalseHTML(TrueFalseQuestion trueFalseQuestion, String file){
        try ( FileWriter f = new FileWriter(file, true);  BufferedWriter b = new BufferedWriter(f);  PrintWriter p = new PrintWriter(b);) {

            p.println("<hr />" + "\n"
                    +"<p><span style='font-size:16px'><strong>T/F: </strong></span></p>"+"\n"
                    +"<p>"+trueFalseQuestion.getQuestionContent()+" ______</p>"+"\n"
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
