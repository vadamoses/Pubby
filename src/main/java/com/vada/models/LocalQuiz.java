package com.vada.models;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.vada.interfaces.LocalQuizService;

public class LocalQuiz implements LocalQuizService {
	private long quizId;
	private int quizSize;
	private int countr = 0;
	Random randomNumber = new Random();
	private List<Question> quizQuestions = new ArrayList<>();

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public LocalQuiz() {
		super();
	}

	/**
	 * @param quizId
	 * @param quizSize
	 */
	public LocalQuiz(int quizSize) {
		super();
		this.quizSize = quizSize;
	}

	/**
	 * @param quizId
	 * @param quizSize
	 */
	public LocalQuiz(long quizId, int quizSize) {
		super();
		this.quizId = quizId;
		this.quizSize = quizSize;
	}

	/**
	 * @return the quizId
	 */
	public long getQuizId() {
		return quizId;
	}

	/**
	 * @param quizId the quizId to set
	 */
	public void setQuizId(long quizId) {
		this.quizId = quizId;
	}

	/**
	 * @return the quizSize
	 */
	public int getQuizSize() {
		return quizSize;
	}

	/**
	 * @param quizSize the quizSize to set
	 */
	public void setQuizSize(int quizSize) {
		this.quizSize = quizSize;
	}

	/**
	 * @return the countr
	 */
	public int getCountr() {
		return countr;
	}

	/**
	 * @param countr the countr to set
	 */
	public void setCountr(int countr) {
		this.countr = countr;
	}

	/**
	 * @return the quizQuestions
	 */
	public List<Question> getQuizQuestions() {
		return quizQuestions;
	}

	/**
	 * @param quizQuestions the quizQuestions to set
	 */
	public void setQuizQuestions(List<Question> quizQuestions) {
		this.quizQuestions = quizQuestions;
	}

	@Override
	public void fetchAllQuizQuestions() throws IOException, ParseException {
		// read from file and populate allQuestions list with Questions

		// JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader("src/main/resources/AllContextQnA.json")) {
			// Read JSON file
			Object obj = jsonParser.parse(reader);

			// convert Object to JSONObject
			JSONObject jsonObject = (JSONObject) obj;

			JSONArray questionArray = (JSONArray) jsonObject.get("QuestionsAndAnswers");

			List<Question> questionList = new ArrayList<>();
			for (Object object : questionArray) {
				// convert Object to JSONObject
				JSONObject questionObject = (JSONObject) object;

				Question q = new Question();
				q.setqId( Integer.parseInt(questionObject.get("qId").toString()));
				q.setqContext(questionObject.get("qContext").toString());
				JSONArray answerList = (JSONArray) questionObject.get("qAnswers");
				ArrayList<String> qAns = new ArrayList<>();
				for (int i = 0; i < answerList.size(); i++) {
					qAns.add((String) answerList.get(i));
				}
				q.setqAnswers(qAns);
				q.setqText(questionObject.get("qText").toString());
				questionList.add(q);
			}
			this.setQuizQuestions(questionList);

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void askQuestion() {
		int z = randomNumber.nextInt(this.getQuizQuestions().size());

		Question question = this.getQuizQuestions().get(z);
		System.out.println(question.getqText());
		System.out.println(" ");
		System.out.println("Enter your Answer:");
		System.out.println(" ");
		this.getAnswers(question);

	}

	@Override
	public void getAnswers(Question question) {
		while (this.getCountr() < this.getQuizSize()) {
			try (Scanner sc = new Scanner(System.in)) {
				String inputLines = sc.nextLine();
				List<String> givenAnswers = new ArrayList<>();
				givenAnswers.add(inputLines);
				System.out.println("Your answer is: " + validateAnswers(question, givenAnswers));
				this.setCountr(this.getCountr() + 1);
				if (this.getCountr() >= this.getQuizSize()) {
					System.out.println();
					return;
				}
				this.askQuestion();
			}
		}
	}

	@Override
	public String validateAnswers(Question question, List<String> answers) {
		return Arrays.asList(question.getqAnswers()).contains(answers) ? "Correct" : "Incorrect\n The correct answer(s): "+ question.getqAnswers();
	}
	
	@Override
	public void showCorrectAnswers(Question question) {
		//System.out.println("The Correct Answer: \n "+ question.getqAnswers());	
		question.getqAnswers().forEach(ans -> System.out.println("The Correct Answer: \n "+ ans));
	}

	@Override
	public int hashCode() {
		return Objects.hash(quizQuestions, quizId, quizSize);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof LocalQuiz))
			return false;
		LocalQuiz other = (LocalQuiz) obj;
		return Objects.equals(quizQuestions, other.quizQuestions) && countr == other.countr && quizId == other.quizId
				&& quizSize == other.quizSize;
	}

}
