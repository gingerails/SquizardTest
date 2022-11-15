package com.example.springTestProj.Service.QuestionService;

import com.example.springTestProj.Entities.SQuestion;
import com.example.springTestProj.Repository.SQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SQuestionService {

    @Autowired
    SQuestionRepository sQuestionRepository;

    public SQuestion createSQuestion(String questionContent, String questionAnswer){
        String questionID = String.valueOf(UUID.randomUUID());
        SQuestion newSQuestion = new SQuestion(questionID, questionContent, questionAnswer);

        return newSQuestion;
    }
    public void saveQuestionToRepository(SQuestion sQuestion){
        sQuestionRepository.save(sQuestion);
        System.out.println("Question saved?");
    }

    public void addReference(SQuestion mcQuestion, String refMaterial){

    }
    public void addRefSection(SQuestion mcQuestion, String refSection){

    }
    public void addComment(SQuestion mcQuestion, String comment){

    }
    public void addInstructions(SQuestion mcQuestion, String instructions){

    }

    public List<SQuestion> readQuestions(){
        return sQuestionRepository.findAll();
    }

}
