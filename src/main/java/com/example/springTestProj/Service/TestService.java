package com.example.springTestProj.Service;

import com.example.springTestProj.Entities.*;
import com.example.springTestProj.Entities.QuestionEntities.*;
import com.example.springTestProj.Repository.TestsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TestService {

//    @Autowired
//    TestsRepository testsRepository;
    private final TestsRepository testsRepository;
    private final UserService userService;

    Test currentTest;

    public TestService(TestsRepository testsRepository, UserService userService) {
        this.testsRepository = testsRepository;
        this.userService = userService;
    }

    // functions needed:

    // creating html of test

    // creating test key

    public Test createTest(String testName, String sectionUUID){
        Date dateCreated = new Date();
        String testUUID = String.valueOf(UUID.randomUUID());
        Test newTest = new Test(testUUID, dateCreated, testName, sectionUUID);
        newTest.setCreatorId(userService.returnCurrentUserID());
        currentTest = newTest;

        return newTest;
    }

    public void saveTestToRepository(Test test){
        testsRepository.save(test);
        System.out.println("Test saved?");
    }


    public Test returnTestByTestID(String testID){
        return testsRepository.findByTestUUID(testID);
    }

    public List<Test> findAllTests() {
        return testsRepository.findAll();
    }
    public List<Test> findAllTestsByUser(String creatorID) {
        return testsRepository.findAllByCreatorId(creatorID);
    }
    public Test returnThisTest(){
        return currentTest;
    }

    public void setCurrentTest(Test test){
        currentTest = test;
    }

    /**
     * update test object by adding tf question
     * @param test
     * @param trueFalseQuestion
     * @return
     */
    public Test addTFQuestion(Test test, TrueFalseQuestion trueFalseQuestion){
        String tfQuestionID = trueFalseQuestion.getQuestionID();
        String tfSection = test.getTrueFalseQ();
        tfSection = tfSection + "," + tfQuestionID;
        test.setTrueFalseQ(tfSection);

        addQuestionToExistingTestAndSave(test);     // call this un every addQuestion method

        return test;
    }

    /**
     * Updates test by adding Essay Question
     * @param test
     * @param essayQuestion
     * @return
     */
    public Test addEQuestion(Test test, EssayQuestion essayQuestion){
        String eQuestionID = essayQuestion.getQuestionID();
        String essaySection = test.getEssayQ();
        essaySection = essaySection + "," + eQuestionID;
        test.setEssayQ(essaySection);

        addQuestionToExistingTestAndSave(test);     // call this un every addQuestion method

        return test;
    }

    /**
     *
     * @param test
     * @param multiChoiceQuestion
     * @return
     */
    public Test addMCQuestion(Test test, MultiChoiceQuestion multiChoiceQuestion){
        String multiChoiceQID = multiChoiceQuestion.getQuestionID();
        String multiChoiceSection = test.getMultiChoiceQ();
        multiChoiceSection = multiChoiceSection + "," + multiChoiceQID;
        test.setMultiChoiceQ(multiChoiceSection);

        addQuestionToExistingTestAndSave(test);     // call this un every addQuestion method

        return test;
    }
    public Test addShortAnswerQ(Test test, ShortAnswerQuestion shortAnswerQuestion){
        String shortAnswerID = shortAnswerQuestion.getQuestionID();
        String shortAnswerSection = test.getShortAnswerQ();
        shortAnswerSection = shortAnswerSection + "," + shortAnswerID;
        test.setShortAnswerQ(shortAnswerSection);

        addQuestionToExistingTestAndSave(test);     // call this un every addQuestion method

        return test;
    }
    public Test addMatchingQuestion(Test test, MatchingQuestion matchingQuestion) {
        String matchingID = matchingQuestion.getQuestionID();
        String matchingSection = test.getMatchingQ();
        matchingSection = matchingSection + "," + matchingID;
        test.setMatchingQ(matchingSection);

        addQuestionToExistingTestAndSave(test);     // call this un every addQuestion method

        return test;
    }

    public void addFIBQuestion(FillinBlankQuestion fillinBlankQuestion){

    }

    @Transactional
    public void addQuestionToExistingTestAndSave(Test test){
       // // the updatedCourse already has all the new attributes added to it via addCourseController
        String testUUID = test.getTestUUID();
        testsRepository.deleteById(testUUID); // delete existing vers of this course, preserving the uuid
        saveTestToRepository(test);
        currentTest = test; // update current test
    }





//    public void addTFQuestion(TFQuestion tfQuestion){
//
//    }
//    public void addTFQuestion(TFQuestion tfQuestion){
//
//    }



}
