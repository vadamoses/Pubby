import { Component, EventEmitter, Output, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginRequest } from '../../model/authentication/login-request';
import { RegisterRequest } from '../../model/authentication/register-request';

@Component({
  selector: 'app-auth-card',
  templateUrl: './auth-card.component.html',
  styleUrls: ['./auth-card.component.css']
})
export class AuthCardComponent implements OnInit {
  @Output() onLogin = new EventEmitter<LoginRequest>();
  @Output() onRegister = new EventEmitter<RegisterRequest>();

  loginMode = true;
  authForm: FormGroup;

  loading = false;
  submitted = false;
  error = '';

  constructor(private formBuilder: FormBuilder) {
    this.authForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      email: ['', Validators.required],
      roles: [['USER'], Validators.required]
    });
  }

  ngOnInit() {}

  user: RegisterRequest = {
		  username: '',
		  password: '',
		  email: '',
		  roles: []
		};

  // convenience getters for easy access to form fields
  get f() { return this.authForm.controls; }

  toggleLoginMode(mode: boolean) {
    this.loginMode = mode;
  }
  
  onSubmit() {
    this.submitted = true;
    if (this.loginMode) {
      this.onLoginSubmit();
    } else {
      this.onRegisterSubmit();
    }
  }

  onLoginSubmit() {
    this.user.username = this.f['username'].value;
    this.user.password = this.f['password'].value;
    this.onLogin.emit(this.user);
  }
  
  
  onRegisterSubmit() {
	  this.submitted = true;

	  // Check if the form is valid
	  if (this.authForm.invalid) {
	    return;
	  }

	  this.user.username = this.f['username'].value;
	  this.user.password = this.f['password'].value;
	  this.user.email = this.f['email'].value;
	  this.user.roles.push(this.f['roles'].value);
	  this.onRegister.emit(this.user);
	  this.loginMode = true;

	  // Clear the form
	  this.authForm.reset();
	}
  
  

}
