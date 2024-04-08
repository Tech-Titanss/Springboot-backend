package com.techtitans.surveyservice.surveyservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.techtitans.surveyservice.surveyservice.domain.QuestionRepository;
import com.techtitans.surveyservice.surveyservice.domain.Survey;
import com.techtitans.surveyservice.surveyservice.domain.SurveyRepository;

@SpringBootApplication
public class SurveyserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurveyserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(SurveyRepository surveyRepository, QuestionRepository questionRepository) {
		return (args) -> {

			Survey surveyTest1 = new Survey("Survey #1", "Description of survey");
			Survey surveyTest2 = new Survey("Survey #2", "Description of survey 2");


			surveyRepository.save(surveyTest1);
			surveyRepository.save(surveyTest2);
		};
	}
}
