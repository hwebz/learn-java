import { Component, OnInit } from '@angular/core';
import Product, { CartItem } from '../../models/product.model';
import ProductService from '../../services/product.service';
import CartService from '../../services/cart.service';

@Component({
  selector: 'app-order-confirmation',
  templateUrl: './order-confirmation.component.html',
  styleUrl: './order-confirmation.component.scss'
})
export class OrderConfirmationComponent implements OnInit {
  cartItems: CartItem[] = [];
  totalAmount: number = 0;

  constructor(
    private cartService: CartService,
    private productService: ProductService,
  ) {}

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
        },
        error: (e: any) => {
          alert(e.error)
        }
      })
  }

  calculateTotalPrice(): void {
    this.totalAmount = this.cartItems.reduce((total, item) => total + item.product.price * item.quantity, 0);
  }

  applyCoupon(couponCode: string): void {
    // Implement apply coupon here
  }
}
