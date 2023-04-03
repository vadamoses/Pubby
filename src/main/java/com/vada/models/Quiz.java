package com.vada.models;

public class Quiz {
	private int quizSize;

	/**
	 * 
	 */
	public Quiz() {
		super();
	}

	/**
	 * @param quizSize
	 */
	public Quiz(int quizSize) {
		super();
		this.quizSize = quizSize;
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
}
