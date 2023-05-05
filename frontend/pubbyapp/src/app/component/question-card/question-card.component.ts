import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Question } from '../../model/question';
import { PossibleAnswer } from '../../model/possible-answer';

@Component({
	selector: 'app-question-card',
	templateUrl: './question-card.component.html',
	styleUrls: ['./question-card.component.css']
})
export class QuestionCardComponent implements OnInit {

	@Input() question: Question;
	@Output() givenAnswer  = new EventEmitter<{answer:PossibleAnswer}>();

	answerSelected = false;
	selectedAnswer: PossibleAnswer = new PossibleAnswer();

	constructor() { this.question = new Question;}

	ngOnInit(): void {}


	onSelectAnswer(answer: any): void {
	    this.answerSelected = true;
	    this.selectedAnswer = answer;
	    this.givenAnswer.emit({answer: answer});
	}
}
