/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springTestProj.Entities;
import com.example.springTestProj.Entities.CompositeKeys.SectionPrimaryKey;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "Section")
public class Section {
    @EmbeddedId
    SectionPrimaryKey sectionPrimaryKey;

    @Column(name = "CourseUUID")
    private String courseUUID;

    @Column(name = "Tests")
    private String test;

    public Section() {

    }

    public Section(String sectionID, String courseUUID, String sectionNum) {
    }


//    @Override
//    public String toString() {
//        return "Section [SectionUUID=" + sectionUUID + "]";
//    }

}