package com.example.springTestProj.Service;

import com.example.springTestProj.Entities.Question;
import com.example.springTestProj.Repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    public Question createQuestion(String questionContent, String questionAnswer) {
        //Long questionID = UUID.randomUUID();
        Question newShortAnswerQuestion = new Question(questionContent, questionAnswer);

        return newShortAnswerQuestion;
    }
    public Question createQuestionWithFalseAnswer(String questionContent, String correctAnswer, String falseAnswer) {
        //Long questionID = UUID.randomUUID();
        Question newShortAnswerQuestion = new Question(questionContent, correctAnswer, falseAnswer);

        return newShortAnswerQuestion;
    }

    public void saveQuestionToRepository(Question question) {
        questionRepository.save(question);
        System.out.println("Question saved?");
    }

    public List<Question> readQuestions() {
        return questionRepository.findAll();
    }





}
