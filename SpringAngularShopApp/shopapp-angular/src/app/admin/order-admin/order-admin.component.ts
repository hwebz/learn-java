import { Component, OnInit } from '@angular/core';
import Order from '../../models/order.model';
import OrderService from '../../services/order.service';
import OrdersResponse from '../../responses/orders.response';
import { Router } from '@angular/router';

@Component({
  selector: 'app-order-admin',
  templateUrl: './order-admin.component.html',
  styleUrl: './order-admin.component.scss'
})
export class OrderAdminComponent implements OnInit {
  orders: Order[] = []
  currentPage: number = 1
  itemsPerPage: number = 2
  pages: number[] = []
  totalPages: number = 0
  keyword: string = '';
  visiblePages: number[] = [];

  constructor(
    private orderService: OrderService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getAllOrders(this.keyword, this.currentPage, this.itemsPerPage);
  }

  getAllOrders(keyword: string, page: number, limit: number) {
    this.orderService.getAllOrders(keyword, page, limit)
    .subscribe({
      next: (response: OrdersResponse) => {
        this.orders = response.orders;
        this.totalPages = response.totalPages;
        this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
      },
      complete: () => {
        console.log('fetch orders completed')
      },
      error: (e: any) => {
        alert(e.error)
      }
    });
  }

  generateVisiblePageArray(currentPage: number, totalPages: number): number[] {
    const visiblePages = [];

    // Calculate the start and end pages for the visible range
    let startPage = Math.max(1, currentPage - 2);
    let endPage = Math.min(totalPages, currentPage + 2);

    // Adjust the start and end pages if the range is near the beginning or end
    if (endPage - startPage < 4) {
      if (currentPage < 3) {
        endPage = Math.min(5, totalPages);
      } else {
        startPage = Math.max(totalPages - 4, 1);
      }
    }

    // Generate the visible page numbers
    for (let i = startPage; i < endPage; i++) {
      visiblePages.push(i);
    }

    return visiblePages;
  }

  onPageChange(page: number) {
    this.currentPage = page;
    this.getAllOrders(this.keyword, page, this.itemsPerPage);
  }

  viewOrder(id: number) {
    console.log(`Navigating to order detail page for order ${id}`)
    this.router.navigate(['/orders', id]);
  }

  deleteOrder(id: number) {
    console.log(`Deleting order ${id}`)
    this.orderService.deleteOrder(id)
    .subscribe({
      next: () => {
        alert('Order deleted successfully');
        this.getAllOrders(this.keyword, this.currentPage, this.itemsPerPage);
      },
      complete: () => {
        console.log('delete order completed')
      },
      error: (e: any) => {
        console.log(e.error)
      }
    });
  }
}
