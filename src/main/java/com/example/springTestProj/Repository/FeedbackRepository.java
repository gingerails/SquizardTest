/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.springTestProj.Repository;

import com.example.springTestProj.Entities.Feedback;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, String> {
    // boolean existsByUserID(String userID);

//    @Query("SELECT COUNT(u) FROM User u")
//    Long getTotalUsers();


    Feedback findByClassName(String className);

    // User findUsersByUsernameAndPassword(String username, String password);

    //  User findUsersByUsername(String username);
    // User findUsersBy
}