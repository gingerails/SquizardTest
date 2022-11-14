package com.example.springTestProj.Service;

import com.example.springTestProj.Entities.FIBQuestion;
import com.example.springTestProj.Entities.MCQuestion;
import com.example.springTestProj.Entities.TFQuestion;
import com.example.springTestProj.Entities.Test;
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

    public void createTest(String testName, String section){
//        String testUUID = String.valueOf(UUID.randomUUID());
        Test newTest = new Test(testName, section);
       // Test newTest = new Test(testUUID);
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

    public void addQuestionToSection(Test test, Callable function){
        String testUUID = test.getTestUUID();
        Test copyOfTest = test;
        //function.

    }

    public void addTFQuestion(TFQuestion tfQuestion){

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
