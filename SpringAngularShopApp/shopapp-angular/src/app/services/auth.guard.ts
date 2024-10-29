import { inject, Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from "@angular/router";
import TokenService from "./token.service";
import { UserService } from "./user.service";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard {
  constructor(
    private tokenService: TokenService,
    private router: Router,
    private userService: UserService
  ) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const isTokenExpired = this.tokenService.isTokenExpied();
    const isUserIdValid = this.tokenService.getUserId() > 0;
    if (!isTokenExpired && isUserIdValid) {
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }

  canAdminActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const isTokenExpired = this.tokenService.isTokenExpied();
    const isUserIdValid = this.tokenService.getUserId() > 0;
    if (!isTokenExpired && isUserIdValid) {
      const isAdmin = this.userService.isAdmin();
      if (isAdmin) {
        return true
      }
    }
    this.router.navigate(['/login']);
    return false;
  }
}

export const AuthGuardFn: CanActivateFn = (next: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  return inject(AuthGuard).canActivate(next, state);
}

export const AdminGuardFn: CanActivateFn = (next: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  return inject(AuthGuard).canAdminActivate(next, state);
}