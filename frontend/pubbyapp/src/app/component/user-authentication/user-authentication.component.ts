import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AppUser } from '../../model/app-user';
import { AuthenticationService } from '../../service/authentication-service';

@Component({
  selector: 'app-user-authentication',
  templateUrl: './user-authentication.component.html',
  styleUrls: ['./user-authentication.component.css']
})
export class UserAuthenticationComponent {

  constructor(private router: Router, private authService: AuthenticationService) { }
  
  handleLogin(user: AppUser) {
	  this.authService.loginUser(user).subscribe({
	    next: () => {
	      this.router.navigate(['/setupquiz']);
	    },
	    error: (error) => console.log(error)
	  });
	}

	handleRegister(user: AppUser) {
	  this.authService.registerUser(user).subscribe({
	    next: () => {
	      this.router.navigate(['/auth']);
	    },
	    error: (error) => console.log(error)
	  });
	}

}
