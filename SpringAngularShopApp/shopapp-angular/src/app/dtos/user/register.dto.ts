import { IsDataURI, IsDate, IsNotEmpty, IsPhoneNumber, IsString, validate } from "class-validator";

export class RegisterDTO {
  @IsString()
  fullname: string;

  @IsPhoneNumber('VN')
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

  async validate() {
    try {
      const errors = await validate(this);
      if (errors.length > 0) {
        console.log('validation failed. errors: ', errors);
        return false;
      }
      console.log('validation succeed');
      return true;
    } catch (error) {
      console.log('validation failed. error: ', error);
      return false;
    }
  }
}