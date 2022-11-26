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

@Component
public class QuestionHTMLHelper {

    public static WebEngine engine;
    private final TestService testService;
    private final ShortAnswerQService shortAnswerQService;

    public QuestionHTMLHelper(TestService testService, ShortAnswerQService shortAnswerQService) {
        this.testService = testService;
        this.shortAnswerQService = shortAnswerQService;
    }

    // public static String getHTML(String )

    public static File createNewFile(String testName) throws IOException {
        String path = "src\\main\\resources\\generatedTests\\";
        File templateFile = new File(path + "template.html");
        File newFile = new File(path + testName);
        Files.copy(templateFile.toPath(), newFile.toPath()); // copy template file

        String htmlString = Files.readString(Path.of(newFile.getPath()));
        testName = testName.replace(".html", "");
        htmlString = htmlString.replace("$testName", testName);
        PrintWriter pwr = new PrintWriter(path + testName);
        pwr.println(htmlString);
        pwr.close();
        return  newFile;
    }



    public void addShortAnswerHTML(ShortAnswerQuestion shortAnswerQuestion, String file) throws IOException {
        String path = "src\\main\\resources\\generatedTests\\";
        File templateFile = new File(file);
        File newFile = new File(file);
        Files.copy(templateFile.toPath(), newFile.toPath()); // copy current version of file

        String htmlString = Files.readString(newFile.toPath());
        String multiChoiceSect = "<section id=\"MultiChoice\">";
        String endMCSect = "</section>";
        int sectLength = multiChoiceSect.length();
        int startSAIndex = htmlString.indexOf(multiChoiceSect);
        int endSAIndex = htmlString.indexOf(endMCSect);
        String replaceThis = htmlString.substring(startSAIndex - sectLength , endSAIndex);  // inbetween section tags. need to be copied and appended to

        htmlString = htmlString.replace(replaceThis, htmlString);
        try (FileWriter f = new FileWriter(file, true); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);) {

            p.println("<hr />" + "\n"
                    + "<p><strong>Short Answer: " + shortAnswerQuestion.getQuestionContent() + "</strong></p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n");
            b.close();
            p.close();
            f.close();
            TestMakerController.engine.reload();
            //engine.reload();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    /**
     * Once a question is added or removed, we will re-read the questions from the repo and put them in the html
     */
    public void updateSections(){
        Test thisTest = testService.returnThisTest();
        String essayQs = thisTest.getEssayQ();
        String[] arrStr = essayQs.split(",");
        for(String id : arrStr){

        }

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
