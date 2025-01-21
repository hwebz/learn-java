package com.hwebz.microservices.order.repositories;

import com.hwebz.microservices.order.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
