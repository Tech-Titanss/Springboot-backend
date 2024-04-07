package com.techtitans.surveyservice.surveyservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String questionText;

    @ManyToOne
    private Survey survey;

    public Question() {
    }

    public Question(String questionText, Survey survey) {
        this.questionText = questionText;
        this.survey = survey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Survey getSurvey() {
        return this.survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

}
