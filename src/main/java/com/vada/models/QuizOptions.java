package com.vada.models;

public class QuizOptions {
    private int quizSize;

    public QuizOptions() {
    }

    public QuizOptions(int quizSize) {
        this.quizSize = quizSize;
    }

    public int getQuizSize() {
        return quizSize;
    }

    public void setQuizSize(int quizSize) {
        this.quizSize = quizSize;
    }
}

