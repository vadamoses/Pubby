import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizSetupFormComponent } from './quiz-setup-form.component';

describe('QuizSetupFormComponent', () => {
  let component: QuizSetupFormComponent;
  let fixture: ComponentFixture<QuizSetupFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ QuizSetupFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(QuizSetupFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
