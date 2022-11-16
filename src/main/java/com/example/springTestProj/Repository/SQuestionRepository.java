package com.example.springTestProj.Repository;

import com.example.springTestProj.Entities.SQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SQuestionRepository extends JpaRepository<SQuestion, Long> {
}
