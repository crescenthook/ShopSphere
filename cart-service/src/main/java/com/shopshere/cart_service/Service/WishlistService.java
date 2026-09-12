package com.shopshere.cart_service.Service;

import com.shopshere.cart_service.DTO.WishlistResponseDto;

public interface WishlistService {

    void addItem(Long userId, Long productId);

    WishlistResponseDto getWishlist(Long userId);

    void removeItem(Long userId, Long productId);
}
