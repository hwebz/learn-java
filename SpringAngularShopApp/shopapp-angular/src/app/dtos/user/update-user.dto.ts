import { IsDate, IsPhoneNumber, IsString, validate } from "class-validator";

export class UpdateUserDTO {
  @IsString()
  fullname?: string;

  @IsPhoneNumber('VN')
  phone_number?: string;

  @IsString()
  password?: string;

  @IsString()
  address?: string;

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
    this.role_id = 2;
    this.address = data.address;
    this.date_of_birth = data.date_of_birth;
    this.facebook_account_id = data.facebook_account_id;
    this.google_account_id = data.google_account_id;
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