package com.example.springTestProj.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "Feedback")
public class Feedback {
    @Id
    @Column(name = "TestUUID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String testUUID;
    @Column(name = "class_average")
    private Double class_average;
    @Column(name = "Class")
    private String className;
    @Column(name = "test_length")
    private Double testLength;


    public Feedback() {

    }

//    public Feedback(String feedBack) {
//        this.testUUID = String.valueOf(UUID.randomUUID());  // probably dont do this maybe
//    }

    @Override
    public String toString() {
        return "Feedback [TestUUID=" + testUUID + ", class_average =" +  + class_average + ", Class =" + className + ", TestLength =" + testLength + "]";
    }

}