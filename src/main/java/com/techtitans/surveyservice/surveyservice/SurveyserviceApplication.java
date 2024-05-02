package com.techtitans.surveyservice.surveyservice;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.techtitans.surveyservice.surveyservice.domain.QuestionRepository;
import com.techtitans.surveyservice.surveyservice.domain.Survey;
import com.techtitans.surveyservice.surveyservice.domain.SurveyRepository;
import com.techtitans.surveyservice.surveyservice.domain.Question;

@SpringBootApplication
public class SurveyserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurveyserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(SurveyRepository surveyRepository, QuestionRepository questionRepository) {
		return (args) -> {

			Survey surveyTest1 = new Survey("Kouluruoka kysely", "Kysely kouluruuasta ja mielipiteestä ruokaan",
					new Date(), new Date());
			Survey surveyTest2 = new Survey("Pasilan kampuksen kysely",
					"Kysymykset liittyvät Haaga-Helian pasilan kampuksen viihtyisyyteen ja yleisesti tiloihin",
					new Date(), new Date());

			surveyRepository.save(surveyTest1);
			surveyRepository.save(surveyTest2);

			Question kouluruoka1 = new Question("Mikä on lempiruokasi koulussa?", surveyTest1);
			Question kouluruoka2 = new Question("Oletko tyytyväinen Haaga-Helian ruokaan?", surveyTest1);
			// Question kouluruoka3 = new Question("Mitä mieltä olet kouluruuan hinnasta?",
			// surveyTest1);
			Question kouluruoka4 = new Question("Kerro omin sanoin mitä mieltä olet kouluruuasta.", surveyTest1);
			Question kouluruoka5 = new Question("Onko kouluruoka tarpeeksi monipuolista?", surveyTest1);

			Question kouluruoka6 = new Question("Kouluruoka on sopivan hintaista.", "radiobutton", surveyTest1);
			kouluruoka6.getOptions().add(1);
			kouluruoka6.getOptions().add(2);
			kouluruoka6.getOptions().add(3);
			kouluruoka6.getOptions().add(4);
			kouluruoka6.getOptions().add(5);

			questionRepository.save(kouluruoka1);
			questionRepository.save(kouluruoka2);
			// questionRepository.save(kouluruoka3);
			questionRepository.save(kouluruoka4);
			questionRepository.save(kouluruoka5);
			questionRepository.save(kouluruoka6);

			Question kampus1 = new Question("Mitä mieltä olet Pasilan kampuksen siisteydestä?", surveyTest2);
			Question kampus2 = new Question("Millä tavalla kuljet Pasilan kampukselle?", surveyTest2);
			Question kampus3 = new Question("Onko Pasilan kampuksella tarpeeksi oleskelutilaa opiskella/odotella?",
					surveyTest2);
			Question kampus4 = new Question("Onko Pasilan kampuksella jotakin parannettavaa?", surveyTest2);
			Question kampus5 = new Question("Oletko tyytyväinen kouluruokaan?", surveyTest2);

			questionRepository.save(kampus1);
			questionRepository.save(kampus2);
			questionRepository.save(kampus3);
			questionRepository.save(kampus4);
			questionRepository.save(kampus5);
		};
	}
}
