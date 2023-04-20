package com.vada.interfaces;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.vada.models.Question;

@Transactional
public interface QuestionService {

	List<Question> getAllQuestions();

	List<Question> getQuizQuestions();

	Question addQuestion(final Question question);

	Question editQuestion(final Question question);

	List<Question> saveQuestions(List<Question> questionsList);

	Question findQuestion(long questionId);

	void deleteAllQuizQuestions();

	void setupQuiz(int quizSize);
	
	boolean validateAnswer(String givenAnswer);

}
