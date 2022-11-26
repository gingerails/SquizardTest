package com.example.springTestProj.Repository.QuestionRepositories;

import com.example.springTestProj.Entities.QuestionEntities.ShortAnswerQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortAnswerQRepository extends JpaRepository<ShortAnswerQuestion, Long> {

    ShortAnswerQuestion findByQuestionID(String questionID);

}
