package com.shopshere.cart_service.Service;

import com.shopshere.cart_service.Client.ProductClient;
import com.shopshere.cart_service.DTO.CartItemResponseDto;
import com.shopshere.cart_service.DTO.CartResponseDto;
import com.shopshere.cart_service.DTO.ProductResponseDto;
import com.shopshere.cart_service.Entity.Cart;
import com.shopshere.cart_service.Entity.CartItem;
import com.shopshere.cart_service.Exception.CartItemNotFoundException;
import com.shopshere.cart_service.Exception.CartNotFoundException;
import com.shopshere.cart_service.Exception.ProductNotFoundException;
import com.shopshere.cart_service.Repository.CartItemRepository;
import com.shopshere.cart_service.Repository.CartRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductClient productClient;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductClient productClient){
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productClient = productClient;
    }

    @Transactional
    public void addItem(Long userId, Long productId, Integer quantity) {

        try{
            ProductResponseDto product = productClient.getProductById(productId);
        }
        catch(FeignException.NotFound ex){
            throw new ProductNotFoundException("Product not found: " + productId);
        }

        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> createCart(userId));

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId).orElse(null);

        if(cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        }
        else{
            CartItem newItem = new CartItem();
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            newItem.setProductId(productId);

            cartItemRepository.save(newItem);
        }

        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
    }

    @Transactional
    public CartResponseDto getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new CartNotFoundException("Cart not found for user: " + userId));

        List<CartItemResponseDto> items = cart.getItems().stream().map(item -> new CartItemResponseDto(item.getProductId(),item.getQuantity())).toList();

        return new CartResponseDto(userId,cart.getId(),items);
    }

    @Transactional
    public void updateItem(Long userId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new CartNotFoundException("Cart not found for user: " + userId));

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId).orElseThrow(() -> new CartItemNotFoundException("Product not found in cart: " + productId));

        cartItem.setQuantity(quantity);

        cartItemRepository.save(cartItem);

        cart.setUpdatedAt(java.time.LocalDateTime.now());
        cartRepository.save(cart);
    }

    @Transactional
    public void removeItem(Long userId, Long productId) {

        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new CartNotFoundException("Cart not found for user: " + userId));

        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId).orElseThrow(() -> new CartItemNotFoundException("Product not found in cart: " + productId));

        cart.getItems().remove(cartItem);
        cart.setUpdatedAt(LocalDateTime.now());

        cartRepository.save(cart);
    }

    @Transactional
    public void clearCart(Long userId) {

        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new CartNotFoundException("Cart not found for user: " + userId));

        cart.getItems().clear();
        cart.setUpdatedAt(LocalDateTime.now());

        cartRepository.save(cart);
    }

    private Cart createCart(Long userId) {

        Cart cart = new Cart();
        cart.setUserId(userId);

        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        cart.setCreatedAt(now);
        cart.setUpdatedAt(now);

        return cartRepository.save(cart);
    }
}
