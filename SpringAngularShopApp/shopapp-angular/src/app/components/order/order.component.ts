import { Component } from '@angular/core';
import CartService from '../../services/cart.service';
import ProductService from '../../services/product.service';
import OrderService from '../../services/order.service';
import Product, { CartItem } from '../../models/product.model';
import { CartItemDTO, OrderDTO } from '../../dtos/order.dto';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrl: './order.component.scss'
})
export class OrderComponent {
  orderForm: FormGroup;
  cartItems: CartItem[] = [];
  couponCode: string = '';
  totalAmount: number = 0;
  orderData: OrderDTO = new OrderDTO({
    user_id: 4, // Should login with user_id = 4 and phone_number = 0987162542
    fullname: 'Ha Manh Do',
    email: 'ha@gmail.com',
    phone_number: '0987654321',
    address: '123 Main Street',
    note: '',
    total_money: 0,
    payment_method: 'cod',
    shipping_method: 'express',
    coupon_code: '',
    cart_items: []
  });

  constructor(
    private cartService: CartService,
    private productService: ProductService,
    private orderService: OrderService,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.orderForm = this.fb.group({
      fullname: [this.orderData.fullname, Validators.required],
      email: [this.orderData.email, Validators.email],
      phone_number: [this.orderData.phone_number, [Validators.required, Validators.pattern('^0[0-9]{9}$')]],
      address: [this.orderData.address, [Validators.required, Validators.minLength(5)]],
      note: [this.orderData.note],
      payment_method: [this.orderData.payment_method],
      shipping_method: [this.orderData.shipping_method],
      coupon_code: [this.orderData.coupon_code, [Validators.pattern('^[a-zA-Z0-9]{5,10}$')]],
    })
  }

  ngOnInit(): void {
      const cart = this.cartService.getCart();
      const productIds = Array.from(cart.keys());

      this.productService.getProductsByIds(productIds).subscribe({
        next: (products: Product[]) => {
          this.cartItems = products.map((product: Product) => ({
            product,
            quantity: cart.get(product.id),
          }) as CartItem);
        },
        complete: () => {
          console.log('fetch cart completed')
          this.calculateTotalPrice();
          this.orderData.cart_items = this.cartItems.map((item) => new CartItemDTO({
            product_id: item.product.id,
            quantity: item.quantity
          }));
        },
        error: (e: any) => {
          alert(e.error)
        }
      })
  }

  calculateTotalPrice(): void {
    this.totalAmount = this.cartItems.reduce((total, item) => total + item.product.price * item.quantity, 0);
  }

  applyCoupon(): void {
    // Implement apply coupon here
  }

  placeOrder(): void {
    if (this.orderForm.valid) {
      this.orderData = {
        ...this.orderData,
        ...this.orderForm.value,
        total_money: this.totalAmount
      }
      this.orderService.placeOrder(this.orderData).subscribe({
        next: (response: any) => {
          this.router.navigate(['/orders', response.id]);
        },
        complete: () => {
          console.log('order placed')
        },
        error: (e: any) => {
          alert(e.error)
        }
      })
    } else {
      alert('Please fill in all required fields');
    }
  }
}
