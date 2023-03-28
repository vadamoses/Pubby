package com.vada.contollers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vada.interfaces.QuestionServiceImpl;
import com.vada.models.LocalQuiz;
import com.vada.models.Question;

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

	@Getter
	@Setter
	private List<Question> answeredQuestions = new ArrayList<>();
	@Getter
	@Setter
	private int qCount = 0;

	/**
	 * @param model
	 * @return qService.getQuestionsList()
	 */
	@GetMapping(value = "/set-questions")
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
	 * @param question
	 */
	@PostMapping(value = "/add-new-question")
	public void addQuestion(@RequestBody Question question) {
		LOGGER.info("Start - addQuestion");
		this.qService.addQuestion(question);
		LOGGER.info("End - addQuestion");
	}

	/**
	 * @param quizSize
	 * @param model
	 * @return playQuiz
	 */
	@PostMapping(value = "/play-quiz")
	public void playQuiz(@RequestParam int quizSize
			/* + other params in future like time,genre etc */) {
		LOGGER.info("Start - playQuiz");
		
		this.qService.setQuizSize(quizSize);

		LOGGER.info("End - playQuiz");
	}
	
	@GetMapping(value = "/get-quiz-questions")
	public List<Question> getQuizQuestions() {
		LOGGER.info("Start - getQuizQuestions");

		List<Question> quizQuestions = qService.getQuizQuestions();

		LOGGER.info("End - getQuizQuestions");
		return quizQuestions;
	}

	/**
	 * @param model
	 */
	@GetMapping(value = "/ask-question")
	public void askQuestion(ModelMap model) {
		LOGGER.info("Start - askQuestion");

		if (this.getQCount() < this.qService.getQuestionsList().size()) {
			model.addAttribute("currentQuestion", qService.getQuestionsList().get(this.getQCount()));
			this.setQCount(this.getQCount() + 1);
		}

		LOGGER.info("End - askQuestion");
	}

	/**
	 * @param currentQuestion
	 * @param model
	 */
	@PostMapping(value = "/validate-answers")
	public void validateAnswers(@RequestBody Question currentQuestion, List<String> userAnswers,
			ModelMap model) {
		LOGGER.info("Start - validateAnswers");
		
		this.qService.validateUserAnswers(currentQuestion, userAnswers);
		model.addAttribute("quizScore", qService.getScore());
		LOGGER.info("End - validateAnswers");
	}

	@GetMapping(value = "/show-questions-list")
	public String showQuizQuestionsList(ModelMap model) {
		LOGGER.info("Start - showQuizQuestionsList");

		LOGGER.info("End - showQuizQuestionsList");
		return "questions-list";
	}

}
