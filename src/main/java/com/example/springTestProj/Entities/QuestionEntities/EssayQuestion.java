/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.springTestProj.Entities.QuestionEntities;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "essay_q")
public class EssayQuestion {
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
    @Column(name = "creator_id")
    private String creatorId;
    

    public EssayQuestion() {
    }

    public EssayQuestion(String questionID) {
        this.questionID = questionID;
    }

    public EssayQuestion(String questionID, String questionContent) {
        this.questionID = questionID;
        this.questionContent = questionContent;
    }

    public EssayQuestion(String questionID, String questionContent, String correctAnswer) {
        this.questionID = questionID;
        this.questionContent = questionContent;
        this.correctAnswer = correctAnswer;


    }

    @Override
    public String toString() {
        return "EQuestion [question_id=" + questionID + "]";
    }

}
