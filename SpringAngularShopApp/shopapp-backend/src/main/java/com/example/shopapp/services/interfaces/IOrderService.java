package com.example.shopapp.services.interfaces;

import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.responses.OrderResponse;

import java.util.List;

public interface IOrderService {
    OrderResponse createOrder(OrderDTO orderDTO) throws DataNotFoundException;
    OrderResponse getOrder(Long id) throws DataNotFoundException;
    OrderResponse updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException;
    void deleteOrder(Long id) throws DataNotFoundException;
    List<OrderResponse> getAllOrdersByUserId(Long userId);
}
