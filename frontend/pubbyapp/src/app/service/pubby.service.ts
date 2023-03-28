import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap, map } from 'rxjs/operators';
import { Question } from '../model/question';

@Injectable({
	providedIn: 'root'
})
export class PubbyService {

	private questionsUrl: string;

	constructor(private http: HttpClient) {
		this.questionsUrl = 'http://localhost:8088/quiz';
	}
	public setAllQuestionsJSON() {
		return this.http.get(this.questionsUrl + "/set-questions");
	}
	public saveQuestion(question: Question) {
    	return this.http.post<Question>(this.questionsUrl, question);
  	}
	public getQuizQuestions(): Observable<Question[]>{
		return this.http.get<Question[]>(this.questionsUrl +"/get-quiz-questions")
		.pipe(
			tap(
				data => JSON.stringify(data)
			)
		);
	}
	public playQuiz(quizSize: number) {
		return this.http.post(this.questionsUrl + "/play-quiz", quizSize);
	}
	public askQuestion() {
		return this.http.get(this.questionsUrl + "/ask-question");
	}
	public validateAnswers(currentQuestion: Question, userAnswers: string[]) {
		return this.http.post<Question>(this.questionsUrl + "/validate-answers", { currentQuestion, userAnswers });
	}
	public showQuizQuestionsList() {
		return this.http.get(this.questionsUrl + "/show-questions-list");
	}
}
