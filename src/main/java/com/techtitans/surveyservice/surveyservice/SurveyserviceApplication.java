package com.techtitans.surveyservice.surveyservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.techtitans.surveyservice.surveyservice.domain.QuestionRepository;
import com.techtitans.surveyservice.surveyservice.domain.Survey;
import com.techtitans.surveyservice.surveyservice.domain.SurveyRepository;
import com.techtitans.surveyservice.surveyservice.domain.Option;
import com.techtitans.surveyservice.surveyservice.domain.OptionRepository;
import com.techtitans.surveyservice.surveyservice.domain.Question;

@SpringBootApplication
public class SurveyserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurveyserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(SurveyRepository surveyRepository, QuestionRepository questionRepository,
			OptionRepository optionRepository) {
		return (args) -> {

			Survey surveyTest1 = new Survey("Kouluruoka kysely", "Kysely kouluruuasta ja mielipiteestä ruokaan",
					new Date(), new Date());
			Survey surveyTest2 = new Survey("Pasilan kampuksen kysely",
					"Kysymykset liittyvät Haaga-Helian pasilan kampuksen viihtyisyyteen ja yleisesti tiloihin",
					new Date(), new Date());

			surveyRepository.save(surveyTest1);
			surveyRepository.save(surveyTest2);

			Question kouluruoka1 = new Question("Mikä on lempiruokasi koulussa?", "text", surveyTest1);
			Question kouluruoka2 = new Question("Oletko tyytyväinen Haaga-Helian ruokaan?", "text", surveyTest1);
			Question kouluruoka3 = new Question("Mitä mieltä olet kouluruuan hinnasta?", "text", surveyTest1);
			Question kouluruoka4 = new Question("Kerro omin sanoin mitä mieltä olet kouluruuasta.", "text",surveyTest1);
			Question kouluruoka5 = new Question("Onko kouluruoka tarpeeksi monipuolista?", "text", surveyTest1);
			Question kouluruoka6 = new Question("Mihin ikäryhmään kuulut?", "radiobutton", surveyTest1);

			questionRepository.save(kouluruoka1);
			questionRepository.save(kouluruoka2);
			questionRepository.save(kouluruoka3);
			questionRepository.save(kouluruoka4);
			questionRepository.save(kouluruoka5);
			questionRepository.save(kouluruoka6);

			Option option1 = new Option("10-20");
			option1.setQuestion(kouluruoka6);
			Option option2 = new Option("20-30");
			option2.setQuestion(kouluruoka6);
			Option option3 = new Option("30-40");
			option3.setQuestion(kouluruoka6);
			Option option4 = new Option("yli 40");
			option4.setQuestion(kouluruoka6);

			optionRepository.save(option1);
			optionRepository.save(option2);
			optionRepository.save(option3);
			optionRepository.save(option4);

			List<Option> options = new ArrayList<>();
			options.add(option1);
			options.add(option2);
			options.add(option3);
			options.add(option4);
			kouluruoka6.setOptions(options);

			Question kampus1 = new Question("Mitä mieltä olet Pasilan kampuksen siisteydestä?", "text", surveyTest2);
			Question kampus2 = new Question("Millä tavalla kuljet Pasilan kampukselle?", "text", surveyTest2);
			Question kampus3 = new Question("Onko Pasilan kampuksella tarpeeksi oleskelutilaa opiskella/odotella?", "text", surveyTest2);
			Question kampus4 = new Question("Onko Pasilan kampuksella jotakin parannettavaa?", "text", surveyTest2);
			Question kampus5 = new Question("Oletko tyytyväinen kouluruokaan?", "text", surveyTest2);

			questionRepository.save(kampus1);
			questionRepository.save(kampus2);
			questionRepository.save(kampus3);
			questionRepository.save(kampus4);
			questionRepository.save(kampus5);
		};
	}
}
