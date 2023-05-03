import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { Observable, throwError } from 'rxjs';
import { environment } from '../../environments/environment';
import { AuthenticationService } from '../service/authentication-service';

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService implements HttpInterceptor {

	  constructor(private authService: AuthenticationService) { }

	  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
		    // If the request is made by the `AuthenticationService`, just return the request without modifying it
		    if (req.url.includes(`${environment.authenticationUrl}`)) {
		      return next.handle(req);
		    }
		    
		    const currentUser = this.authService.getUserValue();
		    const isLoggedIn = currentUser.token;
		
		    if (isLoggedIn) {
		      req = req.clone({
		    	withCredentials: true,
		        setHeaders: {
		          'Content-Type' : 'application/json; charset=utf-8',
		          'Accept'       : 'application/json',
		          'Authorization': `Bearer ${currentUser.token}`,
		        },
		      });
		    }
		
		    return next.handle(req)/*.pipe(
		      catchError((err) => {
		          if (err.status === 401) {
		            this.authService.logout();
		          }
		          const error = err.error.message || err.statusText;
		          return throwError(() => error);
		        })
		      )*/;
	    }
	 }
