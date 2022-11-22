package com.example.springTestProj.Repository;

import com.example.springTestProj.Entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestsRepository extends JpaRepository<Test, String> {
    boolean existsByTestUUID(String testID);
//
//    @Query("SELECT COUNT(u) FROM User u")
//    Long getTotalUsers();


    Test findByTestUUID(String testID);

//    User findUsersByUsernameAndPassword(String username, String password);
//
//    User findUsersByUsername(String username);
}
