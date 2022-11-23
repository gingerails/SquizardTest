package com.example.springTestProj.Service;

import com.example.springTestProj.Entities.Question;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Repository.TestsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TestService {

    private final TestsRepository testsRepository;
    //private final TestsQuestionRepo testsQuestionRepo;

    Test currentTest;

    public TestService(TestsRepository testsRepository) {
        this.testsRepository = testsRepository;
    }

    // functions needed:

    // creating html of test

    // creating test key

    public Test createTest(String testName, String sectionUUID) {
        String testUUID = String.valueOf(UUID.randomUUID());
        Test newTest = new Test(testUUID, testName, sectionUUID);
        currentTest = newTest;

        return newTest;
    }

    public void saveTestToRepository(Test test) {
        testsRepository.save(test);
        System.out.println("Test saved?");
    }


    public Test returnTestByTestID(String testID) {
        return testsRepository.findByTestUUID(testID);
    }


    public Test returnThisTest() {
        return currentTest;
    }

    /**
     * update test object by adding tf question
     *
     * @param test
     * @param trueFalseQuestion
     * @return
     */


    /**
     * update test object by adding question
     * @param test
     * @return
     */


//    public Test addQuestion(Test test, Question question) {
//        Long shortAnswerID = question.getId();
//        String shortAnswerSection = test.getShortAnswerQ();
//        shortAnswerSection = shortAnswerSection + "," + shortAnswerID;
//        test.setShortAnswerQ(shortAnswerSection);
//
//        addQuestionToExistingTestAndSave(test);     // call this un every addQuestion method
//
//        return test;
//    }


//    @Transactional
//    public void addQuestionToExistingTestAndSave(Test test) {
//        // // the updatedCourse already has all the new attributes added to it via addCourseController
//        String testUUID = test.getTestUUID();
//        testsRepository.deleteById(testUUID); // delete existing vers of this course, preserving the uuid
//        saveTestToRepository(test);
//        currentTest = test; // update current test
//    }





}
