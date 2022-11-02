package com.example.springTestProj.Repository;

import com.example.springTestProj.Entities.MCQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MCQuestionRepository extends JpaRepository<MCQuestion, Long> {
}
