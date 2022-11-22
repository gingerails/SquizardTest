/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springTestProj.Entities;

import com.example.springTestProj.Entities.CompositeKeys.CoursesPrimaryKey;
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
@Table(name = "Course")
public class Courses {
    //    @Id
    // @GeneratedValue(strategy=GenerationType.IDENTITY)  idek
//    @Column(name = "CoursesUUID")
//    private String coursesUUID;
//    @Column(name = "course_num")
//    private String coursesNum;
    @EmbeddedId
    CoursesPrimaryKey coursesPrimaryKey;
    @Column(name = "sections")
    private String sections;

    public Courses() {

    }

    public Courses(CoursesPrimaryKey coursesPrimaryKey) {
        this.coursesPrimaryKey = coursesPrimaryKey;
    }


    public void addSection(String section) {

    }
//    @Override
//    public String toString() {
//        return "Courses [CoursesUUID=" + coursesUUID + "]";
//    }

}