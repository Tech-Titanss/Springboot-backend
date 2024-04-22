package com.techtitans.surveyservice.surveyservice.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class SurveyForm {
    private Long id;
    @NotEmpty
    private String name;
    private String description;
    private String questions;
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$", message = "Entered date (${validatedValue}') must be in dd/mm/yyyy format")
    private String startDate;

    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(19|20)\\d\\d$", message = "Entered date (${validatedValue}') be in dd/mm/yyyy format")
    private String endDate;

    public SurveyForm() {
    }

    public SurveyForm(Long id, String name, String description, String questions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.questions = questions;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
