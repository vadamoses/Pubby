import { Component, OnInit } from '@angular/core';
import { Question } from '../../model/question';
import { PubbyService } from '../../service/pubby.service';

@Component({
  selector: 'app-question-list',
  templateUrl: './question-list.component.html',
  styleUrls: ['./question-list.component.css']
})
export class QuestionListComponent implements OnInit {


  questions: Question[] = [];

  constructor(private _pubbyService: PubbyService) {
  }

  ngOnInit() {
    this._pubbyService.getQuizQuestions().subscribe(data => {
      this.questions = data;
    });
  }
}