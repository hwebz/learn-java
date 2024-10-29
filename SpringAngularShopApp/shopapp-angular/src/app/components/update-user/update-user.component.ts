import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { UpdateUserDTO } from '../../dtos/user/update-user.dto';
import { UserService } from '../../services/user.service';
import { UserResponse } from '../../responses/user/user.reponse';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrl: './update-user.component.scss'
})
export class UpdateUserComponent implements OnInit {
  userForm: FormGroup;
  userResponse?: UserResponse | null;
  userData: UpdateUserDTO = new UpdateUserDTO({
    fullname: '',
    phone_number: '',
    address: '',
    password: '',
    date_of_birth: null,
    facebook_account_id: 0,
    google_account_id: 0
  })

  constructor(
    private fb: FormBuilder,
    private userService: UserService
  ) {
    this.userForm = this.fb.group({
      fullname: [this.userData.fullname, Validators.required],
      phone_number: [this.userData.phone_number, [Validators.required, Validators.pattern('^0[0-9]{9}$')]],
      address: [this.userData.address, [Validators.required, Validators.minLength(5)]],
      password: [this.userData.password, [Validators.minLength(6)]],
      date_of_birth: [this.userData.date_of_birth, [Validators.required, this.pastDateValidator]],
      facebook_account_id: [this.userData.facebook_account_id, [Validators.pattern('^[0-9]+$')]],
      google_account_id: [this.userData.google_account_id, [Validators.pattern('^[0-9]+$')]],
    })
  }

  ngOnInit(): void {
      this.userResponse = this.userService.getUserFromLocalStorage();
      console.log(this.userResponse?.date_of_birth ? new Date(this.userResponse?.date_of_birth): null)
      this.userForm.patchValue({
        fullname: this.userResponse?.fullname || '',
        phone_number: this.userResponse?.phone_number || '',
        address: this.userResponse?.address || '',
        date_of_birth: this.userResponse?.date_of_birth ? new Date(this.userResponse?.date_of_birth).toISOString().split('T')[0] : null,
        facebook_account_id: this.userResponse?.facebook_account_id,
        google_account_id: this.userResponse?.google_account_id,
      })
  }

  pastDateValidator(control: AbstractControl): { [key: string]: boolean } | null {
    const date = new Date(control.value);
    if (isNaN(date.getTime())) {
      return { 'invalidDate': true };
    }
    if (date >= new Date()) {
      return { 'futureDate': true };
    }
    return null;
  }

  get isNameInvalid() {
    return this.userForm.get('fullname')!.invalid && (this.userForm.get('fullname')!.touched || this.userForm.get('fullname')!.dirty);
  }

  get isPhoneInvalid() {
    return this.userForm.get('phone_number')!.invalid && (this.userForm.get('phone_number')!.touched || this.userForm.get('phone_number')!.dirty);
  }

  get isAddressInvalid() {
    return this.userForm.get('address')!.invalid && (this.userForm.get('address')!.touched || this.userForm.get('address')!.dirty);
  }

  get isPasswordInvalid() {
    return this.userForm.get('password')!.invalid && (this.userForm.get('password')!.touched || this.userForm.get('password')!.dirty);
  }

  get isDateInvalid() {
    return this.userForm.get('date_of_birth')!.invalid && (this.userForm.get('date_of_birth')!.touched || this.userForm.get('date_of_birth')!.dirty);
  }

  get isDateFuture() {
    return this.userForm.get('date_of_birth')!.hasError('futureDate') && this.userForm.get('date_of_birth')!.touched;
  }

  get isDateInvalidDate() {
    return this.userForm.get('date_of_birth')!.hasError('invalidDate') && this.userForm.get('date_of_birth')!.touched;
  }

  get isFacebookInvalid() {
    return this.userForm.get('facebook_account_id')!.invalid && (this.userForm.get('facebook_account_id')!.touched || this.userForm.get('facebook_account_id')!.dirty);
  }

  get isGoogleInvalid() {
    return this.userForm.controls['google_account_id']!.invalid && (this.userForm.get('google_account_id')!.touched || this.userForm.get('google_account_id')!.dirty);
  }

  onSubmit() {
    if (this.userForm.invalid) {
      // Trigger validation messages for all fields
      this.userForm.markAllAsTouched();
      return;
    }

    this.userData = {
      ...this.userData,
      ...this.userForm.value
    }

    this.userService.updateUserDetails(this.userData).subscribe({
      next: (response: UserResponse) => {
        this.userResponse = response;
        this.userService.saveUserToLocalStorage(response);
      },
      complete: () => {
        console.log('order placed')
      },
      error: (e: any) => {
        alert(e.error)
      }
    });
  }
}
