import { Component, EventEmitter, Output, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AppUser } from '../../model/app-user';

@Component({
  selector: 'app-auth-card',
  templateUrl: './auth-card.component.html',
  styleUrls: ['./auth-card.component.css']
})
export class AuthCardComponent implements OnInit {
  @Output() onLogin = new EventEmitter<AppUser>();
  @Output() onRegister = new EventEmitter<AppUser>();

  loginMode = true;
  loginForm: FormGroup = this.formBuilder.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
	roles: [[''], Validators.required]
	});


  loading = false;
  submitted = false;
  error = '';

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      roles: ['CUSTOMER', Validators.required]
    });
  }

  user: AppUser = {
    id: 0,
    username: '',
    password: '',
    roles: []
  };

  // convenience getters for easy access to form fields
  get f() { return this.loginForm.controls; }

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


  onRegisterSubmit() {
	  this.user.username = this.f['username'].value;
	  this.user.password = this.f['password'].value;
	  this.user.roles = this.f['roles'].value;
	  this.onRegister.emit(this.user);
	}

  onLoginSubmit() {
    this.user.username = this.f['username'].value;
    this.user.password = this.f['password'].value;
    this.onLogin.emit(this.user);
  }
}
