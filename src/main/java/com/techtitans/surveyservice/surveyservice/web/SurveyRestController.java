package com.techtitans.surveyservice.surveyservice.web;

import com.techtitans.surveyservice.surveyservice.domain.Answer;
import com.techtitans.surveyservice.surveyservice.domain.AnswerRepository;
import com.techtitans.surveyservice.surveyservice.domain.Question;
import com.techtitans.surveyservice.surveyservice.domain.QuestionRepository;
import com.techtitans.surveyservice.surveyservice.domain.Survey;
import com.techtitans.surveyservice.surveyservice.domain.SurveyRepository;

import java.util.List;
import java.util.Optional;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class SurveyRestController {

    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/surveys")
    public @ResponseBody List<Survey> getSurveys() {
        return (List<Survey>) surveyRepository.findAll();
    }

    @GetMapping("/survey/{id}")
    public @ResponseBody Optional<Survey> getSurvey(@PathVariable("id") Long id) {
        return surveyRepository.findById(id);
    }

    @GetMapping("/question/{id}")
    public @ResponseBody Optional<Question> getQuestion(@PathVariable("id") Long id) {
        return questionRepository.findById(id);
    }

    //sidotaan vastaanotettu JSON Map-tietorakenteeseen
    @PostMapping("/saveanswers")
    public @ResponseBody String postSurvey(@RequestBody Map<String, String> surveyParams) {

        //loopataan kaikki alkiot
        for (Map.Entry<String, String> entry : surveyParams.entrySet()) {

            //haetaan kysymyksen id
            Long questionId = Long.parseLong(entry.getKey());

            //kysymyksen id:llä saadaan haettua haluttu kysymys tietokannasta
            Question question = questionRepository.findById(questionId).orElse(null);

            if (question != null) {

                //luodaan uusi vastaus, sidotaan se kysymykseen ja tallennetaan se tietokantaan
                Answer answer = new Answer(entry.getValue(), question);
                answerRepository.save(answer);

            }
        }
        return "";
    }
}
