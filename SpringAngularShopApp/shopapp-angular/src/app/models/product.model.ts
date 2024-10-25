import ProductImage from "./product.image";

export default interface Product {
  name: string;
  price: number;
  priceFomatted?: string;
  thumbnail?: string;
  description?: string;
  created_at: Date;
  updated_at: Date;
  category_id: number;
  url?: string;
  product_images: ProductImage[];
  id: number;
}

export interface CartItem {
  product: Product, quantity: number
}