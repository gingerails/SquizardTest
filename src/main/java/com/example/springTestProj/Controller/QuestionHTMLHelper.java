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

    }
    public void addMultiChoiceHTML(MultiChoiceQuestion multiChoiceQuestion, String file){

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

    }

}
