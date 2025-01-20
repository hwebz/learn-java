package com.hwebz.dreamshops.controllers;

import com.hwebz.dreamshops.exception.AlreadyExistsException;
import com.hwebz.dreamshops.exception.ResourceNotFoundException;
import com.hwebz.dreamshops.models.Product;
import com.hwebz.dreamshops.requests.AddProductRequest;
import com.hwebz.dreamshops.requests.ProductUpdateRequest;
import com.hwebz.dreamshops.responses.ApiResponse;
import com.hwebz.dreamshops.responses.ProductResponse;
import com.hwebz.dreamshops.services.product.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;
    private final ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<ApiResponse> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String category) {
        try {
            ApiResponse response = new ApiResponse(true, "Success", null);
            List<Product> products;
            boolean emptyBrand = brand == null || brand.isEmpty() || brand.isBlank();
            if (name != null && !name.isEmpty() && !name.isBlank()) {
                if (emptyBrand) {
                    products = productService.getProductsByName(name);
                } else {
                    products = productService.getProductsByBrandAndName(brand, name);
                }
            } else if (category != null && !category.isEmpty() && !category.isBlank()) {
                if (emptyBrand) {
                    products = productService.getProductsByCategory(category);
                } else {
                    products = productService.getProductsByCategoryAndBrand(category, brand);
                }
            } else if (!emptyBrand) {
                products = productService.getProductsByBrand(brand);
            } else {
                products = productService.getAllProducts();
            }
            List<ProductResponse> productsResponse = productService.getConvertedProducts(products);
            response.setData(productsResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error: " + e.getMessage(), null));
        }
    }

    @GetMapping("{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            Product theProduct = productService.getProductById(productId);
            ProductResponse productResponse = productService.convertToResponse(theProduct);
            return ResponseEntity.ok(new ApiResponse(true, "Success", productResponse));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error: " + e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody @Valid AddProductRequest product) {
        try {
            Product theProduct = productService.addProduct(product);
            ProductResponse productResponse = productService.convertToResponse(theProduct);
            return ResponseEntity.ok(new ApiResponse(true, "Success", productResponse));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error: " + e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long productId, @RequestBody ProductUpdateRequest product) {
        try {
            Product theProduct = productService.updateProduct(product, productId);
            ProductResponse productResponse = productService.convertToResponse(theProduct);
            return ResponseEntity.ok(new ApiResponse(true, "Success", productResponse));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error: " + e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse(true, "Success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error: " + e.getMessage(), null));
        }
    }
}
