package com.example.springTestProj.Service.QuestionService;

import com.example.springTestProj.Entities.QuestionEntities.MatchingQuestion;
import com.example.springTestProj.Entities.QuestionEntities.ShortAnswerQuestion;
import com.example.springTestProj.Repository.QuestionRepositories.ShortAnswerQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShortAnswerQService {

    @Autowired
    ShortAnswerQRepository shortAnswerQRepository;

    public ShortAnswerQuestion createSQuestion(String questionContent, String questionAnswer){
        String questionID = String.valueOf(UUID.randomUUID());
        ShortAnswerQuestion newShortAnswerQuestion = new ShortAnswerQuestion(questionID, questionContent, questionAnswer);

        return newShortAnswerQuestion;
    }
    public void saveQuestionToRepository(ShortAnswerQuestion shortAnswerQuestion){
        shortAnswerQRepository.save(shortAnswerQuestion);
        System.out.println("Question saved?");
    }
    
    public List<ShortAnswerQuestion> readQuestions(){
        return shortAnswerQRepository.findAll();
    }
      public ShortAnswerQuestion findQuestionByID(String id){
        return shortAnswerQRepository.findByQuestionID(id);
    }

}
