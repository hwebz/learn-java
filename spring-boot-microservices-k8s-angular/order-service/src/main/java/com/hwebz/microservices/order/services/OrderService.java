package com.hwebz.microservices.order.services;

import com.hwebz.microservices.order.dtos.OrderRequest;
import com.hwebz.microservices.order.models.Order;
import com.hwebz.microservices.order.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        // map OrderRequest to Order entity
        Order order = OrderRequest.toOrder(orderRequest);
        // save order to order repository
        orderRepository.save(order);

    }
}
