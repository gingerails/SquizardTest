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
 //   @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY )
    private Long id;
    @Embedded
    @Column(name = "question_type")
    private QuestionType type;
    @Column(name = "question_content")
    private String questionContent;
    @Column(name = "question_graphic")
    private String questionGraphic;
    @Column(name = "correct_answer")
    private String correctAnswer;
    @Column(name = "false_answer")
    private String falseAnswer;
    @Column(name = "answer_graphic")
    private String answerGraphic;
    @Column(name = "text_ref_section")
    private String textReferenceSection;
    @Column(name = "reference_material")
    private String referenceMaterial;
    @Column(name = "instructor_comment")
    private String instructorComment;
    @Column(name = "grading_instruction")
    private String gradingInstruction;
    @Column(name = "class_average")
    private String classAverage;

    protected Question() {
    }

    public Question(String questionContent, String correctAnswer) {
        this.questionContent = questionContent;
        this.correctAnswer = correctAnswer;
    }

    public Question(String questionContent, String correctAnswer, String falseAnswer) {
        this.questionContent = questionContent;
        this.correctAnswer = correctAnswer;
        this.falseAnswer = falseAnswer;
    }
}
