package com.example.springTestProj.Controller;

import com.example.springTestProj.Entities.QuestionEntities.*;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Service.QuestionService.*;
import com.example.springTestProj.Service.TestService;
import java.awt.image.BufferedImage;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

//This class controls the HTML editing
@Component
public class QuestionHTMLHelper {
    //initializing Services, FX Weaver, and all interactive GUI items
    private final TestService testService;
    private final ShortAnswerQService shortAnswerQService;
    private final EssayQuestionService essayQuestionService;
    private final MultiChoiceQService multiChoiceQService;
    private final MatchingQService matchingQService;
    private final TrueFalseQService trueFalseQService;
    private final FillinBlankQService fillinBlankQService;

    private final TestMakerController testMakerController;
    private final MainController mainController;
    
    public static String path = "src\\main\\resources\\";
    public static String pathTo = "";

    //contructor
    public QuestionHTMLHelper(TestService testService, ShortAnswerQService shortAnswerQService, EssayQuestionService essayQuestionService, MultiChoiceQService multiChoiceQService, MatchingQService matchingQService, TrueFalseQService trueFalseQService, FillinBlankQService fillinBlankQService, TestMakerController testMakerController,MainController mainController) {
        this.testService = testService;
        this.shortAnswerQService = shortAnswerQService;
        this.essayQuestionService = essayQuestionService;
        this.multiChoiceQService = multiChoiceQService;
        this.matchingQService = matchingQService;
        this.trueFalseQService = trueFalseQService;
        this.fillinBlankQService = fillinBlankQService;
        this.testMakerController = testMakerController;
        this.mainController = mainController;
    }

    //creates new key and test file from templates
    public static File createNewFile(String testName) throws IOException {

        String cSection = "";
        String cClass = "";
        int count = 0;
        //need to check current section and class
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(
                    "temp.txt"));
            String line = reader.readLine();
            while (line != null) {

                System.out.println(line);
                // read next line
                if (count == 0) {
                    cClass = line;
                }
                if (count == 1) {
                    cSection = line;
                }
                line = reader.readLine();
                count++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

   
        System.out.println(cClass + " " + cSection);

        pathTo = path + cClass + "\\" + cSection + "\\";

        //load which template to copy and gets info from the template button on main screen
        String templateFileName="template.html";
        
        if(MainController.template1==true)
        {
           templateFileName= "template1.html";
        }
         if(MainController.template2==true)
        {
           templateFileName= "template2.html";
            
        }
          if(MainController.template3==true)
        {
          templateFileName= "template3.html";
        }
        
        //creates test html
        File templateFile = new File(path + templateFileName);
        File newFile = new File(pathTo + testName);
        if (!newFile.exists()) {
            Files.copy(templateFile.toPath(), newFile.toPath()); // copy template file

        }

        // creates Test Key
        File keyTemplateFile = new File(path + "KEY_template.html");
        File testKeyFile = new File(pathTo + "KEY_" + testName);
        if (!testKeyFile.exists()) {
            Files.copy(keyTemplateFile.toPath(), testKeyFile.toPath()); // copy template file
        }


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
        return newFile;
    }

    //controls the replacement of a sought after html "keyword" in newly made test and key 
    public void getReplacement(String testFile, File newFile, String addHTML, String htmlString, String startSection, String endMCSect, int sectLength, String answerHTML, String keyHtmlString) throws FileNotFoundException {
        int startIndex = htmlString.indexOf(startSection);
        int endIndex = htmlString.indexOf(endMCSect, startIndex);
        String replaceHTML = htmlString.substring(startIndex + sectLength, endIndex);  // inbetween section tags. need to be copied and appended to

        //replaces html with whatever section was added
        String testHtmlString = htmlString.replace(replaceHTML, addHTML);
        PrintWriter testPrintWriter = new PrintWriter(newFile);
        testPrintWriter.println(testHtmlString);
        testPrintWriter.close();

        int keyStartIndex = keyHtmlString.indexOf(startSection);
        int keyEndIndex = keyHtmlString.indexOf(endMCSect, keyStartIndex);
        String keyReplaceHTML = keyHtmlString.substring(keyStartIndex + sectLength, keyEndIndex);  // inbetween section tags. need to be copied and appended to

        //addes data to the key and replaces variables in key html
        String thisKeyHtmlString = keyHtmlString.replace(keyReplaceHTML, answerHTML);
        PrintWriter keyPrintWriter = new PrintWriter(pathTo + "KEY_" + testFile);
        keyPrintWriter.println(thisKeyHtmlString);
        keyPrintWriter.close();

        testMakerController.refresh();
    }

