package com.example.springTestProj.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Embedded
    private QuestionType type;
    private String bodyText;
    private String questionGraphic;
    private String answer;
    private String answerGraphic;
    private String textReferenceSection;
    private String referenceMaterial;
    private String instructorComment;
    private String gradingInstruction;

    protected Question() {
    }


}
