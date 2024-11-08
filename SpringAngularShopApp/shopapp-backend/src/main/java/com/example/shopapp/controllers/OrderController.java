package com.example.shopapp.controllers;

import com.example.shopapp.dtos.OrderDTO;
import com.example.shopapp.dtos.ProductDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.repositories.ProductRepository;
import com.example.shopapp.responses.OrderListResponse;
import com.example.shopapp.responses.OrderResponse;
import com.example.shopapp.responses.ProductResponse;
import com.example.shopapp.services.interfaces.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;
    private final ProductRepository productRepository;

    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Long userId) {
        try {
            List<OrderResponse> orders = orderService.getAllOrdersByUserId(userId);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@Valid @PathVariable("id") Long id) {
        try {
            OrderResponse existingOrder = orderService.getOrder(id);
            return ResponseEntity.ok(existingOrder);
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(
            @Valid @PathVariable("id") Long id,
            @Valid @RequestBody OrderDTO orderDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            OrderResponse orderResponse = orderService.updateOrder(id, orderDTO);
            return ResponseEntity.ok(orderResponse);
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> createOrder(
            @Valid @RequestBody OrderDTO orderDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            OrderResponse orderResponse = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(orderResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable("id") Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok(Collections.singletonMap("message", "Order deleted successfully"));
        } catch (DataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/get-orders-by-key")
//    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<?> getOrdersByKey(
        @RequestParam(value = "keyword", defaultValue = "") String keyword,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(
            page,
            limit,
            Sort.by("orderDate").descending()
        );
        Page<OrderResponse> ordersPage = orderService.getOrdersByKeyword(keyword, pageRequest);
        int totalPages = ordersPage.getTotalPages();
        List<OrderResponse> orders = ordersPage.getContent();
        return ResponseEntity.ok(OrderListResponse.builder().orders(orders).totalPages(totalPages).build());
    }
}
