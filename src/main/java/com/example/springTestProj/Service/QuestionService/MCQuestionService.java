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

    public void addReference(MCQuestion mcQuestion, String refMaterial){

    }
    public void addRefSection(MCQuestion mcQuestion, String refSection){

    }
    public void addComment(MCQuestion mcQuestion, String comment){

    }
    public void addInstructions(MCQuestion mcQuestion, String instructions){

    }

    public List<MCQuestion> readQuestions(){
        return mcQuestionRepository.findAll();
    }

}
