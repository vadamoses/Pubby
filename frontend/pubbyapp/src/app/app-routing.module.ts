import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { QuestionListComponent } from './component/question-list/question-list.component';
import { QuestionCardComponent } from './component/question-card/question-card.component';
import { QuizSetupFormComponent } from './component/quiz-setup-form/quiz-setup-form.component';

const routes: Routes = [
	{ path: '', redirectTo: 'setupquiz', pathMatch: 'full' },
	{ path: 'questions', component: QuestionListComponent },
	{ path: 'setupquiz', component: QuizSetupFormComponent },
	{ path: 'question', component: QuestionCardComponent }
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
