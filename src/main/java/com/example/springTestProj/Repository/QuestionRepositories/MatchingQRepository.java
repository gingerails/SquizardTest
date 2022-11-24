package com.example.springTestProj.Repository.QuestionRepositories;

import com.example.springTestProj.Entities.QuestionEntities.FillinBlankQuestion;
import com.example.springTestProj.Entities.QuestionEntities.MatchingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchingQRepository extends JpaRepository<MatchingQuestion, String> {
}