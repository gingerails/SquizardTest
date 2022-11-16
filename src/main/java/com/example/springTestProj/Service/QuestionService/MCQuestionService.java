package com.example.springTestProj.Service.QuestionService;

import com.example.springTestProj.Entities.MCQuestion;
import com.example.springTestProj.Repository.MCQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MCQuestionService {

    @Autowired
    MCQuestionRepository mcQuestionRepository;

    public MCQuestion createMCQuestion(String questionContent, String questionAnswer, String falseAnswer){
        String questionID = String.valueOf(UUID.randomUUID());
        MCQuestion newMCQuestion = new MCQuestion(questionID, questionContent, questionAnswer, falseAnswer);

        return newMCQuestion;
    }
    public void saveQuestionToRepository(MCQuestion mcQuestion){
        mcQuestionRepository.save(mcQuestion);
        System.out.println("Question saved?");
    }

    public List<MCQuestion> readQuestions(){
        return mcQuestionRepository.findAll();
    }

}
