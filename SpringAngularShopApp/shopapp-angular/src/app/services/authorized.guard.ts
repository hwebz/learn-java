import { inject, Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from "@angular/router";
import TokenService from "./token.service";

@Injectable({
  providedIn: 'root'
})
export class AuthorizedGuard {
  constructor(
    private tokenService: TokenService,
    private router: Router
  ) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const isTokenExpired = this.tokenService.isTokenExpied();
    const isUserIdValid = this.tokenService.getUserId() > 0;
    if (!isTokenExpired && isUserIdValid) {
      this.router.navigate(['/']);
      return true;
    }
    return true;
  }
}

export const AuthorizedGuardFn: CanActivateFn = (next: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  return inject(AuthorizedGuard).canActivate(next, state);
}