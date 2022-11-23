/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springTestProj.Entities;

import com.example.springTestProj.Entities.CompositeKeys.CourseID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Course {
    @EmbeddedId
    CourseID courseID;
    @Column(name = "sections")
    private String sections;

    public Course(CourseID courseID) {
        this.courseID = courseID;
    }

    public Course() {

    }
}