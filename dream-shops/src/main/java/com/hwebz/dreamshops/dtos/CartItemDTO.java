package com.hwebz.dreamshops.dtos;

import com.hwebz.dreamshops.responses.ProductResponse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Data
public class CartItemDTO {
    private Long id;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductResponse product;
}
