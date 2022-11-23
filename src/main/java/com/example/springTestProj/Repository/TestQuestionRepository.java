package com.example.springTestProj.Repository;

import com.example.springTestProj.Entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;

// idk i just assumed this would also be needed but im not sure
public interface TestQuestionRepository extends JpaRepository<Test, Long> {
    Test getTestByTestUUID(String testID);
}
