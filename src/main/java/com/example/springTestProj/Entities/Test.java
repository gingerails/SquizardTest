package com.example.springTestProj.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "test_vertwo")
public class Test {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) // idek
    @Column(name = "test_uuid")
    private String testUUID;
    @Column(name = "test_name")
    private String testName;
    @Column(name = "section_uuid")
    private String sectionUUID;
    @Column(name = "date_used")
    private String dateUsed;
    @Column(name = "test_html")
    private String testHTML;
    @Column(name = "test_key_html")
    private String testKeyHTML;

    public Test(String testUUID, String sectionUUID) {
        this.testUUID = testUUID;
        this.sectionUUID = sectionUUID;
    }


    public Test() {

    }


    public Test(String testUUID, String testName, String sectionUUID) {
        this.testUUID = testUUID;
        this.testName = testName;
        this.sectionUUID = sectionUUID;
    }
}
