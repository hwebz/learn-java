import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterDTO } from '../dtos/user/register.dto';
import { LoginDTO } from '../dtos/user/login.dto';
import { UserResponse } from '../responses/user/user.reponse';
import TokenService from './token.service';
import CartService from './cart.service';
import { UpdateUserDTO } from '../dtos/user/update-user.dto';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = "/api/users";
  private headers: HttpHeaders;
  
  constructor(
    private http: HttpClient,
    private tokenService: TokenService,
    private cartService: CartService
  ) {
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

  updateUserDetails(updateData: UpdateUserDTO): Observable<any> {
    const endpoint = this.apiUrl + '/update';
    return this.http.put(endpoint, updateData, {
      headers: this.headers
    })
  }

  getUserDetails(): Observable<any> {
    const endpoint = this.apiUrl + '/details';
    return this.http.get(endpoint, {
      headers: this.headers
    })
  }

  saveUserToLocalStorage(user: UserResponse) {
    try {
      const userResponseJSON = JSON.stringify(user);
      localStorage.setItem('user', userResponseJSON);
    } catch (error: any) {
      console.error("Error saving user response to local storage", error);
    }
  }

  getUserFromLocalStorage(): UserResponse | null {
    const userResponseJSON = localStorage.getItem('user');
    if (userResponseJSON) {
      return JSON.parse(userResponseJSON);
    }
    return null;
  }

  logout(): void {
    this.cartService.clearCart();
    this.tokenService.removeToken();
    localStorage.removeItem('user');
  }
}
