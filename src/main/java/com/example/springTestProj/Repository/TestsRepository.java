package com.example.springTestProj.Repository;

import com.example.springTestProj.Entities.CompositeKeys.SectionPrimaryKey;
import com.example.springTestProj.Entities.Section;
import com.example.springTestProj.Entities.Test;
import com.example.springTestProj.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public interface TestsRepository extends JpaRepository<Test, String> {
    boolean existsByTestUUID(String testID);
//
//    @Query("SELECT COUNT(u) FROM User u")
//    Long getTotalUsers(


    Test findByTestUUID(String testID);
    List<Test> findAllByCreatorId(String creatorID);

//    User findUsersByUsernameAndPassword(String username, String password);
//
//    User findUsersByUsername(String username);
}
