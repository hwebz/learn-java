package com.example.shopapp.repositories;

import com.example.shopapp.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.active = True")
    List<Order> findByUserId(@Param("userId") Long userId);
    @Query("SELECT o from Order o WHERE (:keyword IS NULL OR :keyword = '' OR o.fullName LIKE %:keyword% OR o.address LIKE %:keyword% OR o.note LIKE %:keyword%) AND o.active = True")
    Page<Order> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
