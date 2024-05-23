import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export default class ProductService {
  private apiUrl = "/api/products";
  private headers: HttpHeaders;

  constructor(private http: HttpClient) {
    this.headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  }

  getProducts(page: number, limit: number): Observable<any> {
    return this.http.get(this.apiUrl, {
      headers: this.headers,
      // By default HttpClient response type is json, it means API returns 200 OK with string message
      // it gonna throw an error here because of Invalid JSON, you have to return ResponseEntity.ok({ message: "Register successfully." })
      // Otherwise, you can use 'text' to prevent that error
      // responseType: 'text'
      params: {
        page,
        limit
      }
    });
  }
}