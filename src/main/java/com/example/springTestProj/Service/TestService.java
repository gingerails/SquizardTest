package com.example.springTestProj.Service;

import com.example.springTestProj.Entities.*;
import com.example.springTestProj.Repository.TestsRepository;
import org.aspectj.weaver.ast.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.Callable;

@Service
public class TestService {

    @Autowired
    TestsRepository testsRepository;

    Test currentTest;

    // functions needed:

    // creating html of test

    // creating test key

    public Test createTest(String testName, String sectionUUID){
        String testUUID = String.valueOf(UUID.randomUUID());
        Test newTest = new Test(testUUID, testName, sectionUUID);
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


    public Test returnThisTest(){
        return currentTest;
    }

    /**
     * update test object by adding tf question
     * @param test
     * @param tfQuestion
     * @return
     */
    public Test addTFQuestion(Test test, TFQuestion tfQuestion){
        String tfQuestionID = tfQuestion.getQuestionID();
        String tfSection = test.getTrueFalseQ();
        tfSection = tfSection + "," + tfQuestionID;
        test.setTrueFalseQ(tfSection);

        addQuestionToExistingTestAndSave(test);     // call this un every addQuestion method

        return test;
    }

    /**
     * Updates test by adding Essay Question
     * @param test
     * @param eQuestion
     * @return
     */
    public Test addEQuestion(Test test, EQuestion eQuestion){
        String eQuestionID = eQuestion.getQuestionID();
        String essaySection = test.getEssayQ();
        essaySection = essaySection + "," + eQuestionID;
        test.setEssayQ(essaySection);

        addQuestionToExistingTestAndSave(test);     // call this un every addQuestion method

        return test;
    }

    @Transactional
    public void addQuestionToExistingTestAndSave(Test test){
       // // the updatedCourse already has all the new attributes added to it via addCourseController
        String testUUID = test.getTestUUID();
        testsRepository.deleteById(testUUID); // delete existing vers of this course, preserving the uuid
        saveTestToRepository(test);
        currentTest = test; // update current test
    }
    public void addFIBQuestion(FIBQuestion fibQuestion){

    }
    public void addMCQuestion(MCQuestion mcQuestion){

    }
//    public void addTFQuestion(TFQuestion tfQuestion){
//
//    }
//    public void addTFQuestion(TFQuestion tfQuestion){
//
//    }



}
