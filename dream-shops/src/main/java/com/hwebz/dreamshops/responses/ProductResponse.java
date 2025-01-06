package com.hwebz.dreamshops.responses;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int inventory;
    private ProductCategoryResponse category;
    private List<ProductImageResponse> images;
}
