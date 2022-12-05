package com.example.springTestProj.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "Test")
public class Test {

    @Id
    // @GeneratedValue(strategy=GenerationType.IDENTITY)  idek
    @Column(name = "test_uuid")
    private String testUUID;
    @Column(name = "date_created")
    private Date dateCreated;
    @Column(name = "test_name")
    private String testName;
    @Column(name = "section_uuid")
    private String sectionUUID;
    @Column(name = "true_false_q_ids")
    private String trueFalseQ;
    @Column(name = "short_answer_q_ids")
    private String shortAnswerQ;
    @Column(name = "multi_choice_q_ids")
    private String multiChoiceQ;
    @Column(name = "matching_q_ids")
    private String matchingQ;
    @Column(name = "essay_q_ids")
    private String essayQ;
    @Column(name = "fill_blank_q_ids")
    private String fillBlankQ;
    @Column(name = "test_html")
    private String testHTML;
    @Column(name = "test_key_html")
    private String testKeyHTML;
    @Column(name = "creator_id")
    private String creatorId;


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
    public Test(String testUUID, Date dateCreated, String testName, String sectionUUID) {
        this.testUUID = testUUID;
        this.dateCreated = dateCreated;
        this.testName = testName;
        this.sectionUUID = sectionUUID;
    }
}
