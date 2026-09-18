package com.shopshere.cart_service.Repository;

import com.shopshere.cart_service.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
}
