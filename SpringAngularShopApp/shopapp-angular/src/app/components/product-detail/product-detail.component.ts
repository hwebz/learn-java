import { Component, OnInit } from '@angular/core';
import ProductService from '../../services/product.service';
import Product from '../../models/product.model';
import CartService from '../../services/cart.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.scss'
})
export class ProductDetailComponent implements OnInit {
  product!: Product;
  currentImageIndex: number = 0;
  quantity: number = 1;

  constructor(
    private productService: ProductService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    const fakeProductId = 5793;
    this.cartService.clearCart();
    this.productService.getDetailProduct(fakeProductId)
    .subscribe({
      next: (product: any) => {
        this.product = product;
      },
      complete: () => {
        console.log('fetch product completed')
      },
      error: (e: any) => {
        alert(e.error)
      }
    });
  }

  addToCart(): void {
    if (this.product) {
      this.cartService.addToCart(this.product.id, this.quantity)
    } else {
      console.error("Product is not available")
    }
  }

  increaseQuantity(): void {
    this.quantity++;
  }

  updateQuantity(event: any): void {
    const newQuantity = parseInt(event.target.value, 10);
    if (newQuantity > 0) {
      this.quantity = newQuantity;
    } else {
      this.quantity = 1;
    }
  }

  decreaseQuantity(): void {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  thumbnailClick(index: number): void {
    this.currentImageIndex = index;
  }

  previousImage(): void {
    if (this.currentImageIndex > 0) {
      this.currentImageIndex--;
    } else {
      this.currentImageIndex = this.product?.product_images?.length - 1;
    }
  }

  nextImage(): void {
    if (this.currentImageIndex < this.product?.product_images?.length - 1) {
      this.currentImageIndex++;
    } else {
      this.currentImageIndex = 0;
    }
  }
}
