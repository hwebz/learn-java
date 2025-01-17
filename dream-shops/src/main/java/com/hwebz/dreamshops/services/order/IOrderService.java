package com.hwebz.dreamshops.services.order;

import com.hwebz.dreamshops.dtos.OrderDTO;
import com.hwebz.dreamshops.models.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long orderId);
    List<Order> getAllOrders(Long userId);
    OrderDTO convertToDTO(Order order);
}