    //randomizes each sections contents
    public void Randomize(String[] arr) {
        //randomize array
        Random rand = new Random();

        for (int i = 0; i < arr.length; i++) {
            int randomIndexToSwap = rand.nextInt(arr.length);
            String temp = arr[randomIndexToSwap];
            arr[randomIndexToSwap] = arr[i];
            arr[i] = temp;
        }
        System.out.println(Arrays.toString(arr));

    }

    /**
     * I know this is huge and should be made reusable. Very nasty code :(
     *
     * @param thisTest
     * @param file
     * @param keyFile
     * @throws IOException
     */
    
    //updates short answer section of html essentially all file editing operations
    public void updateShortAnswerHTML(Test thisTest, String file, String keyFile) throws IOException {
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

        if (TestMakerController.randSAQ == true) {
            Randomize(shortAnswers);
        }

        String answerHTML = "";
        String gradingInstructions = "";
        for (String id : shortAnswers) {
            shortAnsCount++;
            ShortAnswerQuestion shortAnswerQuestion = shortAnswerQService.findQuestionByID(id);
            String questionContent = shortAnswerQuestion.getQuestionContent();
            String correctAnswer = shortAnswerQuestion.getCorrectAnswer();
            //html code to inject
            addHTML = addHTML + ("<p><strong>" + shortAnsCount + ". "
                    + questionContent + "</strong></p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n");
            if (shortAnswerQuestion.getGradingInstruction() != "") {
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

    //updates essay section of html essentially all file editing operations
    public void updateEssayHTML(Test thisTest, String file, String keyFile) throws IOException {
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

        if (TestMakerController.randEQ == true) {
            Randomize(essayQuestions);
        }

        String answerHTML = "";
        String gradingInstructions = "";
        for (String id : essayQuestions) {
            essayCount++;
            EssayQuestion essayQuestion = essayQuestionService.findQuestionByID(id);
            String questionContent = essayQuestion.getQuestionContent();
            String correctAnswer = essayQuestion.getCorrectAnswer();
            //html code to inject
            addHTML = addHTML + ("<p><strong>" + essayCount + ". "
                    + questionContent + "</strong></p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n");
            if (essayQuestion.getGradingInstruction() != "") {
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

    //updates fib section of html essentially all file editing operations
    public void updateFIBHTML(Test thisTest, String file, String keyFile) throws IOException {
        File templateFile = new File(file);
        File newFile = new File(file);
        Files.copy(templateFile.toPath(), newFile.toPath()); // copy current version of file

        File keyTemplateFile = new File(keyFile);
        File newKeyFile = new File(keyFile);
        Files.copy(keyTemplateFile.toPath(), newKeyFile.toPath()); // copy current version of file
        String keyHtmlString = Files.readString(newKeyFile.toPath());

        int fibCount = 0;
        String addHTML = "<h1>Fill in Blank </h1>"
                + "<div id = \"fibPoints\"> <div id=\"lazyinsert8\"></div> </div>\n";
        String fibQIDs = thisTest.getFillBlankQ();
        String[] fibArrStr = fibQIDs.split(",");
        String[] fibQuestions = Arrays.copyOfRange(fibArrStr, 1, fibArrStr.length);

        if (TestMakerController.randFIBQ == true) {
            Randomize(fibQuestions);
        }
        
        String answerHTML = "";
        String gradingInstructions = "";
        for (String id : fibQuestions) {
            fibCount++;
            FillinBlankQuestion fillinBlankQuestion = fillinBlankQService.findQuestionByID(id);
            String questionContent = fillinBlankQuestion.getQuestionContent();
            questionContent= questionContent.replace("/?/", "______________________");
            String correctAnswer = fillinBlankQuestion.getCorrectAnswer();
            //html code to inject
            addHTML = addHTML + ("<p><strong>" + fibCount + ". "
                    + questionContent + "</strong></p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n"
                    + "<p>&nbsp;</p>" + "\n");
            if (fillinBlankQuestion.getGradingInstruction() != "") {
                gradingInstructions = "<p style=\"color:blue;\"> Grading Instructions: " + fillinBlankQuestion.getGradingInstruction() + "</p>\n";
            }
            answerHTML = gradingInstructions + addHTML + "<p style=\"color:red;\"> Correct Answer: " + correctAnswer + "</p>\n";
        }

        String htmlString = Files.readString(newFile.toPath());
        String fibSect = "<section id=\"FillInBlank\">";
        String endEssaySect = "</section>";
        int sectLength = fibSect.length();
        getReplacement(thisTest.getTestName(), newFile, addHTML, htmlString, fibSect, endEssaySect, sectLength, answerHTML, keyHtmlString); 
    }

    
    //updates true/false section of html essentially all file editing operations
    public void updateTrueFalseHTML(Test thisTest, String file, String keyFile) throws IOException {
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

        if (TestMakerController.randTF == true) {
            Randomize(trueFalseQuestions);
        }

        String answerHTML = "";
        String gradingInstructions = "";
        for (String id : trueFalseQuestions) {
            tfCount++;
            TrueFalseQuestion trueFalseQuestion = trueFalseQService.findQuestionByID(id);
            String questionContent = trueFalseQuestion.getQuestionContent();
            String correctAnswer = trueFalseQuestion.getCorrectAnswer();
            //html code to inject
            addHTML = addHTML + ("<p><strong>" + tfCount + ". "
                    + questionContent + "</strong></p>" + "\n"
                    + "<p> T: ____ </p>" + "\n"
                    + "<p> F: ____ </p>" + "\n");
            if (trueFalseQuestion.getGradingInstruction() != "") {
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

    //updates matching section of html essentially all file editing operations
    public void updateMatchingHTML(Test thisTest, String file, String keyFile) throws IOException {
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

        if (TestMakerController.randMQ == true) {
            Randomize(matchingQuestions);
        }

        ArrayList<String> termDefinitions = new ArrayList<>();
        ArrayList<String> terms = new ArrayList<>();
        for (String id : matchingQuestions) {
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
        for (Map.Entry<String, String> keyVal : shuffledTermAndDef.entrySet()) {
            matchCount++;
            //code to inject
            addHTML = addHTML + (
                    "<div class=\"row\">\n" +
                            "  <div class=\"column\">\n" +
                            "    <p>" + matchCount + ".   " + keyVal.getKey() + ":_____" + "</p>\n" +
                            "  </div>\n" +
                            "  <div class=\"column\">\n" +
                            "    <p>" + "&emsp;" + keyVal.getValue() + "</p>\n" +
                            "  </div>\n" +
                            "  </div>\n"
            );
        }

        String answerHTML = addHTML;

        for (Map.Entry<String, String> keyVal : termAndDef.entrySet()) {
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

    //updates multichoice section of html essentially all file editing operations
    public void updateMultiChoiceHTML(Test thisTest, String file, String keyFile) throws IOException {
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

        if (TestMakerController.randMCQ == true) {
            Randomize(multiChoiceQuestions);
        }

        String answerHTML = "";
        String gradingInstructions = "";
        for (String id : multiChoiceQuestions) {
            ArrayList<String> possibleAnswers = new ArrayList<>();
            mcCount++;
            MultiChoiceQuestion multiChoiceQuestion = multiChoiceQService.findQuestionByID(id);
            String questionContent = multiChoiceQuestion.getQuestionContent();
            String correctAnswer = multiChoiceQuestion.getCorrectAnswer();
            String falseAnswersList = multiChoiceQuestion.getFalseAnswer().replace("[", "").replace("]", "");
            String[] falseAnswers = falseAnswersList.split(",");
            for (String answ : falseAnswers) {
                possibleAnswers.add(answ);
            }
            Collections.shuffle(possibleAnswers);
            //html to inject
            addHTML = addHTML + ("<p><strong>" + mcCount + ".  "
                    + questionContent + "</strong></p>" + "\n"
                    + "<p> a: " + possibleAnswers.get(0) + "</p>" + "\n"
                    + "<p> b: " + possibleAnswers.get(1) + "</p>" + "\n"
                    + "<p> c: " + possibleAnswers.get(2) + "</p>" + "\n"
                    + "<p> d: " + possibleAnswers.get(3) + "</p>" + "\n");
            if (multiChoiceQuestion.getGradingInstruction() != "") {
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

    //updates reference section of html essentially all file editing operations
    public void updateReferenceHTML(Test thisTest, String file, String keyFile) throws IOException {
        File templateFile = new File(file);
        File newFile = new File(file);
        Files.copy(templateFile.toPath(), newFile.toPath()); // copy current version of file

        File keyTemplateFile = new File(keyFile);
        File newKeyFile = new File(keyFile);
        Files.copy(keyTemplateFile.toPath(), newKeyFile.toPath()); // copy current version of file
        String keyHtmlString = Files.readString(newKeyFile.toPath());

        Test currentTest = testService.returnThisTest();
        String testName = currentTest.getTestName();

        File path = new File(pathTo + "\\reference\\" + testName);

        String addHTML = "";
        File[] files = path.listFiles();

        for (int i = 0; i < files.length; i++) {
            String extension = FilenameUtils.getExtension(files[i].getName());
            System.out.println(extension);
            if (extension.equals("pdf")) {
                generateImageFromPDF(path + "\\" + files[i].getName(), "png");
                File delFile = new File(path + "\\" + files[i].getName());
                delFile.delete();
            }
        }


        files = path.listFiles();
        if (path.listFiles() == null) {
            System.out.println("no phototsS");
        } else {
            //if(files[i].getName().getFileExtension())
            addHTML = "<h1>Reference </h1>\n";
            for (int i = 0; i < files.length; i++) {

                System.out.println("FILES PHOTO:" + files[i].getName());


                //addHTML = "<h1>Reference </h1>\n";
                //+ "<div id = \"essayPoints\"> <div id=\"lazyinsert2\"></div> </div>\n";

                addHTML = addHTML + ("<img src=\"reference\\" + testName + "\\" + files[i].getName() + "\" width=\"100%\" height=\"100%\">");

            }
            String htmlString = Files.readString(newFile.toPath());
            String RefSect = "<section id=\"Reference\">";
            String endRefSect = "</section>";
            int sectLength = RefSect.length();
            String answerHTML = "";

            getReplacement(thisTest.getTestName(), newFile, addHTML, htmlString, RefSect, endRefSect, sectLength, answerHTML, keyHtmlString);
        }
    }


    public static <K, V> Map<K, V> zipToMap(ArrayList<K> keys, ArrayList<V> values) {
        return IntStream.range(0, keys.size()).boxed()
                .collect(Collectors.toMap(keys::get, values::get));
    }

    /**
     * Once a question is added or removed, we will re-read the questions from the repo and put them in the html
     */
    public void updateSections(String file, String keyFile) throws IOException {
        Test currentTest = testService.returnThisTest();
        String testName = currentTest.getTestName();
        File checkRef = new File(pathTo + "//reference//" + testName);


        Test thisTest = testService.returnThisTest();
        if (thisTest.getEssayQ() != null) {
            updateEssayHTML(thisTest, file, keyFile);
        }
        if (thisTest.getShortAnswerQ() != null) {
            updateShortAnswerHTML(thisTest, file, keyFile);
        }
        if (thisTest.getTrueFalseQ() != null) {
            updateTrueFalseHTML(thisTest, file, keyFile);
        }
        if (thisTest.getMatchingQ() != null) {
            updateMatchingHTML(thisTest, file, keyFile);
        }
        if (thisTest.getMultiChoiceQ() != null) {
            updateMultiChoiceHTML(thisTest, file, keyFile);
        }
        if (thisTest.getFillBlankQ() != null) {
            updateFIBHTML(thisTest, file, keyFile);
        }
        
        if (checkRef.listFiles() != null) {
            updateReferenceHTML(thisTest, file, keyFile);
        }
      
    }

    //generates image from pdf since webviewer doesnt like pdf format
    private void generateImageFromPDF(String filename, String extension) throws IOException {
    Test currentTest = testService.returnThisTest();
        String testName = currentTest.getTestName();
        PDDocument document = PDDocument.load(new File(filename));
    PDFRenderer pdfRenderer = new PDFRenderer(document);
    for (int page = 0; page < document.getNumberOfPages(); ++page) {
        BufferedImage bim = pdfRenderer.renderImageWithDPI(
          page, 300, ImageType.RGB);
        ImageIOUtil.writeImage(
          bim, String.format(pathTo+"//reference//"+testName+"//"+"pdf-%d.%s", page + 1, extension), 300);
    }
    document.close();
}
}
