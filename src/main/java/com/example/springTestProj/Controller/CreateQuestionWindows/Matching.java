package com.example.springTestProj.Controller.CreateQuestionWindows;


import javafx.beans.property.SimpleStringProperty;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Orames
 */
public class Matching {
    static SimpleStringProperty term;
    static SimpleStringProperty answer;
    
    public Matching(String term, String answer) 
    {
        this.term=new SimpleStringProperty(term);
        this.answer=new SimpleStringProperty(answer);
        
    }
    
    public String getTerm()
    {
        return term.get();
    }
    public String getAnswer()
    {
        return answer.get();
    }
    public void setTerm(String fterm)
    {
        term.set(fterm);
    }
    public void setAnswer(String fanswer)
    {
        answer.set(fanswer);
    }
}
