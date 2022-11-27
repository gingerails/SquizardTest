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
    @Column(name = "test_uuid")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String testUUID;
    @Column(name = "class_average")
    private Double class_average;
    @Column(name = "class")
    private String className;
    @Column(name = "test_length")
    private Double testLength;
    @Column(name = "creator_id")
    private String creatorId;


    public Feedback() {

    }

//    public Feedback(String feedBack) {
//        this.testUUID = String.valueOf(UUID.randomUUID());  // probably dont do this maybe
//    }

    @Override
    public String toString() {
        return "Feedback [test_uuid=" + testUUID + ", class_average =" +  + class_average + ", class =" + className + ", test_length =" + testLength + "]";
    }

}