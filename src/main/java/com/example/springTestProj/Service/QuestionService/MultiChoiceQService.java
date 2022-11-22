package com.example.springTestProj.Service.QuestionService;

import com.example.springTestProj.Entities.QuestionEntities.MultiChoiceQuestion;
import com.example.springTestProj.Repository.QuestionRepositories.MultiChoiceQRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MultiChoiceQService {

    @Autowired
    MultiChoiceQRepository multiChoiceQRepository;

    public MultiChoiceQuestion createMCQuestion(String questionContent, String questionAnswer, String falseAnswer) {
        String questionID = String.valueOf(UUID.randomUUID());
        MultiChoiceQuestion newMultiChoiceQuestion = new MultiChoiceQuestion(questionID, questionContent, questionAnswer, falseAnswer);

        return newMultiChoiceQuestion;
    }

    public void saveQuestionToRepository(MultiChoiceQuestion multiChoiceQuestion) {
        multiChoiceQRepository.save(multiChoiceQuestion);
        System.out.println("Question saved?");
    }

    public List<MultiChoiceQuestion> readQuestions() {
        return multiChoiceQRepository.findAll();
    }

}
