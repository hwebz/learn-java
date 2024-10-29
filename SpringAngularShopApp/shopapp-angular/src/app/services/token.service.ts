import { Injectable } from "@angular/core";
import { JwtHelperService } from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export default class TokenService {
  private readonly TOKEN_KEY = 'access_token';
  private jwtHelperService = new JwtHelperService();
  constructor() {}

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  setToken(token: string): void {
    return localStorage.setItem(this.TOKEN_KEY, token);
  }

  getUserId(): number {
    const token = this.getToken() ?? '';
    if (!token) return 0;
    const userObject = this.jwtHelperService.decodeToken(token);
    return 'userId' in userObject ? userObject['userId'] : 0;
  }

  removeToken(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }

  isTokenExpied(): boolean {
    return this.jwtHelperService.isTokenExpired(this.getToken() ?? '');
  }
}