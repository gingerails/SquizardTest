package com.example.springTestProj.Service.QuestionService;

import com.example.springTestProj.Entities.QuestionEntities.TrueFalseQuestion;
import com.example.springTestProj.Repository.QuestionRepositories.TrueFalseQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TrueFalseQService {

    @Autowired
    TrueFalseQRepository trueFalseQRepository;

    public TrueFalseQuestion createTFQuestion(String questionContent, String questionAnswer) {
        String questionID = String.valueOf(UUID.randomUUID());
        TrueFalseQuestion newTrueFalseQuestion = new TrueFalseQuestion(questionID, questionContent, questionAnswer);

        return newTrueFalseQuestion;
    }

    public void saveQuestionToRepository(TrueFalseQuestion trueFalseQuestion) {
        trueFalseQRepository.save(trueFalseQuestion);
        System.out.println("Question saved?");
    }

    public List<TrueFalseQuestion> readQuestions() {
        return trueFalseQRepository.findAll();
    }

}
