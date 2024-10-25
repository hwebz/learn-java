import { Component, OnInit } from '@angular/core';
import Product, { CartItem } from '../../models/product.model';
import ProductService from '../../services/product.service';
import CartService from '../../services/cart.service';
import OrderService from '../../services/order.service';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-order-confirmation',
  templateUrl: './order-confirmation.component.html',
  styleUrl: './order-confirmation.component.scss'
})
export class OrderConfirmationComponent implements OnInit {
  orderResponse: any;

  constructor(
    private orderService: OrderService,
    private route: ActivatedRoute,
    private location: Location
  ) {}

  ngOnInit(): void {
      this.getOrderDetails();
  }

  getOrderDetails(): void {
    const orderId = this.route.snapshot.paramMap.get('id');
    if (!orderId) {
      this.location.back();
    }
    this.orderService.getOrderById(Number(orderId)).subscribe({
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
