package com.hwebz.dreamshops.services.cart;

import com.hwebz.dreamshops.dtos.UserDTO;
import com.hwebz.dreamshops.exception.ResourceNotFoundException;
import com.hwebz.dreamshops.models.Cart;
import com.hwebz.dreamshops.models.User;
import com.hwebz.dreamshops.repositories.CartItemRepository;
import com.hwebz.dreamshops.repositories.CartRepository;
import com.hwebz.dreamshops.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    @Override
    public Cart getCart(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    @Override
    @Transactional
    public void clearCart(Long id) {
        cartItemRepository.deleteByCartId(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Long initializeNewCart() {
        Cart cart = new Cart();
        cartRepository.save(cart);
        return cartRepository.save(cart).getId();
    }

    @Override
    public Cart initializeNewCart(UserDTO user) {
        return Optional.ofNullable(getCartByUserId(user.getId())).orElseGet(() -> {
            Cart cart = new Cart();
            User existingUser = userRepository.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            cart.setUser(existingUser);
            return cartRepository.save(cart);
        });
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
