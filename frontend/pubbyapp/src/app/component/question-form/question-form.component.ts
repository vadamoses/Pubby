import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PubbyService } from '../../service/pubby.service';
import { Question } from '../../model/question';

@Component({
	selector: 'app-question-form',
	templateUrl: './question-form.component.html',
	styleUrls: ['./question-form.component.css']
})
export class QuestionFormComponent {

	question: Question;

	constructor(
		private route: ActivatedRoute,
		private router: Router,
		private pubbyService: PubbyService) {
			this.question = new Question();
		}
		
	onSubmit() {
    		this.pubbyService.saveQuestion(this.question).subscribe(result => this.gotoQuestionsList());
  		}

  	gotoQuestionsList() {
    		this.router.navigate(['/questions']);
  		}
}
