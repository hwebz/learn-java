import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterDTO } from '../dtos/register.dto';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = "/api/users";
  
  constructor(private http: HttpClient) {}

  register(registerData: RegisterDTO): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post('/api/users/register', registerData, {
      headers,
      // By default HttpClient response type is json, it means API returns 200 OK with string message
      // it gonna throw an error here because of Invalid JSON, you have to return ResponseEntity.ok({ message: "Register successfully." })
      // Otherwise, you can use 'text' to prevent that error
      responseType: 'text'
    });
  }
}
