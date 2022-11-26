package com.example.springTestProj.Repository.QuestionRepositories;

import com.example.springTestProj.Entities.QuestionEntities.MatchingQuestion;
import com.example.springTestProj.Entities.QuestionEntities.MultiChoiceQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultiChoiceQRepository extends JpaRepository<MultiChoiceQuestion, String> {

    MultiChoiceQuestion findByQuestionID(String questionID);

}
