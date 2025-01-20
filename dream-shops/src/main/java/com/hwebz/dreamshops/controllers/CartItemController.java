package com.hwebz.dreamshops.controllers;

import com.hwebz.dreamshops.dtos.UserDTO;
import com.hwebz.dreamshops.exception.ResourceNotFoundException;
import com.hwebz.dreamshops.models.Cart;
import com.hwebz.dreamshops.models.User;
import com.hwebz.dreamshops.responses.ApiResponse;
import com.hwebz.dreamshops.services.cart.ICartItemService;
import com.hwebz.dreamshops.services.cart.ICartService;
import com.hwebz.dreamshops.services.user.IUserService;
import com.hwebz.dreamshops.services.user.UserService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cart-items")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService userService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> addItemToCard(
//            @RequestParam(required = false) Long cartId,
            @RequestParam Long productId,
            @RequestParam Integer quantity
//            @RequestParam Long userId
    ) {
        try {
//            UserDTO userDTO = userService.getUserById(userId);
//            if (cartId == null) {
            UserDTO userDTO = userService.getAuthenticatedUser();
//                cartId = cartService.initializeNewCart();
//            }
            Cart cart = cartService.initializeNewCart(userDTO);
            cartItemService.addItemToCart(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new ApiResponse(true, "Add item success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, e.getMessage(), null));
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @DeleteMapping("{cartId}/{productId}")
    public ResponseEntity<ApiResponse> removeItemFromCard(@PathVariable Long cartId, @PathVariable Long productId) {
        try {
            cartItemService.removeItemFromCart(cartId, productId);
            return ResponseEntity.ok(new ApiResponse(true, "Remove item success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @PutMapping("{cartId}/{productId}")
    public ResponseEntity<ApiResponse> updateItemQuantity(
            @PathVariable Long cartId,
            @PathVariable Long productId,
            @RequestParam Integer quantity
    ) {
        try {
            cartItemService.updateItemQuantity(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse(true, "Update item quantity success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, e.getMessage(), null));
        }
    }
}
