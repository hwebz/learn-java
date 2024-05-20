import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

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

  constructor(private http: HttpClient, private router: Router) {}

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

  register() {
    if (
      this.password != this.confirmPassword ||
      !this.overEighteen()
    ) return;

    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    this.http.post('/api/users/register', {
      fullname: this.fullName,
      phone_number: this.phoneNumber,
      password: this.password,
      confirm_password: this.confirmPassword,
      role_id: 2,
      address: this.address
    }, { headers })
    .subscribe({
      next: (response: any) => {
        debugger;
        if ([200, 201].includes(response?.status)) {
          this.router.navigate(['/login'])
        } else {

        }
      },
      complete: () => {
        debugger
      },
      error: (e: any) => {
        console.error(e);
      }
    });
  }
}
