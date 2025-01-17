package com.hwebz.dreamshops.controllers;

import com.hwebz.dreamshops.exception.ResourceNotFoundException;
import com.hwebz.dreamshops.models.Cart;
import com.hwebz.dreamshops.responses.ApiResponse;
import com.hwebz.dreamshops.responses.CartResponse;
import com.hwebz.dreamshops.services.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.management.RuntimeErrorException;
import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;
    private final ModelMapper modelMapper;

    @GetMapping("{cartId}")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {
        try {
            Cart cart = cartService.getCart(cartId);
            CartResponse cartResponse = modelMapper.map(cart, CartResponse.class);
            return ResponseEntity.ok(new ApiResponse(true, "Success", cartResponse));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("{cartId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse(true, "Clear Cart Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("{cartId}/total")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId) {
        try {
            BigDecimal totalPrice = cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new ApiResponse(true, "Cart total price", totalPrice));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}
