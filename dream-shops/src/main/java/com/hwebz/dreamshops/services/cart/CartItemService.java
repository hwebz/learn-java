package com.hwebz.dreamshops.services.cart;

import com.hwebz.dreamshops.exception.ResourceNotFoundException;
import com.hwebz.dreamshops.models.Cart;
import com.hwebz.dreamshops.models.CartItem;
import com.hwebz.dreamshops.models.Product;
import com.hwebz.dreamshops.repositories.CartItemRepository;
import com.hwebz.dreamshops.repositories.CartRepository;
import com.hwebz.dreamshops.services.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final ICartService cartService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        // 1. Get the cart
        Cart cart = cartService.getCart(cartId);
        // 2. Get the product
        Product product = productService.getProductById(productId);
        // 3. Check if the product is already in the cart?
        CartItem cartItem = cart.getCartItemByProductId(productId);
        // 4. If yes, increase the quantity
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            // 5. Else, add new cart item
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        // 1. Get the cart
        Cart cart = cartService.getCart(cartId);
        // 3. Check if the cart item is already in the cart?
        CartItem cartItemToRemove = cart.getCartItemByProductId(productId);
        if (cartItemToRemove == null) {
            throw new ResourceNotFoundException("Product not found");
        }
        cart.removeItem(cartItemToRemove);
        cartItemRepository.delete(cartItemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        // 1. Get the cart
        Cart cart = cartService.getCart(cartId);
        // 2. Updating quantity and total price of cart item
        CartItem cartItem = cart.updateItem(productId, quantity);
        // 3. Saving cart item and cart itself
        if (cartItem == null) {
            throw new ResourceNotFoundException("Item not found");
        }
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }
}
