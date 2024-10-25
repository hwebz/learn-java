import { Component, OnInit } from '@angular/core';
import Product, { CartItem } from '../../models/product.model';
import ProductService from '../../services/product.service';
import CartService from '../../services/cart.service';
import OrderService from '../../services/order.service';

@Component({
  selector: 'app-order-confirmation',
  templateUrl: './order-confirmation.component.html',
  styleUrl: './order-confirmation.component.scss'
})
export class OrderConfirmationComponent implements OnInit {
  orderResponse: any;

  constructor(
    private orderService: OrderService
  ) {}

  ngOnInit(): void {
      this.getOrderDetails();
  }

  getOrderDetails(): void {
    const orderId = 5;
    this.orderService.getOrderById(orderId).subscribe({
      next: (response: any) => {
        this.orderResponse = response;
        console.log('fetch order response', response)
      },
      complete: () => {
        console.log('fetch order completed')
      },
      error: (e: any) => {
        alert(e.error)
      }
    })
  }
}
