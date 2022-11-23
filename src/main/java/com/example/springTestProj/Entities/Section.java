/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springTestProj.Entities;

import com.example.springTestProj.Entities.CompositeKeys.SectionID;
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

    // what is a section?

    // Sections are individual classes of a course (Course: CS499 Section: 01, 02, ect).
    // course can have multiple sections, sections can have multiple tests.
    @EmbeddedId
    SectionID sectionID;
    @Column(name = "course_uuid")
    private String courseUUID;

    @Column(name = "tests")
    private String test;

    public Section() {

    }

    public Section(SectionID sectionID, String courseUUID) {
        this.sectionID = sectionID;
        this.courseUUID = courseUUID;
    }

}