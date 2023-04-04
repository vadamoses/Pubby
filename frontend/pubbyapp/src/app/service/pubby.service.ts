import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Question } from '../model/question';
import { Quiz } from '../model/quiz';

@Injectable({
	providedIn: 'root'
})
export class PubbyService {


	private questionsUrl: string;

	constructor(private http: HttpClient) {
		this.questionsUrl = 'http://localhost:8088/quiz';
	}
	setAllQuestionsJSON() {
		return this.http.get(this.questionsUrl + "/set-questions-from-file");
	}
	setupQuiz(quiz: Quiz): Observable<Quiz> {
		return this.http.post<Quiz>(`${this.questionsUrl}/setup-quiz`, quiz);
	}

	createQuestion(question: any): Observable<Question> {
		return this.http.post(`${this.questionsUrl}/add-new-question`, question);
	}
	getQuizQuestions(): Observable<Question[]> {
		return this.http.get<Question[]>(`${this.questionsUrl}/get-quiz-questions`);
	}
	refreshQuizQuestions(): Observable<Question[]> {
		return this.http.get<Question[]>(`${this.questionsUrl}/refresh-quiz-questions`);
	}

	askQuestion(): Observable<Question> {
		return this.http.get(`${this.questionsUrl}` + "/ask-question");
	}
	validateAnswers(currentQuestion: any, userAnswers: any[]) {
		return this.http.post(`${this.questionsUrl}/validate-answers`, { currentQuestion, userAnswers });
	}
	showQuizQuestionsList(): Observable<Question> {
		return this.http.get(`${this.questionsUrl}/show-questions-list`);
	}
	deleteAll(): Observable<any> {
		return this.http.delete(`${this.questionsUrl}/delete-quiz-questions`);
	}
}
