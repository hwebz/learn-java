import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import ProductsResponse from "../responses/products.response";
import Product from "../models/product.model";

@Injectable({
  providedIn: 'root'
})
export default class ProductService {
  private apiUrl = "/api/products";
  private headers: HttpHeaders;

  constructor(private http: HttpClient) {
    this.headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  }

  getProducts(
    page: number,
    limit: number,
    categoryId?: number,
    keyword?: string,
  ): Observable<ProductsResponse> {
    return this.http.get(this.apiUrl, {
      headers: this.headers,
      // By default HttpClient response type is json, it means API returns 200 OK with string message
      // it gonna throw an error here because of Invalid JSON, you have to return ResponseEntity.ok({ message: "Register successfully." })
      // Otherwise, you can use 'text' to prevent that error
      // responseType: 'text'
      params: {
        page,
        limit,
        category_id: categoryId ?? '',
        keyword: keyword ?? ''
      }
    }) as Observable<ProductsResponse>;
  }

  getDetailProduct(productId: number): Observable<Product> {
    return this.http.get(`${this.apiUrl}/${productId}`, {
      headers: this.headers,
    }) as Observable<Product>;
  }
}