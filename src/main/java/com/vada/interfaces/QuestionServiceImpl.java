package com.vada.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vada.models.Question;
import com.vada.repositories.QuestionRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Getter;
import lombok.Setter;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepository questionRepo;
	@PersistenceContext
	private EntityManager entityManager;

	@Getter
	@Setter
	private int score = 0;
	@Getter
	@Setter
	private List<Question> questionsList;

	@Override
	public List<Question> getAllQuestions() {
		return questionRepo.findAll();
	}

	@Override
	public void setQuizQuestions(int quizSize) {
		this.setQuestionsList(entityManager.createQuery("SELECT q FROM Question q ORDER BY RAND()", Question.class)
				.setMaxResults(quizSize).getResultList());
	}

	@Override
	public List<Question> getQuizQuestions() {
		return this.getQuestionsList();
	}

	@Override
	public Question findQuestion(final Integer qId) {
		Question question = new Question();
		Optional<Question> questionOptional = questionRepo.findById(qId);
		if (questionOptional.isPresent()) {
			question = questionOptional.get();
		}
		return question;
	}

	@Override
	public Question addQuestion(Question question) {
		return questionRepo.save(question);
	}

	@Override
	public Question editQuestion(Question question) {
		return questionRepo.save(question);
	}

	// move method somewhere else ??
	@Override
	public List<Question> saveQuestions(List<Question> questionsList) {
		return questionRepo.saveAll(questionsList);
	}

	@Override
	public List<String> getQuestionAnswers(Question question) {
		return question.getqAnswers();
	}

	@Override
	public boolean validateUserAnswers(Question question, List<String> userAnswers) {
		int i = 0;
		for (String string : userAnswers) {
			if (question.getqAnswers().contains(string)) {
				i++;
			}
		}
		this.setScore(this.getScore() + i);
		return i == question.getqAnswers().size();
	}

	@Override
	public List<String> showCorrectAnswers(Question question) {
		return question.getqAnswers();
	}

	@Override
	public void deleteAllQuizQuestions() {
		this.questionsList.clear();
	}

}
