import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Question } from '../../model/question';
import { PubbyService } from '../../service/pubby.service';

@Component({
	selector: 'app-question-list',
	templateUrl: './question-list.component.html',
	styleUrls: ['./question-list.component.css']
})
export class QuestionListComponent implements OnInit, OnDestroy {


	quizQuestions?: Question[];
	subscription: Subscription = new Subscription;

	constructor(private router: Router, private pubbyService: PubbyService) {
	}
	ngOnInit(): void {
		this.retrieveQuestions();
		//this.retrieve10Questions();
	}
	retrieveQuestions() {
		this.subscription = this.pubbyService.getQuizQuestions()
			.subscribe(
				result => {
					console.log(result);
					this.quizQuestions = result;
				})
	}
	retrieve10Questions() {
		this.pubbyService.get10Questions()
			.subscribe(
				result => {
					console.log(result);
					this.quizQuestions = result;
				})
	}

	refreshList(): void {
		this.subscription = this.pubbyService.refreshQuizQuestions()
			.subscribe(
				result => {
					console.log(result);
					this.quizQuestions = result;
				})
	}

	removeAllQuestions(): void {
		this.pubbyService.deleteAll()
			.subscribe({
				next: (res) => {
					console.log(res);
					this.gotoSetupForm();
				},
				error: (e) => console.error(e)
			});
	}
	gotoSetupForm() {
		this.router.navigate(['/setupquiz']);
	}
	ngOnDestroy() {
		this.subscription.unsubscribe();
	}

}