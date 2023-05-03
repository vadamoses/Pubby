import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { LoginRequest } from '../model/authentication/login-request';
import { RegisterRequest } from '../model/authentication/register-request';
import { AppUser } from '../model/authentication/app-user';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
	
	private userSubject: BehaviorSubject<AppUser>;
	public user: Observable<AppUser>;

  constructor(private http: HttpClient) {
    const currentUser = localStorage.getItem('currentUser');
    this.userSubject = new BehaviorSubject<AppUser>(
    	currentUser ? JSON.parse(currentUser) : null
  	);
    this.user = this.userSubject.asObservable();
  }
  
  getUserValue(): AppUser {
    return this.userSubject.value;
  }
    
  register(registerRequest: RegisterRequest): Observable<any> {
	  const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
	  return this.http.post<any>(`${environment.authenticationUrl}/register`, registerRequest, { headers: headers });
  }
  
  login(loginRequest: LoginRequest): Observable<AppUser> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post<AppUser>(`${environment.authenticationUrl}/login`, loginRequest, { headers }).pipe(
      tap(res => {
          localStorage.setItem('currentUser', JSON.stringify(res));
          this.userSubject.next(res);
          return res;
      })
    );
  }
  
  logout(): Observable<any> {
    return this.http.post<any>(`${environment.authenticationUrl}/logout`, {}).pipe(
      tap(() => {
    	  localStorage.removeItem('currentUser');
    	  this.userSubject.next(new AppUser());
      })
    );
  }

}
