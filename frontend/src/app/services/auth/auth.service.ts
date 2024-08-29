import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { UserStorageService } from '../storage/user-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private http: HttpClient,
    private userStorageService: UserStorageService
  ) { }

  register(signupRequest: any): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/sign-up`, signupRequest);
  }

  login(username: string, password: string): Observable<boolean> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json');
    const body = JSON.stringify({ username, password });
    return this.http.post(`${environment.apiUrl}/authenticate`, body, { headers, observe: 'response' })
      .pipe(
        map((response) => {
          const token = response.headers.get('Authorization')?.substring(7);
          const user = response.body;
          if (token && user) {
            this.userStorageService.saveToken(token);
            this.userStorageService.saveUser(user);
            return true;
          }
          return false;
        })
      );
  }
}
