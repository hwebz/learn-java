import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { UserResponse } from '../../responses/user/user.reponse';

@Component({
  selector: 'app-admin-header',
  templateUrl: './admin-header.component.html',
  styleUrl: './admin-header.component.scss'
})
export class AdminHeaderComponent implements OnInit {
  activeNavItem: number = -1;
  userResponse?: UserResponse | null;
  
  constructor(
    private userService: UserService,
    private router: Router
  ) {}


  ngOnInit(): void {
    this.userResponse = this.userService.getUserFromLocalStorage();
  }

  logout() {
    this.userService.logout();
    this.router.navigate(['/login']);
  }
}
