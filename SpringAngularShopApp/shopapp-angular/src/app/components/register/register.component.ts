import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { catchError, tap, throwError } from 'rxjs';
import { RegisterDTO } from '../../dtos/user/register.dto';
import RegisterResponse from '../../responses/user/register.response';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  @ViewChild("registerForm") registerForm!: NgForm;
  phoneNumber: string = '';
  password: string = '';
  confirmPassword: string = '';
  fullName: string = '';
  address: string = '';
  agreeTermsAndConditions: boolean = false;
  dateOfBirth?: Date;

  constructor(private router: Router, private userService: UserService) {}

  onPhoneChange() {
    console.log('test')
  }

  overEighteen() {
    if (!this.dateOfBirth) return false;
    // Parse the date of birth
    const birthDate = new Date(this.dateOfBirth);
    
    // Get today's date
    const today = new Date();
    
    // Calculate the age in years
    let age = today.getFullYear() - birthDate.getFullYear();
    const monthDifference = today.getMonth() - birthDate.getMonth();
    
    // Adjust the age if the birth date hasn't occurred yet this year
    if (monthDifference < 0 || (monthDifference === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }
    
    // Check if the person is at least 18 years old
    return age >= 18;
  }

  async register() {
    if (
      this.password != this.confirmPassword ||
      !this.overEighteen()
    ) return;

    const registerData = new RegisterDTO({
      fullname: this.fullName,
      phone_number: this.phoneNumber,
      password: this.password,
      confirm_password: this.confirmPassword,
      address: this.address,
      date_of_birth: this.dateOfBirth,
    })

    const isValid = await registerData.validate()
    if (!isValid) {
      return;
    }

    this.userService.register(registerData)
    .subscribe({
      next: (response: RegisterResponse) => {
        if (response.success) {
          this.router.navigate(['/login'])
        }
      },
      error: (e: any) => {
        alert(e.error)
      }
    });
  }
}
