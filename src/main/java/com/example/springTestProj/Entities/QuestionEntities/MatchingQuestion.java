package com.example.springTestProj.Entities.QuestionEntities;


import javafx.beans.property.SimpleStringProperty;
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
@Table(name = "matching_q")
public class MatchingQuestion {
    @Id
    @Column(name = "question_id")
    private String questionID;
    @Column(name = "term")
    private String term;
    @Column(name = "correct_answer")
    private String correctAnswer;
    @Column(name = "term_graphic")
    private String termGraphic;
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
    
    public MatchingQuestion(String questionID, String term, String correctAnswer)
    {
        this.questionID = questionID;
        this.term = term;
        this.correctAnswer = correctAnswer;
        
    }

    public MatchingQuestion() {

    }

    public MatchingQuestion(String term, String correctAnswer) {
        this.term = term;
        this.correctAnswer = correctAnswer;

    }


//    public String getTerm()
//    {
//        return term.get();
//    }
//    public String getAnswer()
//    {
//        return answer.get();
//    }
//    public void setTerm(String fterm)
//    {
//        term.set(fterm);
//    }
//    public void setAnswer(String fanswer)
//    {
//        answer.set(fanswer);
//    }
}
