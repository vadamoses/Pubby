import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PubbyService } from '../../service/pubby.service';
import { Quiz } from '../../model/quiz';

@Component({
	selector: 'app-quiz-setup-form',
	templateUrl: './quiz-setup-form.component.html',
	styleUrls: ['./quiz-setup-form.component.css']
})
export class QuizSetupFormComponent implements OnInit {

	newQuiz: Quiz = new Quiz;
	constructor(
		private router: Router,
		private pubbyService: PubbyService) { }
	ngOnInit() {
	}

	setupForm = new FormGroup({
		quizSize: new FormControl('', [Validators.required, Validators.minLength(1)])
	})

	// convenient getter for easy access to form fields
	get f() { return this.setupForm.controls; }

	saveQuiz() {
		this.newQuiz = new Quiz();
		this.newQuiz.quizSize = this.f.quizSize.value;
		this.setupQuiz();
	}

	setupQuiz() {
		this.pubbyService.setupQuiz(this.newQuiz).subscribe(error => console.log(error)); 
		this.gotoQuestionsList();
		this.resetForm();
	}

	gotoQuestionsList() {
		this.router.navigate(['/questions']);
	}

	resetForm() {
		this.setupForm.reset();
	}
}
