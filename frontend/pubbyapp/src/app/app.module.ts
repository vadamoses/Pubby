import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {DataTablesModule} from 'angular-datatables';  

import { AppComponent } from './app.component';
import { QuestionListComponent } from './component/question-list/question-list.component';
import { QuestionFormComponent } from './component/question-form/question-form.component';
import { QuizSetupFormComponent } from './component/quiz-setup-form/quiz-setup-form.component';
import { PubbyService } from 'src/app/service/pubby.service';

@NgModule({
	declarations: [
		AppComponent,
		QuizSetupFormComponent,
		QuestionListComponent,
		QuestionFormComponent
	],
	imports: [
		BrowserModule,
		AppRoutingModule,
		HttpClientModule,
		ReactiveFormsModule,
		DataTablesModule,
		FormsModule
	],
	providers: [PubbyService],
	bootstrap: [AppComponent]
})
export class AppModule { }
