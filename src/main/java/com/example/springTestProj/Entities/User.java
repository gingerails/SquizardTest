/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springTestProj.Entities;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "User")
public class User {
    @Id
    @Column(name = "user_id")
    private String userID;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    public User() {

    }

    @Override
    public String toString() {
        return "User [user_id=" + userID + ", username =" + username + "]";
    }

}