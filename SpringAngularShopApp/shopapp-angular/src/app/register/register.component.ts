import { Component } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  phoneNumber: string = '';
  password: string = '';
  confirmPassword: string = '';
  fullName: string = '';
  address: string = '';
  agreeTermsAndConditions: boolean = false;
  dateOfBirth?: Date;

  onPhoneChange() {
    console.log('test')
  }
}
