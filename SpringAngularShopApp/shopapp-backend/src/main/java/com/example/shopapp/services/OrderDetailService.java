package com.example.shopapp.services;

import com.example.shopapp.dtos.OrderDetailDTO;
import com.example.shopapp.exceptions.DataNotFoundException;
import com.example.shopapp.models.Order;
import com.example.shopapp.models.OrderDetail;
import com.example.shopapp.models.Product;
import com.example.shopapp.repositories.OrderDetailRepository;
import com.example.shopapp.repositories.OrderRepository;
import com.example.shopapp.repositories.ProductRepository;
import com.example.shopapp.responses.OrderDetailResponse;
import com.example.shopapp.services.interfaces.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetailResponse createOrderDetail(OrderDetailDTO newOrderDetailDTO) throws DataNotFoundException {
        Order order = orderRepository.findById(newOrderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + newOrderDetailDTO.getOrderId()));
        Product product = productRepository.findById(newOrderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find product with id: " + newOrderDetailDTO.getProductId()));

        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .price(newOrderDetailDTO.getPrice())
                .quantity(newOrderDetailDTO.getQuantity())
                .totalMoney(newOrderDetailDTO.getTotalMoney())
                .color(newOrderDetailDTO.getColor())
                .build();
        orderDetailRepository.save(orderDetail);

        return OrderDetailResponse.fromOrderDetail(orderDetail);
    }

    @Override
    public OrderDetailResponse getOrderDetail(Long id) throws DataNotFoundException {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order detail with id: " + id));

        return OrderDetailResponse.fromOrderDetail(orderDetail);
    }

    @Override
    public OrderDetailResponse updateOrderDetail(Long id, OrderDetailDTO newOrderDetailDTO) throws DataNotFoundException {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order detail with id: " + id));
        Order existingOrder = orderRepository.findById(newOrderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + newOrderDetailDTO.getOrderId()));
        Product existingProduct = productRepository.findById(newOrderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find product with id: " + newOrderDetailDTO.getProductId()));

        existingOrderDetail.setPrice(newOrderDetailDTO.getPrice());
        existingOrderDetail.setQuantity(newOrderDetailDTO.getQuantity());
        existingOrderDetail.setTotalMoney(newOrderDetailDTO.getTotalMoney());
        existingOrderDetail.setColor(newOrderDetailDTO.getColor());
        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setProduct(existingProduct);

        orderDetailRepository.save(existingOrderDetail);

        return OrderDetailResponse.fromOrderDetail(existingOrderDetail);
    }

    @Override
    public void deleteOrderDetail(Long id) throws DataNotFoundException {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order detail with id: " + id));
        orderDetailRepository.delete(existingOrderDetail);
    }

    @Override
    public List<OrderDetailResponse> getOrderDetails(Long orderId) {
        return orderDetailRepository
                .findByOrderId(orderId)
                .stream()
                .map(OrderDetailResponse::fromOrderDetail)
                .toList();
    }
}
