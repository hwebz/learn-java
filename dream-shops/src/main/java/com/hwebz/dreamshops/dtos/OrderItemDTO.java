package com.hwebz.dreamshops.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Data
public class OrderItemDTO {
    private Long productId;
    private String productName;
    private String productBrand;
    private Integer quantity;
    private BigDecimal price;
}
