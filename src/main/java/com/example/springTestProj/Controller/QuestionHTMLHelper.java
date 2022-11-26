package com.example.springTestProj.Controller;

import com.example.springTestProj.Entities.QuestionEntities.*;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.QuestionService.*;
import com.example.springTestProj.Service.TestService;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Component
public class QuestionHTMLHelper {

    private final TestService testService;
    private final ShortAnswerQService shortAnswerQService;
    private final EssayQuestionService essayQuestionService;
    private final MultiChoiceQService multiChoiceQService;
    private final MatchingQService matchingQService;
    private final TrueFalseQService trueFalseQService;
    private final TestMakerController testMakerController;

    public static String path = "src\\main\\resources\\generatedTests\\";

    public QuestionHTMLHelper(TestService testService, ShortAnswerQService shortAnswerQService, EssayQuestionService essayQuestionService, MultiChoiceQService multiChoiceQService, MatchingQService matchingQService, TrueFalseQService trueFalseQService, TestMakerController testMakerController) {
        this.testService = testService;
        this.shortAnswerQService = shortAnswerQService;
        this.essayQuestionService = essayQuestionService;
        this.multiChoiceQService = multiChoiceQService;
        this.matchingQService = matchingQService;
        this.trueFalseQService = trueFalseQService;
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


    /**
     * I know this is huge and should be made reusable. Very nasty code :(
     * @param thisTest
     * @param file
     * @throws IOException
     */
    public void updateShortAnswerHTML(Test thisTest, String file)  throws IOException{
        File templateFile = new File(file);
        File newFile = new File(file);
        Files.copy(templateFile.toPath(), newFile.toPath()); // copy current version of file

        int shortAnsCount = 0;
        String addHTML = "<h1>Short Answer: </h1>";
        String shortAQIDs = thisTest.getShortAnswerQ();
        String[] shortAStr = shortAQIDs.split(",");
        String[] shortAnswers = Arrays.copyOfRange(shortAStr, 1, shortAStr.length);

        for(String id : shortAnswers){
            shortAnsCount++;
            ShortAnswerQuestion shortAnswerQuestion = shortAnswerQService.findQuestionByID(id);
            String questionContent = shortAnswerQuestion.getQuestionContent();
            String correctAnswer = shortAnswerQuestion.getCorrectAnswer();
            addHTML = addHTML + ("<p><strong>" + shortAnsCount + ". "
                    +  questionContent + "</strong></p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n");
        }

        String htmlString = Files.readString(newFile.toPath());
        String shortAnswerSect = "<section id=\"ShortAnswer\">";
        String endMCSect = "</section>";
        int sectLength = shortAnswerSect.length();
        getReplacement(newFile, addHTML, htmlString, shortAnswerSect, endMCSect, sectLength);
    }

    public void getReplacement(File newFile, String addHTML, String htmlString, String startSection, String endMCSect, int sectLength) throws FileNotFoundException {
        int startIndex = htmlString.indexOf(startSection);
        int endIndex = htmlString.indexOf(endMCSect, startIndex);
        String replaceHTML = htmlString.substring(startIndex + sectLength , endIndex);  // inbetween section tags. need to be copied and appended to

        htmlString = htmlString.replace(replaceHTML, addHTML);
        PrintWriter printWriter = new PrintWriter(newFile);
        printWriter.println(htmlString);
        printWriter.close();

        testMakerController.refresh();
    }

    public void updateEssayHTML(Test thisTest, String file)  throws IOException{
        File templateFile = new File(file);
        File newFile = new File(file);
        Files.copy(templateFile.toPath(), newFile.toPath()); // copy current version of file

        int essayCount = 0;
        String addHTML = "<h1>Essay: </h1>";
        String essayQIDs = thisTest.getEssayQ();
        String[] essayArrStr = essayQIDs.split(",");
        String[] essayQuestions = Arrays.copyOfRange(essayArrStr, 1, essayArrStr.length);

        for(String id : essayQuestions){
            essayCount++;
            EssayQuestion essayQuestion = essayQuestionService.findQuestionByID(id);
            String questionContent = essayQuestion.getQuestionContent();
            String correctAnswer = essayQuestion.getCorrectAnswer();
            addHTML = addHTML + ("<p><strong>" + essayCount + ". "
                    +  questionContent + "</strong></p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n");
        }

        String htmlString = Files.readString(newFile.toPath());
        String EssaySect = "<section id=\"Essay\">";
        String endEssaySect = "</section>";
        int sectLength = EssaySect.length();
        getReplacement(newFile, addHTML, htmlString, EssaySect, endEssaySect, sectLength);
    }

    /**
     * Once a question is added or removed, we will re-read the questions from the repo and put them in the html
     */
    public void updateSections(String file) throws IOException {
        Test thisTest = testService.returnThisTest();
        if(thisTest.getEssayQ() != null){
            updateEssayHTML(thisTest, file);

        }
        if(thisTest.getShortAnswerQ() != null){
            updateShortAnswerHTML(thisTest, file);
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
