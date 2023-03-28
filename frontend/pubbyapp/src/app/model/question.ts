export class Question {
	private _qId!: number;

	private _qContext!: string;

	private _qAnswers!: string[];

	private _qText!: string;

	constructor() {

	}
	public set qId(value: number) {
		this._qId = value;
	}
	public get qId(): number {
		return this._qId;
	}
	public set qContext(value: string) {
		this._qContext = value;
	}
	public get qContext(): string {
		return this._qContext;
	}
	public set qAnswers(value: string[]) {
		this._qAnswers = value;
	}
	public get qAnswers(): string[] {
		return this._qAnswers;
	}
	public set qText(value: string) {
		this._qText = value;
	}
	public get qText(): string {
		return this._qText;
	}

}
