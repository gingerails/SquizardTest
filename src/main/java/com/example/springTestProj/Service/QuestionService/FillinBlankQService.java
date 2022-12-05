package com.example.springTestProj.Service.QuestionService;

import com.example.springTestProj.Entities.QuestionEntities.FillinBlankQuestion;
import com.example.springTestProj.Entities.QuestionEntities.ShortAnswerQuestion;
import com.example.springTestProj.Repository.QuestionRepositories.FillinBlankQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FillinBlankQService {

    @Autowired
    FillinBlankQRepository fillinBlankQRepository;

    public FillinBlankQuestion createFIBQuestion(String questionContent, String questionAnswer){
        String questionID = String.valueOf(UUID.randomUUID());
        FillinBlankQuestion newFIBQuestion = new FillinBlankQuestion(questionID, questionContent, questionAnswer);

        return newFIBQuestion;
    }

    public void saveQuestionToRepository(FillinBlankQuestion fillinBlankQuestion){
        fillinBlankQRepository.save(fillinBlankQuestion);
        System.out.println("Question saved?");
    }

    public List<FillinBlankQuestion> readQuestions(){
        return fillinBlankQRepository.findAll();
    }

    public FillinBlankQuestion findQuestionByID(String id){
        return fillinBlankQRepository.findByQuestionID(id);
    }

}
