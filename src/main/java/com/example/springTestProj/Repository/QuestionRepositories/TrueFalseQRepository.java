package com.example.springTestProj.Repository.QuestionRepositories;

import com.example.springTestProj.Entities.QuestionEntities.ShortAnswerQuestion;
import com.example.springTestProj.Entities.QuestionEntities.TrueFalseQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrueFalseQRepository extends JpaRepository<TrueFalseQuestion, Long> {

    TrueFalseQuestion findByQuestionID(String questionID);

}
