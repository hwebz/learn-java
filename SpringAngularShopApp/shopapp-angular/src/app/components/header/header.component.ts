import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { UserResponse } from '../../responses/user/user.reponse';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent implements OnInit {
  activeNavItem: number = -1;
  userResponse?: UserResponse | null;
  
  constructor(
    private userService: UserService,
    private router: Router
  ) {}


  ngOnInit(): void {
    this.userService.getUserDetails().subscribe({
      next: (user: UserResponse) => {
        console.log(user)
        this.userResponse = user;
        this.userService.saveUserToLocalStorage(user);
      },
      complete: () => {
        console.log('fetch user completed')
      },
      error: (e: any) => {
        alert(e.error)
      }
    });
  }

  logout() {
    this.userService.logout();
    this.router.navigate(['/login']);
  }
}
