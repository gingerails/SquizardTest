package com.example.springTestProj.Repository;

import com.example.springTestProj.Entities.TFQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TFQuestionRepository extends JpaRepository<TFQuestion, Long> {
}
