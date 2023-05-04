import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Question } from '../model/question';
import { QuizOptions } from '../model/quiz-options';


const httpOptions = {
   headers: new HttpHeaders({
     'Content-Type':  'application/json'
   })
 };


@Injectable({
  providedIn: 'root'
})
export class PubbyService {

  constructor(private http: HttpClient) {}

  public setupQuiz(quizOptions: QuizOptions): Observable<QuizOptions> {
	  return this.http.post<QuizOptions>(`${environment.quizUrl}/setup-quiz`, quizOptions);
  }

  public getQuizQuestions(): Observable<Question[]> {
    return this.http.get<Question[]>(`${environment.quizUrl}/get-quiz-questions`);
  }

  public validateAnswer(givenAnswer: any): Observable<boolean> {
    const options = { params: { givenAnswer }};
    return this.http.get<boolean>(`${environment.quizUrl}/validate-answer`, options);
  }

  public addQuestion(question: Question): Observable<Question> {
    return this.http.post<Question>(`${environment.quizUrl}/add`, { question });
  }

  public editQuestion(question: Question): Observable<Question> {
    return this.http.put<Question>(`${environment.quizUrl}/edit/${question.questionId}`, question );
  }

  public saveQuestions(questionsList: Question[]): Observable<any> {
    return this.http.post<any>(`${environment.quizUrl}/save`, questionsList);
  }

  public findQuestion(questionId: number): Observable<Question> {
    return this.http.get<Question>(`${environment.quizUrl}/find/${questionId}`);
  }

  public deleteAllQuizQuestions(): Observable<any> {
    return this.http.delete<any>(`${environment.quizUrl}/refresh-quiz-questions`);
  }
}
