package com.techtitans.surveyservice.surveyservice.domain;

public class SurveyForm {
    private Long id;
    private String name;
    private String description;
    private String questions;

    public SurveyForm() {
    }

    public SurveyForm(Long id, String name, String description, String questions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }
}
