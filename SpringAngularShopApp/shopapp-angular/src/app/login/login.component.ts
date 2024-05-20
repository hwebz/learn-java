import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { LoginDTO } from '../dtos/user/login.dto';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  @ViewChild("loginForm") loginForm!: NgForm;
  phoneNumber: string = '';
  password: string = '';
  rememberMe: boolean = false;

  constructor(private router: Router, private userService: UserService) {}

  async login() {
    const loginData = new LoginDTO({
      phone_number: this.phoneNumber,
      password: this.password
    })

    const isValid = await loginData.validate()
    if (!isValid) {
      return;
    }

    this.userService.login(loginData)
    .subscribe({
      next: (response: any) => {
        alert(response);
        this.router.navigate(['/home'])
      },
      error: (e: any) => {
        alert(e.error)
      }
    });
  }
}
