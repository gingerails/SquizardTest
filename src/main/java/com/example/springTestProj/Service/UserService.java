/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springTestProj.Service;

import com.example.springTestProj.Entities.User;
import com.example.springTestProj.Repository.UserRepository;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ginge
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    User currentUser;

    public void saveUserToRepository(User user){
        userRepository.save(user);
        System.out.println("User saved?");

    }

    public User createUser(String username, String password){
        String userID = String.valueOf(UUID.randomUUID());  // probably dont do this maybe
        User newUser = new User(userID, username, password);

        return newUser;
    }

    public User returnCurrentUser(){
        return currentUser;
    }
    public String returnCurrentUserID(){
        return currentUser.getUserID();
    }
    public void setCurrentUser(User thisUser){
        currentUser = thisUser;
    }

    public User returnUserById(String id){
        return userRepository.findByUserID(id);
    }

    public List<User> readUsers(){
        return userRepository.findAll();
    }


    public User returnUser(String username, String password){
        return userRepository.findUsersByUsernameAndPassword(username, password);
    }


    public User returnUserByUsername(String username){
        return userRepository.findUsersByUsername(username);
    }


    @Transactional
    public String deleteUser(User user){
        if (userRepository.existsByUserID(user.getUserID())){
            try {
               User userDelete = userRepository.findByUserID(user.getUserID());
               userRepository.delete(userDelete);
                return "User record deleted successfully.";
            }catch (Exception e){
                throw e;
            }

        }else {
            return "User does not exist";
        }
    }

}
