package com.example.springTestProj.Repository;

import com.example.springTestProj.Entities.FIBQuestion;
import com.example.springTestProj.Entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FIBQuestionRepository extends JpaRepository<FIBQuestion, Long> {
}
