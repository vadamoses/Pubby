import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { QuizOptions } from '../../model/quiz-options';
import { PubbyService } from '../../service/pubby.service';

@Component({
	selector: 'app-quiz-setup-form',
	templateUrl: './quiz-setup-form.component.html',
	styleUrls: ['./quiz-setup-form.component.css']
})
export class QuizSetupFormComponent implements OnInit {
	quizSize: number = 0;

	constructor(private router: Router, private quizService: PubbyService) { }

	ngOnInit(): void {
	}

	setupQuizWithOptions() {
		if (this.quizSize) {
			const quizOptions: QuizOptions = { quizSize: this.quizSize };
			this.quizService.setupQuiz(quizOptions)
			.pipe(first()).subscribe({
				next: () => {
					this.startQuiz();
				},
				error: (error) => console.log(error)
			});
			
		}
	}

	startQuiz() {
		this.quizService.getQuizQuestions()
		.pipe(first()).subscribe({
			next: (questions) => {
				this.router.navigate(['/quiz'], { state: { questions } });
			},
			error: (error) => console.log(error)
		});
	}
}
