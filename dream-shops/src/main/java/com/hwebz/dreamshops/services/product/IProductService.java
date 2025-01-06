package com.hwebz.dreamshops.services.product;

import com.hwebz.dreamshops.models.Product;
import com.hwebz.dreamshops.requests.AddProductRequest;
import com.hwebz.dreamshops.requests.ProductUpdateRequest;
import com.hwebz.dreamshops.responses.ProductResponse;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);

    List<ProductResponse> getConvertedProducts(List<Product> products);

    ProductResponse convertToResponse(Product product);
}
