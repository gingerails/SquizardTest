package com.example.springTestProj.Entities.CompositeKeys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class CoursesPrimaryKey implements Serializable {
    @Column(name = "CoursesUUID")
    private String coursesUUID;
    @Column(name = "CourseNum")
    private String courseNum;
}
