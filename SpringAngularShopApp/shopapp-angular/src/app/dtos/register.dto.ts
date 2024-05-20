import { IsDataURI, IsDate, IsNotEmpty, IsPhoneNumber, IsString } from "class-validator";

export class RegisterDTO {
  @IsString()
  fullname: string;

  @IsPhoneNumber()
  phone_number: string;

  @IsString()
  @IsNotEmpty()
  password: string;

  @IsString()
  @IsNotEmpty()
  confirm_password: string;

  @IsString()
  @IsNotEmpty()
  address: string;

  @IsDate()
  date_of_birth?: Date;

  role_id?: number;
  facebook_account_id?: number;
  google_account_id?: number;

  constructor(data: any) {
    this.fullname = data.fullName;
    this.phone_number = data.phone_number;
    this.address = data.address;
    this.password = data.password;
    this.confirm_password = data.confirm_password;
    this.role_id = 2;
    this.address = data.address;
    this.date_of_birth = data.date_of_birth;
  }
}