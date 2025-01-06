package com.hwebz.dreamshops.responses;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CategoryProductResponse {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int inventory;
}
