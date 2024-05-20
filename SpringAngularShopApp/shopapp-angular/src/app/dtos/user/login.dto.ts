import { IsNotEmpty, IsPhoneNumber, IsString, validate } from "class-validator";

export class LoginDTO {
  @IsPhoneNumber('VN')
  phone_number: string;

  @IsString()
  @IsNotEmpty()
  password: string;

  constructor(data: any) {
    this.phone_number = data.phone_number;
    this.password = data.password;
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