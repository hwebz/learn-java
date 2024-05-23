import Category from "../models/category.model";

export default interface CategoriesResponse {
  categories: Category[];
  totalCategories: number;
}