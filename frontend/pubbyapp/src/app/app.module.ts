import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { DataTablesModule } from 'angular-datatables';

import { AppComponent } from './app.component';
import { QuizSetupFormComponent } from "./component/quiz-setup-form/quiz-setup-form.component"
import { PubbyService } from 'src/app/service/pubby.service';
import { AuthenticationService } from 'src/app/service/authentication-service';
import { QuestionCardComponent } from './component/question-card/question-card.component';
import { QuizComponent } from '../app/component/quiz/quiz.component';
import { AuthCardComponent } from './component/auth-card/auth-card.component';
import { UserAuthenticationComponent } from './component/user-authentication/user-authentication.component';

@NgModule({
	declarations: [
		AppComponent,
		QuizSetupFormComponent,
		QuestionCardComponent,
		QuizComponent,
  		AuthCardComponent,
  		UserAuthenticationComponent
	],
	imports: [
		BrowserModule,
		AppRoutingModule,
		HttpClientModule,
		ReactiveFormsModule,
		DataTablesModule,
		FormsModule
	],
	providers: [PubbyService, AuthenticationService],
	bootstrap: [AppComponent],
	exports: [QuizSetupFormComponent]
})
export class AppModule { }
