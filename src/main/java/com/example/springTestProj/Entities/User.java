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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "UserID", updatable = false, nullable = false)
    private String userID;
    @Column(name = "Username")
    private String username;
    @Column(name = "Password")
    private String password;

    public User() {

    }

    @Override
    public String toString() {
        return "User [UserID=" + userID + ", Username =" + username + "]";
    }

}