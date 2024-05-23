import { Component, OnInit } from '@angular/core';
import ProductService from '../../services/product.service';
import Product from '../../models/product.model';
import ProductsResponse from '../../responses/products.response';
import formatVNDCurrency from '../../utils/formatCurrency';
import CategoryService from '../../services/category.service';
import CategoriesResponse from '../../responses/category.response';
import Category from '../../models/category.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  products: Product[] = [];
  currentPage: number = 1;
  itemsPerPage: number = 12;
  pages: number[] = [];
  totalPages: number = 0;
  visiblePages: number[] = [];
  selectedCategory?: number;
  categories: Category[] = [];
  totalCategories: number = 0;
  searchText?: string;
  
  constructor(private productService: ProductService, private categoryService: CategoryService) {}

  ngOnInit(): void {
    this.getCategories();
    this.getProducts(this.currentPage, this.itemsPerPage);
  }

  onSearchProduct() {
    this.currentPage = 1;
    this.getProducts(this.currentPage, this.itemsPerPage);
  }

  getProducts(page: number, limit: number) {
    this.productService.getProducts(page, limit, this.selectedCategory, this.searchText)
    .subscribe({
      next: (response: ProductsResponse) => {
        response.products.forEach((product: Product) => {
          product.url = `/api/products/images/${product.thumbnail}`
          product.priceFomatted = formatVNDCurrency(product.price)
        });
        this.products = response.products;
        this.totalPages = response.totalPages;
        this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
      },
      complete: () => {
        console.log('fetch products completed')
      },
      error: (e: any) => {
        alert(e.error)
      }
    });
  }

  getCategories() {
    this.categoryService.getCategories()
    .subscribe({
      next: (response: CategoriesResponse) => {
        this.categories = response.categories;
        this.totalCategories = response.totalCategories;
        this.visiblePages = this.generateVisiblePageArray(this.currentPage, this.totalPages);
      },
      complete: () => {
        console.log('fetch products completed')
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
    this.getProducts(page, this.itemsPerPage);
  }
}
