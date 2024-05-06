package com.techtitans.surveyservice.surveyservice.web;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.techtitans.surveyservice.surveyservice.domain.Option;
import com.techtitans.surveyservice.surveyservice.domain.OptionRepository;
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
    @Autowired
    private OptionRepository optionRepository;

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
        String[] listOfQuestionTypes = surveyForm.getQuestionTypes().split(",");
        for (int i = 0; i < listOfQuestionStrings.length; i++) {
            String questionText = listOfQuestionStrings[i];
            String questionType = listOfQuestionTypes[i];
            Question question = new Question(questionText, newSurvey);
            question.setType(questionType);
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

    @GetMapping("/questionedit/{id}")
    public String getEditQuestion(@PathVariable("id") Long id, Model model) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            Survey survey = question.getSurvey();
            model.addAttribute("question", question);
            model.addAttribute("survey", survey);
            return "editquestion";
        } else {
            return "error";
        }
    }

    @GetMapping("/addoption/{id}")
    public String getMethodName(@PathVariable("id") Long id, Model model) {
        Option newOption = new Option();
        Question question = questionRepository.findById(id).get();
        newOption.setQuestion(question);
        model.addAttribute("option", newOption);
        return "addoption";
    }

    @PostMapping("/savenewoption")
    public String postMethodName(@ModelAttribute Option option) {
        System.out.println("AHHGJKGKJAGKJHGK " + option.getQuestion() + "  dkljshflksjhdslkjfhdlkgfkjlsgajlk");
        Question question = option.getQuestion();
        List<Option> options = question.getOptions();
        options.add(option);
        optionRepository.save(option);
        return "redirect:/questionedit/" + question.getId();
    }

    @PostMapping("/saveeditedquestion")
    public String saveEditedQuestion(@ModelAttribute Question question) {
        questionRepository.save(question);
        return "redirect:/surveylist";
    }

    @GetMapping("/surveyedit/{id}")
    public String editsurvey(@PathVariable("id") Long id, Model model) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Survey survey = surveyRepository.findById(id).get();
        model.addAttribute("startDate", formatter.format(survey.getStartDate()));
        model.addAttribute("endDate", formatter.format(survey.getStartDate()));
        model.addAttribute("survey", survey);
        model.addAttribute("questions", survey.getQuestions());
        return "surveyedit";
    }

    @PostMapping("/saveeditedsurvey")
    public String saveEditedSurvey(@ModelAttribute("survey") Survey survey,
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date endDate) {
        survey.setStartDate(startDate);
        survey.setEndDate(endDate);

        List<Question> questions = survey.getQuestions();
        for (Question question : questions) {
            question.setSurvey(survey);
            questionRepository.save(question);
        }
        survey.setQuestions(questions);
        surveyRepository.save(survey);

        return "redirect:/surveylist";
    }
}
