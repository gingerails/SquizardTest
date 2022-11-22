package com.example.springTestProj.Entities;

import javax.persistence.Embeddable;

@Embeddable
public enum QuestionType {
    BLANK, BOOLEAN, ESSAY, MULTIPLE_CHOICE, SHORT_FORM
}