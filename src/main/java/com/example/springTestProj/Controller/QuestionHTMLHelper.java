package com.example.springTestProj.Controller;

import com.example.springTestProj.Entities.QuestionEntities.*;
import javafx.css.Match;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class QuestionHTMLHelper {

   // public static String getHTML(String )
    public void addShortAnswerHTML(ShortAnswerQuestion shortAnswerQuestion, String file){
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
