import Product from '../models/product.model'

export default interface ProductsResponse {
  products: Product[];
  totalPages: number;
}