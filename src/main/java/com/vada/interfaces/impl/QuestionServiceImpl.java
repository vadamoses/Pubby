package com.vada.interfaces.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vada.interfaces.QuestionService;
import com.vada.models.PossibleAnswer;
import com.vada.models.Question;
import com.vada.repositories.QuestionRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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
	private List<Question> questionsList;

	@Transactional
	@Override
	public void setupQuiz(int quizSize) {
		List<Question> quizQuestions = entityManager
				.createQuery("SELECT q FROM Question q LEFT JOIN FETCH q.possibleAnswers ORDER BY RAND()",
						Question.class)
				.setMaxResults(quizSize).getResultList();
		this.setQuestionsList(quizQuestions);
	}

	@Override
	public List<Question> getAllQuestions() {
		return questionRepo.findAll();
	}

	@Override
	public Question findQuestion(long questionId) {
		Question question = new Question();
		Optional<Question> questionOptional = questionRepo.findById(questionId);
		if (questionOptional.isPresent()) {
			question = questionOptional.get();
		}
		question.getPossibleAnswers().size();
		return question;
	}

	@Override
	public List<Question> getQuizQuestions() {
		return this.getQuestionsList();
	}

	@Override
	public boolean validateAnswer(String givenAnswer) {
	    for (Question question : questionsList) {
	        for (PossibleAnswer possibleAnswer : question.getPossibleAnswers()) {
	            if (possibleAnswer.isAccurate() && possibleAnswer.getAnswerText().equalsIgnoreCase(givenAnswer)) {
	                return true;
	            }
	        }
	    }
	    return false;
	}

	@Override
	public Question addQuestion(Question question) {
		for (PossibleAnswer possibleAnswer : question.getPossibleAnswers()) {
			possibleAnswer.setQuestion(question);
		}
		return questionRepo.save(question);
	}

	@Override
	public Question editQuestion(Question question) {
		Optional<Question> existingQuestion = questionRepo.findById(question.getQuestionId());
		if (existingQuestion.isPresent()) {
			Question updatedQuestion = existingQuestion.get();
			updatedQuestion.setTitle(question.getTitle());
			updatedQuestion.setQuestionText(question.getQuestionText());
			updatedQuestion.setPossibleAnswers(question.getPossibleAnswers());
			return questionRepo.save(updatedQuestion);
		}
		return null;
	}

	// move method somewhere else ??
	@Transactional
	@Override
	public List<Question> saveQuestions(List<Question> questionsList) {
		for (Question question : questionsList) {
			List<PossibleAnswer> possibleAnswers = question.getPossibleAnswers();
			if (possibleAnswers != null && !possibleAnswers.isEmpty()) {
				for (PossibleAnswer answer : possibleAnswers) {
					answer.setQuestion(question);
				}
			}
		}
		return questionRepo.saveAll(questionsList);
	}

	@Override
	public void deleteAllQuizQuestions() {
		this.questionsList.clear();
	}

}
