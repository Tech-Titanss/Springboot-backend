package com.techtitans.surveyservice.surveyservice.web;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.techtitans.surveyservice.surveyservice.domain.Survey;
import com.techtitans.surveyservice.surveyservice.domain.SurveyRepository;
import com.techtitans.surveyservice.surveyservice.domain.Question;
import com.techtitans.surveyservice.surveyservice.domain.QuestionRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SurveyController {

    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/addsurvey")
    public String addSurvey(Model model) {
        model.addAttribute("survey", new Survey());
        return "addsurvey";
    }

    @GetMapping("/surveylist")
    public String listSurveys(Model model) {
        model.addAttribute("surveys", surveyRepository.findAll());
        return "surveylist";
    }

    @GetMapping("/surveyquestions/{id}")
    public String listSurveyQuestions(@PathVariable("id") Long surveyId, Model model) {

        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);

        if (optionalSurvey.isPresent()) {

            Survey survey = optionalSurvey.get();
            model.addAttribute("survey", survey);
            model.addAttribute("question", new Question());

            return "surveyquestions";

        } else {

            // Handle book not found
            return "error"; // TODO: create error page
        }
    }

    @PostMapping("/savesurvey")
    public String saveSurvey(Survey survey) {

        surveyRepository.save(survey);

        return "redirect:/surveylist";
    }

    @GetMapping("/addquestion/{id}")
    public String addReader(@PathVariable("id") Long surveyId, Model model) {

        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);

        if (optionalSurvey.isPresent()) {

            Survey survey = optionalSurvey.get();
            model.addAttribute("survey", survey);
            model.addAttribute("question", new Question());

            return "addquestion";

        } else {

            // Handle book not found
            return "error"; // You need to create an error page
        }
    }

    @PostMapping("/addquestion")
    public String saveReader(Question question, @RequestParam("surveyId") Long surveyId) {

        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);

        Survey survey = optionalSurvey.get();

        question.setSurvey(survey);
        questionRepository.save(question); // Tallenna kysymys ensin
        survey.getQuestions().add(question);
        surveyRepository.save(survey); // Tallenna kysely sen jälkeen
        return "redirect:/surveyquestions/" + surveyId;

    }

}
