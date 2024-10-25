package com.example.shopapp.services.interfaces;

import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.exceptions.ProductImageExceededException;
import com.example.shopapp.models.Product;
import com.example.shopapp.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    public Product createProduct(ProductDTO productDTO, List<String> imageUrls) throws DataNotFoundException, ProductImageExceededException;
    Product getProductById(Long id) throws DataNotFoundException;
    Page<ProductResponse> getAllProducts(String keyword, Long categoryId, PageRequest pageRequest);
    Product updateProduct(Long id, ProductDTO productDTO) throws DataNotFoundException;
    void deleteProduct(Long id);
    boolean existsByName(String name);
    List<ProductResponse> findProductsByIds(List<Long> productIds);
}
