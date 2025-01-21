package com.hwebz.microservices.order.dtos;

import com.hwebz.microservices.order.models.Order;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderRequest(Long id, String skuCode, BigDecimal price, Integer quantity) {
    public static Order toOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setSkuCode(orderRequest.skuCode());
        order.setPrice(orderRequest.price());
        order.setQuantity(orderRequest.quantity());
        return order;
    }
}
