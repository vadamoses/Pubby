import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { QuestionListComponent } from './component/question-list/question-list.component';
import { UserFormComponent } from './component/user-form/user-form.component';
import { QuestionFormComponent } from './component/question-form/question-form.component';

@NgModule({
  declarations: [
    AppComponent,
    QuestionListComponent,
    UserFormComponent,
    QuestionFormComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
