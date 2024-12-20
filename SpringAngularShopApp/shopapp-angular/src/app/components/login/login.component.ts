import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { LoginDTO } from '../../dtos/user/login.dto';
import LoginResponse from '../../responses/user/login.response';
import TokenService from '../../services/token.service';
import RoleResponse from '../../responses/role.response';
import RoleService from '../../services/role.service';
import { UserResponse } from '../../responses/user/user.reponse';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {
  @ViewChild("loginForm") loginForm!: NgForm;
  phoneNumber: string = '';
  password: string = '';
  rememberMe: boolean = false;
  selectedRole?: RoleResponse;
  roles: RoleResponse[] = [];
  userResponse?: UserResponse | null;

  constructor(
    private router: Router,
    private userService: UserService,
    private tokenService: TokenService,
    private roleService: RoleService
  ) {}
  
  ngOnInit(): void {
    this.fetchRoles();
  }

  private fetchRoles() {
    this.roleService.getRoles()
    .subscribe({
      next: (response: RoleResponse[]) => {
        this.roles = response;
        this.selectedRole = response?.[1]
      },
      complete: () => {
        console.log('fetch roles completed')
      },
      error: (e: any) => {
        alert(e.error)
      }
    });
  }

  async login() {
    const loginData = new LoginDTO({
      phone_number: this.phoneNumber,
      password: this.password,
      role_id: this.selectedRole?.id
    })

    const isValid = await loginData.validate()
    if (!isValid) {
      return;
    }

    this.userService.login(loginData)
    .subscribe({
      next: (response: LoginResponse) => {
        if (response.success) {
          if (this.rememberMe) {
            this.tokenService.setToken(response.token);
          }
          
          this.userService.getUserDetails().subscribe({
            next: (user: UserResponse) => {
              this.userResponse = user;
              this.userService.saveUserToLocalStorage(user);

              if (this.userResponse?.role?.name === 'Administrator') {
                this.router.navigate(['/admin']);
              } else {
                this.router.navigate(['/']);
              }
            },
            complete: () => {
              console.log('fetch user completed')
            },
            error: (e: any) => {
              alert(e.error)
            }
          });
        }
      },
      complete: () => {
        console.log('login completed')
      },
      error: (e: any) => {
        alert(e.error)
      }
    });
  }
}
