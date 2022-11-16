package com.example.springTestProj.Repository;

import com.example.springTestProj.Entities.EQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EssayQuestionRepository extends JpaRepository<EQuestion, String> {

    EQuestion findByQuestionID(String questionID);

}
