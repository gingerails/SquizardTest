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
@Table(name = "Feedback")
public class Feedback {
    @Id
    @Column(name = "TestUUID")
    private String testUUID;
    @Column(name = "ClassAverage")
    private double classAverage;
    @Column(name = "Class")
    private String className;
    @Column(name = "TestLength")
    private double testLength;


    public Feedback() {

    }

//    @Override
//    public String toString() {
//        return "User [UserID=" + userID + ", Username =" + username + "]";
//    }

}