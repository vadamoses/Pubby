import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { first } from 'rxjs/operators';
import { LoginRequest } from '../../model/authentication/login-request';
import { RegisterRequest } from '../../model/authentication/register-request';
import { AuthenticationService } from '../../service/authentication-service';

@Component({
  selector: 'app-user-authentication',
  templateUrl: './user-authentication.component.html',
  styleUrls: ['./user-authentication.component.css']
})
export class UserAuthenticationComponent {

	errorMessage = '';

  constructor(private router: Router, private authService: AuthenticationService) { }
  
    handleLogin(user: LoginRequest) {
	   this.authService.login(user)
	   .pipe(first()).subscribe({
	       next: () => {
	       	   this.router.navigate(['/setupquiz']);
	         },
	         error: err => {
	           this.errorMessage = err.error.message;
	         }
	       });
	}

	handleRegister(user: RegisterRequest) {
	  this.authService.register(user).subscribe({
	      next: () => {
	      	  this.router.navigate(['/auth']);
	        },
	        error: err => {
	          this.errorMessage = err.error.message;
	        }
	      });
	    }
}
