package com.hwebz.dreamshops.responses;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponse {
    private Long id;
    private BigDecimal totalAmount;
    private List<CartItemResponse> items;
}
