package com.hwebz.microservices.product.services;

import com.hwebz.microservices.product.dtos.ProductRequest;
import com.hwebz.microservices.product.dtos.ProductResponse;
import com.hwebz.microservices.product.models.Product;
import com.hwebz.microservices.product.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();

        productRepository.save(product);
        log.info("Product created successfully: {}", product);
        return ProductResponse.fromProduct(product);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        log.info("Returning all products: {}", products);
        return products.stream().map(ProductResponse::fromProduct).toList();
    }
}
