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
    private String term;
    private String answer;
    
    public Matching(String term, String answer) 
    {
        this.term=term;
        this.answer=answer;
        
    }
    
    public String getTerm()
    {
        return term;
    }
    public String getAnswer()
    {
        return answer;
    }
    public void setTerm(String term)
    {
        this.term=term;
    }
    public void setAnswer(String answer)
    {
        this.answer=answer;
    }
}
