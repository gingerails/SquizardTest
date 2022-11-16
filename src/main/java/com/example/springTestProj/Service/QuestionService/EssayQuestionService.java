package com.example.springTestProj.Service.QuestionService;

import com.example.springTestProj.Entities.EQuestion;
import com.example.springTestProj.Entities.TFQuestion;
import com.example.springTestProj.Repository.EssayQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EssayQuestionService {
    @Autowired
    EssayQuestionRepository essayQuestionRepository;

    public EQuestion createEssayQuestion(String questionContent, String questionAnswer){
        String questionID = String.valueOf(UUID.randomUUID());
        EQuestion newEQuestion = new EQuestion(questionID, questionContent, questionAnswer);

        return newEQuestion;
    }

    public void saveQuestionToRepository(EQuestion eQuestion){
        essayQuestionRepository.save(eQuestion);
        System.out.println("Question saved?");
    }

    public List<EQuestion> readQuestions(){
        return essayQuestionRepository.findAll();
    }

}
