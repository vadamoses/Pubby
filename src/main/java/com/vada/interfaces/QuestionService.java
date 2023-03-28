package com.vada.interfaces;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.vada.models.Question;

@Transactional
public interface QuestionService {

	List<Question> getAllQuestions();

	List<Question> getQuizQuestions();

	List<String> getQuestionAnswers(Question question);

	boolean validateUserAnswers(Question question, List<String> userAnswers);

	List<String> showCorrectAnswers(Question question);

	void addQuestion(final Question question);
	
	void editQuestion(final Question question);

	List<Question> saveQuestions(List<Question> questionsList);

	Question findQuestion(Integer qId);

}
