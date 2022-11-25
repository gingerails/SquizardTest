package com.example.springTestProj.Service.QuestionService;

import com.example.springTestProj.Entities.QuestionEntities.EssayQuestion;
import com.example.springTestProj.Repository.QuestionRepositories.EssayQRepository;
import com.example.springTestProj.Repository.QuestionRepositories.ShortAnswerQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EssayQuestionService {
    @Autowired
    EssayQRepository essayQRepository;

    public EssayQuestion createEssayQuestion(String questionContent, String questionAnswer){
        String questionID = String.valueOf(UUID.randomUUID());
        EssayQuestion newEssayQuestion = new EssayQuestion(questionID, questionContent, questionAnswer);

        return newEssayQuestion;
    }

    public void saveQuestionToRepository(EssayQuestion essayQuestion){
        essayQRepository.save(essayQuestion);
        System.out.println("Question saved?");
    }

    public List<EssayQuestion> readQuestions(){
        return essayQRepository.findAll();
    }
    public EssayQuestion findQuestionByID(String id){
        return essayQRepository.findByQuestionID(id);
    }

}
