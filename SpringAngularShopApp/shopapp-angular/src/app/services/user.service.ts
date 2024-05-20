import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterDTO } from '../dtos/user/register.dto';
import { LoginDTO } from '../dtos/user/login.dto';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = "/api/users";
  private headers: HttpHeaders;
  
  constructor(private http: HttpClient) {
    this.headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  }

  register(registerData: RegisterDTO): Observable<any> {
    const endpoint = this.apiUrl + '/register';
    return this.http.post(endpoint, registerData, {
      headers: this.headers,
      // By default HttpClient response type is json, it means API returns 200 OK with string message
      // it gonna throw an error here because of Invalid JSON, you have to return ResponseEntity.ok({ message: "Register successfully." })
      // Otherwise, you can use 'text' to prevent that error
      // responseType: 'text'
    });
  }

  login(loginData: LoginDTO): Observable<any> {
    const endpoint = this.apiUrl + '/login';
    return this.http.post(endpoint, loginData, {
      headers: this.headers,
      // responseType: 'text'
    })
  }
}
