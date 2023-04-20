import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Question } from '../../model/question';
import { PubbyService } from '../../service/pubby.service';
import { AuthenticationService } from '../../service/authentication-service';

@Component({
	selector: 'app-quiz',
	templateUrl: './quiz.component.html',
	styleUrls: ['./quiz.component.css']
})
export class QuizComponent implements OnInit {

	questions: Question[] = [];
	currentQuestion: Question = new Question();
	currentQuestionIndex: number = 0;
	quizComplete: boolean = false;
	quizScore: number = 0;
	
	model = { answer: "player choice"}

	constructor(private router: Router,private quizService: PubbyService, private authService: AuthenticationService) { }

	ngOnInit(): void {
		this.loadQuizQuestions();
	}

	loadQuizQuestions(): void {
		this.quizService.getQuizQuestions().subscribe({
			next: (questions: Question[]) => {
				this.questions = questions;
				this.currentQuestion = this.questions[this.currentQuestionIndex];
			},
			error: (error) => console.log(error)
		});
	}
	
	onAnswerGiven(givenAnswer: any){		
		console.log("givenAnswer:", givenAnswer.answer);
		this.validateAnswer(givenAnswer);
	}
		
	validateAnswer(givenAnswer: any) {
		  this.quizService.validateAnswer(givenAnswer.answer).subscribe(isCorrect => {
		    if (isCorrect) {
		      // answer is correct
		      this.quizScore++;
		    } else {
		      // answer is incorrect
		      this.quizScore = this.quizScore;
		    }
		  });
		}



	onNextQuestion(): void {
		this.nextQuestion();
	}

	nextQuestion() {
		this.currentQuestionIndex++;
		if (this.currentQuestionIndex < this.questions.length) {
			this.currentQuestion = this.questions[this.currentQuestionIndex];
		} else {
			this.quizComplete = true;
		}
	}
	
	getQuizScore(): number {
		return this.quizScore;
	}

	getQuizProgress(): number {
		return ((this.currentQuestionIndex + 1) / this.questions.length) * 100;
	}

	restartQuiz(): void {
		this.currentQuestionIndex = 0;
		this.quizScore = 0;
		this.quizComplete = false;
		this.loadQuizQuestions();
	}
	
	quit() {
	 	this.authService.logout().subscribe(() => {
	    console.log('User has been logged out');
		this.router.navigate(['/auth']);
	  });
	}
}
	