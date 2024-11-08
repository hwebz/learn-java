import { Injectable } from "@angular/core";
import { OrderDTO } from "../dtos/order.dto";
import { Observable } from "rxjs";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import OrdersResponse from "../responses/orders.response";

@Injectable({
  providedIn: 'root'
})
export default class OrderService {
  private apiUrl = "/api/orders";
  private apiGetAllOrders = "/api/orders/get-orders-by-key"
  private headers: HttpHeaders;

  constructor(private http: HttpClient) {
    this.headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  }

  placeOrder(orderData: OrderDTO): Observable<any> {
    return this.http.post(this.apiUrl, orderData, {
      headers: this.headers
    }) as Observable<any>;
  }

  getOrderById(orderId: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${orderId}`) as Observable<any>;
  }

  getAllOrders(keyword: string, page: number, limit: number): Observable<OrdersResponse> {
    const params = new HttpParams()
      .set('keyword', keyword)
      .set('page', (page - 1).toString())
      .set('limit', limit.toString())

    return this.http.get<OrdersResponse>(this.apiGetAllOrders, { params })
  }

  deleteOrder(orderId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${orderId}`) as Observable<any>;
  }
}