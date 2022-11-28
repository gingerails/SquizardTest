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

    public static String path = "src\\main\\resources\\";
    public static String pathTo = "";

    public QuestionHTMLHelper(TestService testService, ShortAnswerQService shortAnswerQService, EssayQuestionService essayQuestionService, MultiChoiceQService multiChoiceQService, MatchingQService matchingQService, TrueFalseQService trueFalseQService, TestMakerController testMakerController) {
        this.testService = testService;
        this.shortAnswerQService = shortAnswerQService;
        this.essayQuestionService = essayQuestionService;
        this.multiChoiceQService = multiChoiceQService;
        this.matchingQService = matchingQService;
        this.trueFalseQService = trueFalseQService;
        this.testMakerController = testMakerController;
    }

    public static File createNewFile(String testName) throws IOException {
        
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
	
                
                //Files.deleteIfExists(Paths.get("temp.txt"));
        System.out.println(cClass+" "+cSection);
        
        pathTo = path+cClass+"\\" +cSection+"\\";
        
        
        
        
        
        File templateFile = new File(path+ "template.html");
        File newFile = new File(pathTo + testName);
        Files.copy(templateFile.toPath(), newFile.toPath()); // copy template file

        // Test Key
        File keyTemplateFile = new File(path + "KEY_template.html");
        File testKeyFile = new File(pathTo + "KEY_" + testName);
        Files.copy(keyTemplateFile.toPath(), testKeyFile.toPath()); // copy template file

        String htmlString = Files.readString(Path.of(newFile.getPath()));
        String displayName = testName.replace(".html", "");
        String testHtmlString = htmlString.replace("$testName", displayName);

        PrintWriter pwrTest = new PrintWriter(pathTo + testName);
        pwrTest.println(testHtmlString);
        pwrTest.close();

        htmlString = Files.readString(Path.of(testKeyFile.getPath()));

        String keyHtmlString = htmlString.replace("$testName", " KEY " + displayName + " KEY ");
        PrintWriter pwrKey = new PrintWriter(pathTo + "KEY_" + testName);
        pwrKey.println(keyHtmlString);
        pwrKey.close();
        return  newFile;
    }

    public void getReplacement(String testFile, File newFile, String addHTML, String htmlString, String startSection, String endMCSect, int sectLength, String answerHTML, String keyHtmlString) throws FileNotFoundException {
        int startIndex = htmlString.indexOf(startSection);
        int endIndex = htmlString.indexOf(endMCSect, startIndex);
        String replaceHTML = htmlString.substring(startIndex + sectLength , endIndex);  // inbetween section tags. need to be copied and appended to

        String testHtmlString = htmlString.replace(replaceHTML, addHTML);
        PrintWriter testPrintWriter = new PrintWriter(newFile);
        testPrintWriter.println(testHtmlString);
        testPrintWriter.close();

        int keyStartIndex = keyHtmlString.indexOf(startSection);
        int keyEndIndex = keyHtmlString.indexOf(endMCSect, keyStartIndex);
        String keyReplaceHTML = keyHtmlString.substring(keyStartIndex + sectLength , keyEndIndex);  // inbetween section tags. need to be copied and appended to

        String thisKeyHtmlString = keyHtmlString.replace(keyReplaceHTML, answerHTML);
        PrintWriter keyPrintWriter = new PrintWriter(pathTo + "KEY_" + testFile);
        keyPrintWriter.println(thisKeyHtmlString);
        keyPrintWriter.close();

        testMakerController.refresh();
    }


    /**
     * I know this is huge and should be made reusable. Very nasty code :(
     *
     * @param thisTest
     * @param file
     * @param keyFile
     * @throws IOException
     */
    public void updateShortAnswerHTML(Test thisTest, String file, String keyFile)  throws IOException{
        File templateFile = new File(file);
        File newFile = new File(file);
        Files.copy(templateFile.toPath(), newFile.toPath()); // copy current version of file

        File keyTemplateFile = new File(keyFile);
        File newKeyFile = new File(keyFile);
        Files.copy(keyTemplateFile.toPath(), newKeyFile.toPath()); // copy current version of file
        String keyHtmlString = Files.readString(newKeyFile.toPath());

        int shortAnsCount = 0;
        String addHTML = "<h1>Short Answer </h1>"
                + "<div id = \"saPoints\"> <div id=\"lazyinsert1\"></div> </div>\n";
        String shortAQIDs = thisTest.getShortAnswerQ();
        String[] shortAStr = shortAQIDs.split(",");
        String[] shortAnswers = Arrays.copyOfRange(shortAStr, 1, shortAStr.length);

        String answerHTML = "";
        String gradingInstructions = "";
        for(String id : shortAnswers){
            shortAnsCount++;
            ShortAnswerQuestion shortAnswerQuestion = shortAnswerQService.findQuestionByID(id);
            String questionContent = shortAnswerQuestion.getQuestionContent();
            String correctAnswer = shortAnswerQuestion.getCorrectAnswer();
            addHTML = addHTML + ("<p><strong>" + shortAnsCount + ". "
                    +  questionContent + "</strong></p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n");
            if(shortAnswerQuestion.getGradingInstruction() != ""){
                gradingInstructions = "<p style=\"color:blue;\"> Grading Instructions: " + shortAnswerQuestion.getGradingInstruction() + "</p>\n";
            }
            answerHTML = gradingInstructions + addHTML + "<p style=\"color:red;\"> Correct Answer: " + correctAnswer + "</p>\n";
        }

        String htmlString = Files.readString(newFile.toPath());
        String shortAnswerSect = "<section id=\"ShortAnswer\">";
        String endMCSect = "</section>";
        int sectLength = shortAnswerSect.length();
        getReplacement(thisTest.getTestName(), newFile, addHTML, htmlString, shortAnswerSect, endMCSect, sectLength, answerHTML, keyHtmlString);
    }


    public void updateEssayHTML(Test thisTest, String file, String keyFile)  throws IOException{
        File templateFile = new File(file);
        File newFile = new File(file);
        Files.copy(templateFile.toPath(), newFile.toPath()); // copy current version of file

        File keyTemplateFile = new File(keyFile);
        File newKeyFile = new File(keyFile);
        Files.copy(keyTemplateFile.toPath(), newKeyFile.toPath()); // copy current version of file
        String keyHtmlString = Files.readString(newKeyFile.toPath());

        int essayCount = 0;
        String addHTML = "<h1>Essay </h1>"
                + "<div id = \"essayPoints\"> <div id=\"lazyinsert2\"></div> </div>\n";
        String essayQIDs = thisTest.getEssayQ();
        String[] essayArrStr = essayQIDs.split(",");
        String[] essayQuestions = Arrays.copyOfRange(essayArrStr, 1, essayArrStr.length);

        String answerHTML = "";
        String gradingInstructions = "";
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
            if(essayQuestion.getGradingInstruction() != ""){
                gradingInstructions = "<p style=\"color:blue;\"> Grading Instructions: " + essayQuestion.getGradingInstruction() + "</p>\n";
            }
            answerHTML = gradingInstructions + addHTML + "<p style=\"color:red;\"> Correct Answer: " + correctAnswer + "</p>\n";
        }

        String htmlString = Files.readString(newFile.toPath());
        String EssaySect = "<section id=\"Essay\">";
        String endEssaySect = "</section>";
        int sectLength = EssaySect.length();
        getReplacement(thisTest.getTestName(), newFile, addHTML, htmlString, EssaySect, endEssaySect, sectLength, answerHTML, keyHtmlString);
    }

    public void updateTrueFalseHTML(Test thisTest, String file, String keyFile)  throws IOException{
        File templateFile = new File(file);
        File newFile = new File(file);
        Files.copy(templateFile.toPath(), newFile.toPath()); // copy current version of file

        File keyTemplateFile = new File(keyFile);
        File newKeyFile = new File(keyFile);
        Files.copy(keyTemplateFile.toPath(), newKeyFile.toPath()); // copy current version of file
        String keyHtmlString = Files.readString(newKeyFile.toPath());


        int tfCount = 0;
        String addHTML = "<h1> True / False </h1>"
                + "<div id = \"tfPoints\"> <div id=\"lazyinsert3\"></div> </div>\n";
        String trueFalseQIDs = thisTest.getTrueFalseQ();
        String[] trueFalseArrStr = trueFalseQIDs.split(",");
        String[] trueFalseQuestions = Arrays.copyOfRange(trueFalseArrStr, 1, trueFalseArrStr.length);

        String answerHTML = "";
        String gradingInstructions = "";
        for(String id : trueFalseQuestions){
            tfCount++;
            TrueFalseQuestion trueFalseQuestion = trueFalseQService.findQuestionByID(id);
            String questionContent = trueFalseQuestion.getQuestionContent();
            String correctAnswer = trueFalseQuestion.getCorrectAnswer();
            addHTML = addHTML + ("<p><strong>" + tfCount + ". "
                    +  questionContent + "</strong></p>" + "\n"
                    + "<p> T: ____ </p>" + "\n"
                    + "<p> F: ____ </p>" + "\n");
            if(trueFalseQuestion.getGradingInstruction() != ""){
                gradingInstructions = "<p style=\"color:blue;\"> Grading Instructions: " + trueFalseQuestion.getGradingInstruction() + "</p>\n";
            }
            answerHTML = gradingInstructions + addHTML + "<p style=\"color:red;\"> Correct Answer: " + correctAnswer + "</p>\n";
        }

        String htmlString = Files.readString(newFile.toPath());
        String TrueFalseSect = "<section id=\"TrueFalse\">";
        String endTFSect = "</section>";
        int sectLength = TrueFalseSect.length();
        getReplacement(thisTest.getTestName(), newFile, addHTML, htmlString, TrueFalseSect, endTFSect, sectLength, answerHTML, keyHtmlString);
    }


    public void updateMatchingHTML(Test thisTest, String file, String keyFile)  throws IOException{
        File templateFile = new File(file);
        File newFile = new File(file);
        Files.copy(templateFile.toPath(), newFile.toPath()); // copy current version of file

        File keyTemplateFile = new File(keyFile);
        File newKeyFile = new File(keyFile);
        Files.copy(keyTemplateFile.toPath(), newKeyFile.toPath()); // copy current version of file
        String keyHtmlString = Files.readString(newKeyFile.toPath());

        int matchCount = 0;
        String addHTML = "<h1> Matching </h1>"
                + "<div id = \"matchPoints\"> <div id=\"lazyinsert4\"></div> </div>\n";
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

        String answerHTML = addHTML;

        for ( Map.Entry<String, String> keyVal: termAndDef.entrySet()) {
            answerHTML = answerHTML + ("<p style=\"color:red;\"> Correct Answer: " +
                    "<div class=\"row\">\n" +
                    "  <div class=\"column\">\n" +
                    "    <p style=\"color:red;\">" + keyVal.getKey() + ":_____" + "</p>\n" +
                    "  </div>\n" +
                    "  <div class=\"column\">\n" +
                    "    <p style=\"color:red;\"> &emsp; " + keyVal.getValue() + "</p>\n" +
                    "  </div>\n" +
                    "  </div>\n");
        }
        String htmlString = Files.readString(newFile.toPath());
        String matchingSect = "<section id=\"Matching\">";
        String endTFSect = "</section>";
        int sectLength = matchingSect.length();
        getReplacement(thisTest.getTestName(), newFile, addHTML, htmlString, matchingSect, endTFSect, sectLength, answerHTML, keyHtmlString);
    }

    public void updateMultiChoiceHTML(Test thisTest, String file, String keyFile)  throws IOException{
        File templateFile = new File(file);
        File newFile = new File(file);
        Files.copy(templateFile.toPath(), newFile.toPath()); // copy current version of file

        File keyTemplateFile = new File(keyFile);
        File newKeyFile = new File(keyFile);
        Files.copy(keyTemplateFile.toPath(), newKeyFile.toPath()); // copy current version of file
        String keyHtmlString = Files.readString(newKeyFile.toPath());

        int mcCount = 0;
        String addHTML = "<h1> Multiple Choice </h1>"
                + "<div id = \"mcPoints\"> <div id=\"lazyinsert5\"></div> </div>\n";

        String multiChoiceQs = thisTest.getMultiChoiceQ();
        String[] multiChoiceArrStr = multiChoiceQs.split(",");
        String[] multiChoiceQuestions = Arrays.copyOfRange(multiChoiceArrStr, 1, multiChoiceArrStr.length);

        String answerHTML = "";
        String gradingInstructions = "";
        for(String id : multiChoiceQuestions){
            ArrayList<String> possibleAnswers = new ArrayList<>();
            mcCount++;
            MultiChoiceQuestion multiChoiceQuestion = multiChoiceQService.findQuestionByID(id);
            String questionContent = multiChoiceQuestion.getQuestionContent();
            String correctAnswer = multiChoiceQuestion.getCorrectAnswer();
            String falseAnswersList = multiChoiceQuestion.getFalseAnswer().replace("[","").replace("]","");
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
            if(multiChoiceQuestion.getGradingInstruction() != ""){
                gradingInstructions = "<p style=\"color:blue;\"> Grading Instructions: " + multiChoiceQuestion.getGradingInstruction() + "</p>\n";
            }
            answerHTML = gradingInstructions + addHTML + "<p style=\"color:red;\"> Correct Answer: " + correctAnswer + "</p>\n";


        }

        String htmlString = Files.readString(newFile.toPath());
        String multiChoiceSect = "<section id=\"MultiChoice\">";
        String endTFSect = "</section>";
        int sectLength = multiChoiceSect.length();
        getReplacement(thisTest.getTestName(), newFile, addHTML, htmlString, multiChoiceSect, endTFSect, sectLength, answerHTML, keyHtmlString);
    }

    public static <K, V> Map<K, V> zipToMap(ArrayList<K> keys, ArrayList<V> values) {
        return IntStream.range(0, keys.size()).boxed()
                .collect(Collectors.toMap(keys::get, values::get));
    }

    /**
     * Once a question is added or removed, we will re-read the questions from the repo and put them in the html
     */
    public void updateSections(String file, String keyFile) throws IOException {
        Test thisTest = testService.returnThisTest();
        if(thisTest.getEssayQ() != null){
            updateEssayHTML(thisTest, file, keyFile);
        }
        if(thisTest.getShortAnswerQ() != null){
            updateShortAnswerHTML(thisTest, file, keyFile);
        }
        if(thisTest.getTrueFalseQ() != null){
            updateTrueFalseHTML(thisTest, file, keyFile);
        }
        if(thisTest.getMatchingQ() != null){
            updateMatchingHTML(thisTest, file, keyFile);
        }
        if(thisTest.getMultiChoiceQ() != null){
            updateMultiChoiceHTML(thisTest, file, keyFile);
        }
    }

    public void generateTestKey(String file) throws  IOException{


    }
}
