import { PossibleAnswer } from "./possible-answer";

export class Question {
	public questionId?: number;
	public title?: string;
	public questionText?: string;
	public possibleAnswers: PossibleAnswer[] = [];


	constructor(questionId?: number, title?: string, questionText?: string, possibleAnswers: PossibleAnswer[] = []) {
		this.questionId = questionId;
		this.title = title;
		this.questionText = questionText;
		this.possibleAnswers = possibleAnswers;
	}
}
