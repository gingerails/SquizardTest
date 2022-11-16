package com.example.springTestProj.Repository.QuestionRepositories;

import com.example.springTestProj.Entities.QuestionEntities.EssayQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EssayQRepository extends JpaRepository<EssayQuestion, String> {

    EssayQuestion findByQuestionID(String questionID);

}
