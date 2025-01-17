package com.hwebz.dreamshops.services.order;

import com.hwebz.dreamshops.dtos.OrderDTO;
import com.hwebz.dreamshops.exception.ResourceNotFoundException;
import com.hwebz.dreamshops.models.*;
import com.hwebz.dreamshops.repositories.OrderRepository;
import com.hwebz.dreamshops.repositories.ProductRepository;
import com.hwebz.dreamshops.services.cart.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> items = createOrderItems(order, cart);
        order.setItems(new HashSet<>(items));
        order.setTotalAmount(calculateTotalAmount(items));

        Order orderSaved = orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return orderSaved;
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());
        order.setUser(cart.getUser());

        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getItems().stream().map(item -> {
            // 1. Decrease the product quantity in case of order is filled
            Product product = item.getProduct();
            product.setInventory(product.getInventory() - item.getQuantity());
            productRepository.save(product);

            // 2. Create order item
            return new OrderItem(order, product, item.getQuantity(), item.getUnitPrice());
        }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<Order> getAllOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public OrderDTO convertToDTO(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }
}
