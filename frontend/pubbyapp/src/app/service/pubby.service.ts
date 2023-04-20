import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Question } from '../model/question';
import { QuizOptions } from '../model/quiz-options';

@Injectable({
  providedIn: 'root'
})
export class PubbyService {

  private authHeaders: HttpHeaders;

  constructor(private http: HttpClient) {
    this.authHeaders = new HttpHeaders();
  }

  private getAuthHeaders(): HttpHeaders {
	  this.authHeaders = this.authHeaders.set('Authorization', `Bearer ${localStorage.getItem('currentUser')}`);
	  this.authHeaders = this.authHeaders.set('Content-Type', 'application/json');
	  return this.authHeaders;
  }


  public setupQuiz(quizOptions: QuizOptions): Observable<QuizOptions> {
    return this.http.post<QuizOptions>(`${environment.quizUrl}/setup-quiz`, quizOptions, { headers: this.getAuthHeaders() });
  }

  public getQuizQuestions(): Observable<Question[]> {
    return this.http.get<Question[]>(`${environment.quizUrl}/get-quiz-questions`, { headers: this.getAuthHeaders() });
  }
  
  public validateAnswer(givenAnswer: any): Observable<boolean> {
	  const options = { params: { givenAnswer }, headers: this.getAuthHeaders() };
	  return this.http.get<boolean>(`${environment.quizUrl}/validate-answer`, options);
  }

  public addQuestion(question: Question): Observable<Question> {
    return this.http.post<Question>(`${environment.quizUrl}/add`, { question }, { headers: this.getAuthHeaders() });
  }

  public editQuestion(question: Question): Observable<Question> {
    return this.http.put<Question>(`${environment.quizUrl}/edit/${question.questionId}`, question, { headers: this.getAuthHeaders() });
  }

  public saveQuestions(questionsList: Question[]): Observable<any> {
    return this.http.post<any>(`${environment.quizUrl}/save`, questionsList, { headers: this.getAuthHeaders() });
  }

  public findQuestion(questionId: number): Observable<Question> {
    return this.http.get<Question>(`${environment.quizUrl}/find/${questionId}`, { headers: this.getAuthHeaders() });
  }

  public deleteAllQuizQuestions(): Observable<any> {
    return this.http.delete<any>(`${environment.quizUrl}/refresh-quiz-questions`, { headers: this.getAuthHeaders() });
  }
}
