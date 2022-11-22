/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springTestProj.Entities;

import com.example.springTestProj.Entities.CompositeKeys.SectionPrimaryKey;
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
@Table(name = "Section")
public class Section {
    @EmbeddedId
    SectionPrimaryKey sectionPrimaryKey;

    @Column(name = "course_uuid")
    private String courseUUID;

    @Column(name = "tests")
    private String test;

    public Section() {

    }
//
//    public Section(String sectionID, String courseUUID, String sectionNum) {
//        this.section
//    }

    public Section(SectionPrimaryKey sectionPrimaryKey, String courseUUID) {
        this.sectionPrimaryKey = sectionPrimaryKey;
        this.courseUUID = courseUUID;
    }


//    @Override
//    public String toString() {
//        return "Section [SectionUUID=" + sectionUUID + "]";
//    }

}