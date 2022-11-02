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
@Table(name = "FIBQuestion")
public class FIBQuestion {
    @Id
   // @GeneratedValue(strategy=GenerationType.IDENTITY)  idek
    @Column(name = "QuestionID")
    private String questionID;
    @Column(name = "QContent")
    private String qContent;
    @Column(name = "QGraphic")
    private String qGraphic;
    @Column(name = "CorrectAnswer")
    private String correctAnswer;
    @Column(name = "AnswerGraphic")
    private String answerGraphic;
    @Column(name = "TextReferenceSection")
    private String textReferenceSection;
    @Column(name = "ReferenceMaterial")
    private String referenceMaterial;
    @Column(name = "InstructorComment")
    private String instructorComment;
    @Column(name = "GradingInstruction")
    private String gradingInstruction;
    @Column(name = "ClassAverage")
    private Double classAverage;
    

    public FIBQuestion() {
    }

    @Override
    public String toString() {
        return "FIBQuestion [QuestionID=" + questionID + "]";
    }

}
