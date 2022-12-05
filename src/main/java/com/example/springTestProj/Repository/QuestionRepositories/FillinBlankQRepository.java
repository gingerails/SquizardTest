package com.example.springTestProj.Repository.QuestionRepositories;

import com.example.springTestProj.Entities.QuestionEntities.FillinBlankQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FillinBlankQRepository extends JpaRepository<FillinBlankQuestion, String> {
    FillinBlankQuestion findByQuestionID(String id);
}
