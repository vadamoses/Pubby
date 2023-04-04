import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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
	}

	retrieveQuestions() {
		this.subscription = this.pubbyService.getQuizQuestions()
			.subscribe(
				result => {
					console.log(result);
					this.quizQuestions = result;
				})
	}

	refreshList(): void {
		this.pubbyService.deleteAll();

		this.subscription = this.pubbyService.refreshQuizQuestions()
			.subscribe(
				result => {
					this.quizQuestions = result;
					this.redirectTo('/questions');
				})
	}

	removeAllQuestions(): void {
		this.pubbyService.deleteAll()
			.subscribe({
				next: (res) => {
					this.redirectTo('/setupquiz');
				},
				error: (e) => console.error(e)
			});
	}

	redirectTo(url: string): void {
		// When skipLocationChange true, navigates without pushing a new state into history.
		this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
			this.router.navigate([url]);
		});
	}
	ngOnDestroy() {
		if (this.subscription) {
			this.subscription.unsubscribe();
		}
	}

}