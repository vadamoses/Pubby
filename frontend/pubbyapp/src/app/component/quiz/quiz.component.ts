import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Quiz } from '../../model/quiz';
import { Question } from '../../model/question';
import { PossibleAnswer } from '../../model/possible-answer';
import { PubbyService } from '../../service/pubby.service';
import { AuthenticationService } from '../../service/authentication-service';

@Component({
	selector: 'app-quiz',
	templateUrl: './quiz.component.html',
	styleUrls: ['./quiz.component.css']
})
export class QuizComponent {

	quiz: Quiz = new Quiz();
  
	constructor(private router: Router, private quizService: PubbyService, private authService: AuthenticationService) {
	  this.loadQuizQuestions();
	}

	getQuiz() {
		return this.quiz;
	}
  
	loadQuizQuestions(): void {
	  this.quizService.getQuizQuestions().subscribe({
		next: (questions: Question[]) => {
		  this.quiz.questions = questions;
		  this.quiz.currentQuestion = this.quiz.questions[this.quiz.currentQuestionIndex];
		  this.startTimer();
		},
		error: (error) => console.log(error)
	  });
	}
  
	startTimer() {
		this.quiz.timeLeft = this.quiz.questionTimeLimit;
		this.quiz.timer = setInterval(() => {
			this.quiz.totalTimeTaken++;
		  if (this.quiz.timeLeft > 0) {
			this.quiz.timeLeft--;
		  } else {
			if (this.quiz.givenAnswers.length === this.quiz.currentQuestionIndex) {
			  // Add dummy entry to givenAnswers to indicate skipped question
			  this.quiz.givenAnswers.push({ text: "", accurate: false });
			}
			this.quiz.timeExpired = true;
			clearInterval(this.quiz.timer);
			this.nextQuestion();
		  }
		}, 1000); // updates time every second
	}	  
  
	onAnswerGiven(givenAnswer: any) {
	  if (this.quiz.currentQuestion.possibleAnswers && !this.quiz.timeExpired) {
		this.quiz.givenAnswers.push(givenAnswer.answer);
		if (this.quiz.quizComplete) {
		  this.validateAnswers();
		} else {
		  clearInterval(this.quiz.timer);
		  this.nextQuestion();
		}
	  }
	}
  
	validateAnswer(answer: PossibleAnswer, question: Question) {
	  this.quiz.currentPossibleAnswers = question.possibleAnswers;
	  const index = this.quiz.currentPossibleAnswers.findIndex(a => a.text === answer.text && a.accurate);
	  return index !== -1;
	}
  
	validateAnswers() {
		let score = 0;
		for (let i = 0; i < this.quiz.questions.length; i++) {
		  const question = this.quiz.questions[i];
		  const givenAnswer = this.quiz.givenAnswers[i];
		  if (givenAnswer && this.validateAnswer(givenAnswer, question)) {
			score++;
		  }
		}
		this.quiz.quizScore = score;
	  }
  
	nextQuestion() {
	this.quiz.currentQuestionIndex++;
	if (this.quiz.currentQuestionIndex < this.quiz.questions.length) {
		this.quiz.currentQuestion = this.quiz.questions[this.quiz.currentQuestionIndex];
		this.quiz.timeLeft = this.quiz.questionTimeLimit;
		this.quiz.timeExpired = false;
		setTimeout(() => {
		if (!this.quiz.quizComplete) {
			this.startTimer();
		}
		}, 2000);
	} else {
		this.quiz.quizComplete = true;
		this.validateAnswers();
		clearInterval(this.quiz.timer);
	}
	}
  
	getQuizScore(): number {
	  return this.quiz.quizScore;
	}
  
	restartQuiz(): void {
	  this.quiz.currentQuestionIndex = 0;
	  this.quiz.quizScore = 0;
	  this.quiz.totalTimeTaken = 0;
	  this.quiz.quizComplete = false;
	  this.quiz.givenAnswers = [];
	  clearInterval(this.quiz.timer);
	  this.loadQuizQuestions();
	}
  
	quit() {
	  this.authService.logout().subscribe(() => {
		console.log('User has been logged out');
		this.router.navigate(['/auth']);
	  });
	}
  }
  