package com.example.springTestProj.Entities.CompositeKeys;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class CoursesPrimaryKey implements Serializable {
    @Column(name = "CoursesUUID")
    private String coursesUUID;
    @Column(name = "course_num")
    private String courseNum;

    public CoursesPrimaryKey(String coursesUUID, String courseNum) {
        this.coursesUUID = coursesUUID;
        this.courseNum = courseNum;
    }

    public CoursesPrimaryKey() {

    }
}
