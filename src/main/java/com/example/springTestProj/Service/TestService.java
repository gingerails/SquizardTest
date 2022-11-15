package com.example.springTestProj.Service;

import com.example.springTestProj.Entities.*;
import com.example.springTestProj.Repository.TestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.Callable;

@Service
public class TestService {

    @Autowired
    TestsRepository testsRepository;

    @Autowired
    SectionService sectionService;

    public Test createTest(String testName, String section, String courseID){
        Section testSection = sectionService.returnSectionBySectionAndCourseID(section, courseID);
        String sectionUUID = testSection.getSectionPrimaryKey().getSectionUUID();
        Test newTest = new Test(testName, sectionUUID);

        return newTest;
    }
    // functions needed:

    // creating a test
    // adding questions to test
    // creating html of test

    // creating test key

    // updating existing tests

    // this prolly wont work. need to have a way to dynamically add

    public void updateQuestionSection(){

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

        return test;

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
