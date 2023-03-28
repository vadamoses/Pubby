package com.vada.interfaces;

import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.List;

import com.vada.models.Question;

public interface LocalQuizService {
	void fetchAllQuizQuestions() throws IOException, ParseException;

	void askQuestion();

	void getAnswers(Question question);

	String validateAnswers(Question question, List<String> answers);
	
	void showCorrectAnswers(Question question);
}
