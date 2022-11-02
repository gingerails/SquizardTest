/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springTestProj.Entities;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "Section")
public class Section {
    @Id
   // @GeneratedValue(strategy=GenerationType.IDENTITY)  idek
    @Column(name = "SectionUUID")
    private String sectionUUID;
    @Column(name = "CourseUUID")
    private String courseUUID;
    @Column(name = "SectionNum")
    private String sectionNum;
    @Column(name = "Tests")
    private String test;

    public Section() {

    }

    @Override
    public String toString() {
        return "Section [SectionUUID=" + sectionUUID + "]";
    }

}