package com.example.springTestProj.Service.QuestionService;

import com.example.springTestProj.Entities.QuestionEntities.EssayQuestion;
import com.example.springTestProj.Entities.QuestionEntities.MatchingQuestion;
import com.example.springTestProj.Repository.QuestionRepositories.EssayQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springTestProj.Repository.QuestionRepositories.MatchingQRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MatchingQService {
    @Autowired
    MatchingQRepository matchingQRepository;

    public MatchingQuestion createMatchingQuestion(String questionTerm, String questionAnswer){
        String questionID = String.valueOf(UUID.randomUUID());
        MatchingQuestion newMatchingQuestion = new MatchingQuestion(questionID, questionTerm, questionAnswer);

        return newMatchingQuestion;
    }

    public void saveQuestionToRepository(MatchingQuestion matchingQuestion){
        matchingQRepository.save(matchingQuestion);
        System.out.println("Question saved?");
    }

    public List<MatchingQuestion> readQuestions(){
        return matchingQRepository.findAll();
    }
     public MatchingQuestion findQuestionByID(String id){
        return matchingQRepository.findByQuestionID(id);
    }

}
