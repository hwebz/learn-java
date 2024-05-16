package com.example.shopapp.services.interfaces;

import com.example.shopapp.dtos.OrderDetailDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.OrderDetail;
import com.example.shopapp.responses.OrderDetailResponse;

import java.util.List;

public interface IOrderDetailService {
    OrderDetailResponse createOrderDetail(OrderDetailDTO newOrderDetailDTO) throws DataNotFoundException;
    OrderDetailResponse getOrderDetail(Long id) throws DataNotFoundException;
    OrderDetailResponse updateOrderDetail(Long id, OrderDetailDTO newOrderDetailDTO) throws DataNotFoundException;
    void deleteOrderDetail(Long id) throws DataNotFoundException;
    List<OrderDetailResponse> getOrderDetails(Long orderId);
}
