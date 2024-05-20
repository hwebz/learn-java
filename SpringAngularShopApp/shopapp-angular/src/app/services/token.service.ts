import { Injectable } from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export default class TokenService {
  private readonly TOKEN_KEY = 'access_token';

  constructor() {}

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  setToken(token: string): void {
    return localStorage.setItem(this.TOKEN_KEY, token);
  }

  removeToken(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }
}