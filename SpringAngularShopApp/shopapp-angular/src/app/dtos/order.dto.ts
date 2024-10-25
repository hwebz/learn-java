import { IsEmail, IsNotEmpty, IsNumber, IsOptional, IsString, Min, validate, ValidateNested } from 'class-validator';

export class CartItemDTO {
  @IsNumber()
  product_id: number;

  @IsNumber()
  @Min(1)
  quantity: number;

  constructor(data: any) {
    this.product_id = data.product_id;
    this.quantity = data.quantity;
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

export class OrderDTO {
  @IsNumber()
  @Min(1)
  user_id: number;

  @IsString()
  @IsNotEmpty()
  fullname: string;

  @IsEmail()
  email: string;

  @IsString()
  @IsNotEmpty()
  phone_number: string;

  @IsString()
  @IsNotEmpty()
  address: string;

  @IsString()
  @IsOptional()
  note?: string;

  @IsNumber()
  @Min(0)
  total_money: number;

  @IsString()
  @IsNotEmpty()
  payment_method: string;

  @IsString()
  @IsNotEmpty()
  shipping_method: string;

  @IsString()
  @IsOptional()
  coupon_code?: string;

  @ValidateNested({ each: true })
  cart_items?: CartItemDTO[];

  constructor(data: any) {
    this.user_id = data.user_id;
    this.fullname = data.fullname;
    this.email = data.email;
    this.phone_number = data.phone_number;
    this.address = data.address;
    this.note = data.note;
    this.total_money = data.total_money;
    this.payment_method = data.payment_method;
    this.shipping_method = data.shipping_method;
    this.coupon_code = data.coupon_code;
    this.cart_items = data.cart_items
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