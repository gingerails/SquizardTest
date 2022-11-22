/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springTestProj.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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