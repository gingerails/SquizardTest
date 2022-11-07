package com.example.springTestProj.Service;

import com.example.springTestProj.Entities.FIBQuestion;
import com.example.springTestProj.Entities.MCQuestion;
import com.example.springTestProj.Entities.TFQuestion;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Repository.TestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TestService {

    @Autowired
    TestsRepository testsRepository;

    public void createTest(Test test){
        String testUUID = String.valueOf(UUID.randomUUID());
        Test newTest = new Test(testUUID);
    }
    // functions needed:

    // creating a test
    // adding questions to test
    // creating html of test

    // creating test key

    // updating existing tests

    public void addQuestionToTest(Test test){
        String testUUID = test.getTestUUID();
        Test copyOfTest = test;

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
