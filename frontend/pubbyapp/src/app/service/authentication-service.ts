import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { AppUser } from '../model/app-user';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private currentUserSubject: BehaviorSubject<AppUser | null>;
  public currentUser: Observable<AppUser | null>;
  private httpOptions: { headers: HttpHeaders };
  
  constructor(private http: HttpClient) {
    const storedUser = localStorage.getItem('currentUser');
    this.currentUserSubject = new BehaviorSubject<AppUser | null>(storedUser ? JSON.parse(storedUser) : null);
    this.currentUser = this.currentUserSubject.asObservable();
    
    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
  }
  
  public registerUser(user: AppUser): Observable<AppUser> {
    return this.http.post<AppUser>(`${environment.authenticationUrl}/register`, { user }, this.httpOptions);
  }

  public loginUser(user: AppUser): Observable<AppUser> {
    return this.http.post<AppUser>(`${environment.authenticationUrl}/login`, { user }, this.httpOptions)
      .pipe(
        map(user => {
          user.authdata = window.btoa(user.username + ':' + user.password);
          localStorage.setItem('currentUser', JSON.stringify(user));
          this.currentUserSubject.next(user);
          return user;
        })
      );
  }
  
  public assignRole(userId: string, roleName: string): Observable<any> {
    return this.http.post<any>(`${environment.authenticationUrl}/assign`, { userId, roleName }, this.httpOptions);
  }
  
  public validateToken() {
	    return this.http.get<{userId: number, name: string, valid: boolean}>(`${environment.authenticationUrl}/validateToken`, this.httpOptions);
  }

  public logout(): Observable<any> {
	    // remove user from local storage to log user out
	    localStorage.removeItem('currentUser');
	    this.currentUserSubject.next(null);
	    return this.http.post(`${environment.authenticationUrl}/logout`, {}, this.httpOptions);
  }

  public getCurrentUser(): AppUser | null {
    return this.currentUserSubject.value;
  }
  
  public isUserLoggedIn(): boolean {
    return !!this.getCurrentUser();
  }
}
