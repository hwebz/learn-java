package com.hwebz.dreamshops.services.cart;

import com.hwebz.dreamshops.dtos.UserDTO;
import com.hwebz.dreamshops.models.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Long initializeNewCart();
    Cart initializeNewCart(UserDTO user);
    Cart getCartByUserId(Long userId);
}
