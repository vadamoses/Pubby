package com.vada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PubbyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PubbyApplication.class, args);
		/*
		 * LocalQuiz Q = new LocalQuiz(); Q.setQuizSize(2); Q.fetchAllQuizQuestions();
		 * Q.askQuestion();
		 */
	}

}
