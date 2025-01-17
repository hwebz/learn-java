package com.hwebz.dreamshops.controllers;

import com.hwebz.dreamshops.dtos.OrderDTO;
import com.hwebz.dreamshops.exception.ResourceNotFoundException;
import com.hwebz.dreamshops.models.Order;
import com.hwebz.dreamshops.responses.ApiResponse;
import com.hwebz.dreamshops.services.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("{userId}")
    public ResponseEntity<ApiResponse> createOrder(@PathVariable Long userId) {
        try {
            Order order = orderService.placeOrder(userId);
            OrderDTO orderDTO = orderService.convertToDTO(order);
            return ResponseEntity.ok(new ApiResponse(true, "Order placed successfully", orderDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error: " + e.getMessage(), null));
        }
    }

    @GetMapping("{orderId}")
    public ResponseEntity<ApiResponse> getOrder(@PathVariable Long orderId) {
        try {
            Order order = orderService.getOrder(orderId);
            OrderDTO orderDTO = orderService.convertToDTO(order);
            return ResponseEntity.ok(new ApiResponse(true, "Fetch order successfully", orderDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("by-user/{userId}")
    public ResponseEntity<ApiResponse> getOrdersByUserId(@PathVariable Long userId) {
        try {
            List<Order> orders = orderService.getAllOrders(userId);
            List<OrderDTO> orderDTO = orders.stream().map(orderService::convertToDTO).toList();
            return ResponseEntity.ok(new ApiResponse(true, "Fetch orders successfully", orderDTO));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}
