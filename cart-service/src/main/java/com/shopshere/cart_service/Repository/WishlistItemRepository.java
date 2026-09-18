package com.shopshere.cart_service.Repository;

import com.shopshere.cart_service.Entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistItemRepository extends JpaRepository<WishlistItem,Long> {
    Optional<WishlistItem> findByWishlistIdAndProductId(Long wishlistId, Long productId);
}
