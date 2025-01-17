package com.hwebz.dreamshops.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Data
public class CartDTO {
    private Long id;
    private Set<CartItemDTO> items;
    private BigDecimal totalAmount;
}
