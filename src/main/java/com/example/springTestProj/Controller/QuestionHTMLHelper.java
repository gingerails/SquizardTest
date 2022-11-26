package com.example.springTestProj.Controller;

import com.example.springTestProj.Entities.QuestionEntities.*;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.QuestionService.*;
import com.example.springTestProj.Service.TestService;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public void updateTrueFalseHTML(Test thisTest, String file)  throws IOException{
        File templateFile = new File(file);
        File newFile = new File(file);
        Files.copy(templateFile.toPath(), newFile.toPath()); // copy current version of file

        int tfCount = 0;
        String addHTML = "<h1> True / False: </h1>";
        String trueFalseQIDs = thisTest.getTrueFalseQ();
        String[] trueFalseArrStr = trueFalseQIDs.split(",");
        String[] trueFalseQuestions = Arrays.copyOfRange(trueFalseArrStr, 1, trueFalseArrStr.length);

        for(String id : trueFalseQuestions){
            tfCount++;
            TrueFalseQuestion trueFalseQuestion = trueFalseQService.findQuestionByID(id);
            String questionContent = trueFalseQuestion.getQuestionContent();
            String correctAnswer = trueFalseQuestion.getCorrectAnswer();
            addHTML = addHTML + ("<p><strong>" + tfCount + ". "
                    +  questionContent + "</strong></p>" + "\n"
                    + "<p> T: ____ </p>" + "\n"
                    + "<p> F: ____ </p>" + "\n");
        }

        String htmlString = Files.readString(newFile.toPath());
        String TrueFalseSect = "<section id=\"TrueFalse\">";
        String endTFSect = "</section>";
        int sectLength = TrueFalseSect.length();
        getReplacement(newFile, addHTML, htmlString, TrueFalseSect, endTFSect, sectLength);
    }


    public void updateMatchingHTML(Test thisTest, String file)  throws IOException{
        File templateFile = new File(file);
        File newFile = new File(file);
        Files.copy(templateFile.toPath(), newFile.toPath()); // copy current version of file

        int matchCount = 0;
        String addHTML = "<h1> Matching: </h1>";
        String matchQIDs = thisTest.getMatchingQ();
        String[] matchingArrStr = matchQIDs.split(",");
        String[] matchingQuestions = Arrays.copyOfRange(matchingArrStr, 1, matchingArrStr.length);

        ArrayList<String> termDefinitions = new ArrayList<>();
        ArrayList<String> terms = new ArrayList<>();
        for(String id : matchingQuestions){
            MatchingQuestion matchingQuestion = matchingQService.findQuestionByID(id);
            String questionContent = matchingQuestion.getTerm();
            String correctAnswer = matchingQuestion.getCorrectAnswer();
            termDefinitions.add(correctAnswer);
            terms.add(questionContent);
        }
        Map<String, String> termAndDef = zipToMap(terms, termDefinitions); // Key-value pair for term and def. Use for answer key
        Collections.shuffle(termDefinitions);
        Collections.shuffle(terms);
        Map<String, String> shuffledTermAndDef = zipToMap(terms, termDefinitions); // Key-value pair for term and def. Use for answer key
        for ( Map.Entry<String, String> keyVal: shuffledTermAndDef.entrySet()) {
            matchCount++;
            addHTML = addHTML + (
                    "<div class=\"row\">\n" +
                            "  <div class=\"column\">\n" +
                            "    <p>"+ matchCount + ".   " + keyVal.getKey() + ":_____" + "</p>\n" +
                            "  </div>\n" +
                            "  <div class=\"column\">\n" +
                            "    <p>" +  "&emsp;" + keyVal.getValue() + "</p>\n" +
                            "  </div>\n" +
                            "  </div>\n"
            );
        }
        String htmlString = Files.readString(newFile.toPath());
        String matchingSect = "<section id=\"Matching\">";
        String endTFSect = "</section>";
        int sectLength = matchingSect.length();
        getReplacement(newFile, addHTML, htmlString, matchingSect, endTFSect, sectLength);
    }

    public void updateMultiChoiceHTML(Test thisTest, String file)  throws IOException{
        File templateFile = new File(file);
        File newFile = new File(file);
        Files.copy(templateFile.toPath(), newFile.toPath()); // copy current version of file

        int mcCount = 0;
        String addHTML = "<h1> Multiple Choice: </h1>";
        String multiChoiceQs = thisTest.getMultiChoiceQ().replace("[","").replace("]","");
        String[] multiChoiceArrStr = multiChoiceQs.split(",");
        String[] trueFalseQuestions = Arrays.copyOfRange(multiChoiceArrStr, 1, multiChoiceArrStr.length);

        for(String id : trueFalseQuestions){
            ArrayList<String> possibleAnswers = new ArrayList<>();
            mcCount++;
            MultiChoiceQuestion multiChoiceQuestion = multiChoiceQService.findQuestionByID(id);
            String questionContent = multiChoiceQuestion.getQuestionContent();
            String correctAnswer = multiChoiceQuestion.getCorrectAnswer();
            String falseAnswersList = multiChoiceQuestion.getFalseAnswer();
            String[] falseAnswers = falseAnswersList.split(",");
            for (String answ: falseAnswers) {
                possibleAnswers.add(answ);
            }
            Collections.shuffle(possibleAnswers);
            addHTML = addHTML + ("<p><strong>" + mcCount + ".  "
                    +  questionContent + "</strong></p>" + "\n"
                    + "<p> a: " + possibleAnswers.get(0)  +"</p>" + "\n"
                    + "<p> b: " + possibleAnswers.get(1)  +"</p>" + "\n"
                    + "<p> c: " + possibleAnswers.get(2)  +"</p>" + "\n"
                    + "<p> d: " + possibleAnswers.get(3)  +"</p>" + "\n");
        }

        String htmlString = Files.readString(newFile.toPath());
        String multiChoiceSect = "<section id=\"MultiChoice\">";
        String endTFSect = "</section>";
        int sectLength = multiChoiceSect.length();
        getReplacement(newFile, addHTML, htmlString, multiChoiceSect, endTFSect, sectLength);
    }

    public static <K, V> Map<K, V> zipToMap(ArrayList<K> keys, ArrayList<V> values) {
        return IntStream.range(0, keys.size()).boxed()
                .collect(Collectors.toMap(keys::get, values::get));
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
        if(thisTest.getTrueFalseQ() != null){
            updateTrueFalseHTML(thisTest, file);
        }
        if(thisTest.getMatchingQ() != null){
            updateMatchingHTML(thisTest, file);
        }
        if(thisTest.getMultiChoiceQ() != null){
            updateMultiChoiceHTML(thisTest, file);
        }


    }

}
