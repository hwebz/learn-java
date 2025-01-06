package com.hwebz.dreamshops.responses;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CategoryResponse {
    private Long id;
    private String name;

    private List<CategoryProductResponse> products;
}
