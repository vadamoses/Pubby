import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { QuizComponent } from '../app/component/quiz/quiz.component';
import { QuestionCardComponent } from './component/question-card/question-card.component';
import { QuizSetupFormComponent } from "./component/quiz-setup-form/quiz-setup-form.component";
import { UserAuthenticationComponent } from './component/user-authentication/user-authentication.component';

const routes: Routes = [
	{ path: '', redirectTo: 'auth', pathMatch: 'full' },
	{ path: 'quiz', component: QuizComponent },
	{ path: 'setupquiz', component: QuizSetupFormComponent },
	{ path: 'question', component: QuestionCardComponent },
	{ path: 'auth', component: UserAuthenticationComponent }
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
