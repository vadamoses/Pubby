package com.vada.contollers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vada.interfaces.QuestionServiceImpl;
import com.vada.models.LocalQuiz;
import com.vada.models.Question;
import com.vada.models.Quiz;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 2050610
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/quiz")
public class QuizController {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuizController.class);

	@Autowired
	private QuestionServiceImpl qService;

	LocalQuiz lQuiz = new LocalQuiz();

	Quiz newQuiz = new Quiz();

	@Getter
	@Setter
	private List<Question> answeredQuestions = new ArrayList<>();
	@Getter
	@Setter
	private int quizSize;
	@Getter
	@Setter
	private int qCount = 0;

	/**
	 * @param model
	 * @return qService.getQuestionsList()
	 */
	@GetMapping(value = "/set-questions-from-file")
	public void setAllQuestionsJSON() {
		LOGGER.info("Start - setAllQuestionsJSON");

		try {
			this.lQuiz.fetchAllQuizQuestions();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		qService.saveQuestions(this.lQuiz.getQuizQuestions());

		LOGGER.info("End - setAllQuestionsJSON");
	}

	/**
	 * @param quizSize
	 * @param model
	 */
	@PostMapping(value = "/setup-quiz")
	public ResponseEntity<Quiz> setupQuiz(@RequestBody Quiz newQuiz
	/* + other params in future like time,genre etc */) {
		LOGGER.info("Start - setupQuiz");
		qService.setQuizQuestions(newQuiz.getQuizSize());
		LOGGER.info("End - setupQuiz");
		return new ResponseEntity<>(newQuiz, HttpStatus.OK);
	}

	/**
	 * @return quizQuestions
	 */
	@GetMapping(value = "/get-quiz-questions")
	public ResponseEntity<List<Question>> getQuizQuestions() {
		LOGGER.info("Start - getQuizQuestions");
		List<Question> quizQuestions = qService.getQuizQuestions();
		LOGGER.info("End - getQuizQuestions");
		return new ResponseEntity<>(quizQuestions, HttpStatus.OK);
	}

	/**
	 * @return quizQuestions
	 */
	@GetMapping(value = "/refresh-quiz-questions")
	public ResponseEntity<List<Question>> refreshQuizQuestions() {
		LOGGER.info("Start - refreshQuizQuestions");
		// qService.setQuestionsList(List.of());
		List<Question> quizQuestions = qService.getQuizQuestions();
		LOGGER.info("End - refreshQuizQuestions");
		return new ResponseEntity<>(quizQuestions, HttpStatus.OK);
	}

	/**
	 * @param question
	 */
	@PostMapping(value = "/add-new-question")
	public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
		LOGGER.info("Start - addQuestion");
		Question newQuestion = this.qService.addQuestion(question);
		LOGGER.info("End - addQuestion");
		return new ResponseEntity<>(newQuestion, HttpStatus.CREATED);
	}

	/**
	 * @param model
	 */
	@GetMapping(value = "/ask-question")
	public ResponseEntity<Question> askQuestion(ModelMap model) {
		LOGGER.info("Start - askQuestion");
		Question question = new Question();
		if (this.getQCount() < this.qService.getQuestionsList().size()) {
			question = qService.getQuestionsList().get(this.getQCount());
			this.setQCount(this.getQCount() + 1);
		}

		LOGGER.info("End - askQuestion");
		return new ResponseEntity<>(question, HttpStatus.OK);
	}

	/**
	 * @param currentQuestion
	 * @param model
	 */
	@PostMapping(value = "/validate-answers")
	public void validateAnswers(@RequestBody Question currentQuestion, List<String> userAnswers) {
		LOGGER.info("Start - validateAnswers");

		this.qService.validateUserAnswers(currentQuestion, userAnswers);

		LOGGER.info("End - validateAnswers");
	}

	@DeleteMapping(value = "/delete-quiz-questions")
	public ResponseEntity<HttpStatus> deleteAllQuizQuestions() {
		LOGGER.info("Start - deleteAllQuizQuestions");

		this.qService.deleteAllQuizQuestions();

		LOGGER.info("End - deleteAllQuizQuestions");
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
