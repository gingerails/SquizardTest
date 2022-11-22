package com.example.springTestProj.Entities.CompositeKeys;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class CourseID implements Serializable {
    private String uuid;
    private String number;

    public CourseID(String uuid, String number) {
        this.uuid = uuid;
        this.number = number;
    }

    public CourseID() {

    }
}
