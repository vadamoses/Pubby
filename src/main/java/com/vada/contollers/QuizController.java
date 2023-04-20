package com.vada.contollers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vada.interfaces.impl.QuestionServiceImpl;
import com.vada.models.PossibleAnswer;
import com.vada.models.Question;
import com.vada.models.QuizOptions;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 2050610
 *
 */
@Controller
@RequestMapping(value = "/quiz", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuizController {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuizController.class);

	@Autowired
	private QuestionServiceImpl qService;

	QuizOptions quizOptions = new QuizOptions();
	@Getter
	@Setter
	private int quizSize;
	@Getter
	@Setter
	private int qCount = 0;

	@GetMapping(value = "/load-questions-from-file")
	public void loadQuestionsFromFile() {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Question[] questions = objectMapper.readValue(new File("quizQuestions.json"), Question[].class);
			qService.saveQuestions(Arrays.asList(questions));
			LOGGER.info("Questions loaded successfully.");
		} catch (IOException e) {
			LOGGER.info("Failed to read quiz questions from file: " + e.getMessage());
		}
	}

	@PostMapping(value = "/setup-quiz")
	public ResponseEntity<QuizOptions> setupQuiz(@RequestBody QuizOptions quizOptions) {
		LOGGER.info("Setting up quiz with quiz size: " + quizOptions.getQuizSize());
		qService.setupQuiz(quizOptions.getQuizSize());
		LOGGER.info("End - setupQuiz");
		return ResponseEntity.ok(quizOptions);
	}

	/**
	 * @return quizQuestions
	 */
	@GetMapping(value = "/get-quiz-questions")
	public ResponseEntity<List<Question>> getQuizQuestions() {
		LOGGER.info("Start - getQuizQuestions");
		List<Question> quizQuestions = qService.getQuizQuestions();
		List<PossibleAnswer> possibleAnswers = new ArrayList<>();
		for (Question question : quizQuestions) {
			possibleAnswers.addAll(question.getPossibleAnswers());
		}
		LOGGER.info("End - getQuizQuestions");
		return ResponseEntity.ok(quizQuestions);
	}

	/**
	 * @param givenAnswer
	 * @return boolean;
	 */
	@GetMapping(value = "/validate-answer/")
	public Boolean validateAnswer(@RequestParam(value = "givenAnswer") String givenAnswer) {
	    LOGGER.info("Start - validateAnswer");
	    LOGGER.info("End - validateAnswer");
	    return qService.validateAnswer(givenAnswer);
	}


	/**
	 * @return quizQuestions
	 */
	@GetMapping(value = "/refresh-quiz-questions")
	public ResponseEntity<List<Question>> refreshQuizQuestions() {
		LOGGER.info("Start - refreshQuizQuestions");
		List<Question> quizQuestions = qService.getQuizQuestions();
		LOGGER.info("End - refreshQuizQuestions");
		return ResponseEntity.ok(quizQuestions);
	}

	/**
	 * @param question
	 */
	@PostMapping(value = "/add-new-question")
	public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
		LOGGER.info("Start - addQuestion");
		Question newQuestion = this.qService.addQuestion(question);
		LOGGER.info("End - addQuestion");
		return ResponseEntity.status(HttpStatus.CREATED).body(newQuestion);
	}

	@DeleteMapping(value = "/delete-quiz-questions")
	public ResponseEntity<HttpStatus> deleteAllQuizQuestions() {
		LOGGER.info("Start - deleteAllQuizQuestions");
		this.qService.deleteAllQuizQuestions();

		LOGGER.info("End - deleteAllQuizQuestions");
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
