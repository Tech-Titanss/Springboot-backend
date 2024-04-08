package com.techtitans.surveyservice.surveyservice.web;

import org.springframework.web.bind.annotation.RestController;

import com.techtitans.surveyservice.surveyservice.domain.Question;
import com.techtitans.surveyservice.surveyservice.domain.QuestionRepository;
import com.techtitans.surveyservice.surveyservice.domain.Survey;
import com.techtitans.surveyservice.surveyservice.domain.SurveyRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@CrossOrigin
public class SurveyRestController {

    
    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/surveys")
    public @ResponseBody List<Survey> getSurveys() {
        return (List<Survey>) surveyRepository.findAll();
    }
    
    @GetMapping("surveys/{id}")
    public @ResponseBody Optional <Survey> getSurvey(@PathVariable("id") Long id) {
        return surveyRepository.findById(id);
    }
    
    @PostMapping("/surveys")
    public @ResponseBody Survey postSurvey(@RequestBody Survey survey) {
        return surveyRepository.save(survey);
    }

    @PutMapping("/surveys/{id}")
    public @ResponseBody Survey changeSurvey(@RequestBody Survey survey, @PathVariable("id") Long id) {
        Survey modifiedSurvey = surveyRepository.findById(id).get();
        modifiedSurvey.setName(survey.getName());
        modifiedSurvey.setDescription(survey.getDescription());
        return surveyRepository.save(modifiedSurvey);
    }
}
