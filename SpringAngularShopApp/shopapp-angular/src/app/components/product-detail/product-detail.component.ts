import { Component, OnInit } from '@angular/core';
import ProductService from '../../services/product.service';
import Product from '../../models/product.model';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.scss'
})
export class ProductDetailComponent implements OnInit {
  product!: Product;
  currentImageIndex: number = 0;

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    const fakeProductId = 5793;
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
