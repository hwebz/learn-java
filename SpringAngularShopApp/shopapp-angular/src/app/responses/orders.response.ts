import Order from '../models/order.model'

export default interface OrdersResponse {
  orders: Order[];
  totalPages: number;
}