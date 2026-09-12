package com.shopshere.cart_service.Service;

import com.shopshere.cart_service.DTO.CartResponseDto;

public interface CartService {

    void addItem(Long userId, Long productId, Integer quantity);

    CartResponseDto getCart(Long userId);

    void updateItem(Long userId, Long productId, Integer quantity);

    void removeItem(Long userId, Long productId);

    void clearCart(Long userId);

}
