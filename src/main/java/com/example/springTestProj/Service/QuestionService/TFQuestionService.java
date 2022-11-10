package com.example.springTestProj.Service.QuestionService;

import com.example.springTestProj.Entities.TFQuestion;
import com.example.springTestProj.Repository.TFQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TFQuestionService {

    @Autowired
    TFQuestionRepository tfQuestionRepository;

    public TFQuestion createTFQuestion(String questionContent, String questionAnswer){
        String questionID = String.valueOf(UUID.randomUUID());
        TFQuestion newTFQuestion = new TFQuestion(questionID, questionContent, questionAnswer);

        return newTFQuestion;
    }
    public void saveQuestionToRepository(TFQuestion tfQuestion){
        tfQuestionRepository.save(tfQuestion);
        System.out.println("Question saved?");
    }

    public void addReference(TFQuestion tfQuestion, String refMaterial){

    }
    public void addRefSection(TFQuestion tfQuestion, String refSection){

    }
    public void addComment(TFQuestion tfQuestion, String comment){

    }
    public void addInstructions(TFQuestion tfQuestion, String instructions){

    }

    public List<TFQuestion> readQuestions(){
        return tfQuestionRepository.findAll();
    }

}
