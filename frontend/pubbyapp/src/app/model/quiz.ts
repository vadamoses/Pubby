import { Question } from '../model/question';
import { PossibleAnswer } from '../model/possible-answer';

export class Quiz {
    questions: Question[] = [];
	currentQuestion: Question = new Question();
	currentPossibleAnswers: PossibleAnswer[] = [];
	currentQuestionIndex: number = 0;
	givenAnswers: PossibleAnswer[] = [];
	quizComplete: boolean = false;
	quizScore: number = 0;
	timer: any;
	questionTimeLimit = 5; // seconds
	timeLeft: number = 0;
	timeExpired: boolean = false;
    totalTimeTaken: number = 0;

    constructor() {
    }
}
