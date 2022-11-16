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
@Table(name = "MCQuestion")
public class MCQuestion {
    @Id
   // @GeneratedValue(strategy=GenerationType.IDENTITY)  idek
    @Column(name = "question_id")
    private String questionID;
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
    private Double classAverage;
    

    public MCQuestion() {
    }

    public MCQuestion(String questionID) {
        this.questionID = questionID;
    }

    public MCQuestion(String questionID, String questionContent, String correctAnswer, String falseAnswer) {
        this.questionID = questionID;
        this.questionContent = questionContent;
        this.correctAnswer = correctAnswer;
        this.falseAnswer = falseAnswer;

    }

    @Override
    public String toString() {
        return "MCQuestion [question_id=" + questionID + "]";
    }

}
