package com.hwebz.dreamshops.requests;

import com.hwebz.dreamshops.models.Category;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequest {
    @NotBlank(message = "Product name is required.")
    @Size(max = 100, message = "Product name must not exceed 100 characters.")
    private String name;

    @NotBlank(message = "Description is required.")
    @Size(max = 500, message = "Description must not exceed 500 characters.")
    private String description;

    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0.")
    private BigDecimal price;

    @Min(value = 0, message = "Inventory must be 0 or greater.")
    private int inventory;

    @NotBlank(message = "Brand is required.")
    @Size(max = 50, message = "Brand must not exceed 50 characters.")
    private String brand;

    @NotBlank(message = "Category is required.")
    @Size(max = 50, message = "Category must not exceed 50 characters.")
    private String category;
}
