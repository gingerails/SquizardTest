package com.example.springTestProj.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    @Column(name = "section_uuid")
    private String sectionUUID;
    @Column(name = "true_false_q_ids")
    private String trueFalseQ;
    @Column(name = "short_answer_q_ids")
    private String shortAnswerQ;
    @Column(name = "mult_choice_q_ids")
    private String multiChoiceQ;
    @Column(name = "matching_q_id")
    private String matchingQ;
    @Column(name = "essay_q_ids")
    private String essayQ;
    @Column(name = "test_html")
    private String testHTML;
    @Column(name = "test_key_html")
    private String testKeyHTML;

    public Test(String testUUID) {
        this.testUUID = testUUID;
    }

    public Test() {

    }

}
