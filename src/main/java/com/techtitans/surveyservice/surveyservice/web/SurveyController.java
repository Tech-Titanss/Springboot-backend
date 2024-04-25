package com.techtitans.surveyservice.surveyservice.web;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import com.techtitans.surveyservice.surveyservice.domain.Survey;
import com.techtitans.surveyservice.surveyservice.domain.SurveyForm;
import com.techtitans.surveyservice.surveyservice.domain.SurveyRepository;

import jakarta.validation.Valid;

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

    @GetMapping({ "/", "/surveylist" })
    public String listSurveys(Model model) {
        model.addAttribute("surveys", surveyRepository.findAll());
        return "surveylist";
    }

    @GetMapping("/surveyquestions/{id}")
    public String listSurveyQuestions(@PathVariable("id") Long surveyId, Model model) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);

        if (optionalSurvey.isPresent()) {

            Survey survey = optionalSurvey.get();
            String startDateString = formatter.format(survey.getStartDate());
            String endDateString = formatter.format(survey.getEndDate());
            model.addAttribute("survey", survey);
            model.addAttribute("question", new Question());
            model.addAttribute("answeringPeriod", (startDateString + "-" + endDateString));

            return "surveyquestions";

        } else {

            // Handle book not found
            return "error"; // TODO: create error page
        }
    }

    @PostMapping("/savesurvey")
    public String saveSurvey(@Valid @ModelAttribute SurveyForm surveyForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("survey", surveyForm);
            return "addsurvey";
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Survey newSurvey = new Survey();
        newSurvey.setName(surveyForm.getName());
        newSurvey.setDescription(surveyForm.getDescription());
        try {
            Date startDate = formatter.parse(surveyForm.getStartDate());
            Date endDate = formatter.parse(surveyForm.getEndDate());
            newSurvey.setStartDate(startDate);
            newSurvey.setEndDate(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        surveyRepository.save(newSurvey);

        List<Question> questions = new ArrayList<>();
        String[] listOfQuestionStrings = surveyForm.getQuestions().split(",");
        for (int i = 0; i < listOfQuestionStrings.length; i++) {
            String questionText = listOfQuestionStrings[i];
            Question question = new Question(questionText, newSurvey);
            questionRepository.save(question);
            questions.add(question);
        }
        newSurvey.setQuestions(questions);
        surveyRepository.save(newSurvey);

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

    @GetMapping("/surveyedit/{id}")
    public String editsurvey(@PathVariable("id") Long id, Model model) {
        Survey survey = surveyRepository.findById(id).get();
        model.addAttribute("survey", survey);
        model.addAttribute("questions", survey.getQuestions());
        return "surveyedit";
    }

    @PostMapping("/saveeditedsurvey")
    public String saveEditedSurvey(@ModelAttribute("survey") Survey survey) {
        List<Question> questions = survey.getQuestions();
   
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            System.out.println("CONSOLE LOOOGOGOGGOGOGOGGO" + question.getId() + " JAJAJAJAJAAJJAJAJAJ");
            questionRepository.save(question);
            survey.setQuestions(questions);
        }

        surveyRepository.save(survey);
        return "redirect:/surveylist";
    }
}
