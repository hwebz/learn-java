package com.hwebz.dreamshops.repositories;

import com.hwebz.dreamshops.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteByCartId(@Param("cartId") Long cartId);
}
