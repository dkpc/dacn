import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router,
  NavigationExtras,
} from '@angular/router';
import { AuthService } from './auth.service';
@Injectable()
export class AuthGuard implements CanActivate {
  postIdUrl: string;
  constructor(private router: Router, private authService: AuthService) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (this.authService.isLoggin()) {
      return true;
    } else {
      // this.router.navigate(["/sessions/signin"], {
      //   queryParams: {
      //     return: state.url
      //   }
      // });
      // return false;
      // let navigationExtras: NavigationExtras = {
      //   queryParams: { 'returnUrl': state.url },
      //   fragment: 'anchor'
      // };
      this.router.navigate([this.postIdUrl]);
      return false;
    }
  }
}
