/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springTestProj.Entities;
import com.example.springTestProj.Entities.CompositeKeys.CoursesPrimaryKey;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "Courses")
public class Courses {
//    @Id
//   // @GeneratedValue(strategy=GenerationType.IDENTITY)  idek
//    @Column(name = "CoursesUUID")
//    private String coursesUUID;
//    @Column(name = "CourseNum")
//    private String coursesNum;
    @EmbeddedId
    CoursesPrimaryKey coursesPrimaryKey;
    @Column(name = "Sections")
    private String sections;

    public Courses() {

    }


    public Courses(CoursesPrimaryKey coursesPrimaryKey, String sections) {
    }

    public Courses(CoursesPrimaryKey coursesPrimaryKey) {
    }

//    @Override
//    public String toString() {
//        return "Courses [CoursesUUID=" + coursesUUID + "]";
//    }

}