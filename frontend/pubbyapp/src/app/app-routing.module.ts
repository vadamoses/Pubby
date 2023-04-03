import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { QuestionListComponent } from './component/question-list/question-list.component';
import { QuestionFormComponent } from './component/question-form/question-form.component';
import { QuizSetupFormComponent } from './component/quiz-setup-form/quiz-setup-form.component';

const routes: Routes = [
	{ path: '', redirectTo: 'setupquiz', pathMatch: 'full' },
	{ path: 'questions', component: QuestionListComponent },
	{ path: 'setupquiz', component: QuizSetupFormComponent },
	{ path: 'addquestion', component: QuestionFormComponent }
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
