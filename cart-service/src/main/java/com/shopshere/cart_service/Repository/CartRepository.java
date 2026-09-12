package com.shopshere.cart_service.Repository;

import com.shopshere.cart_service.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Optional<Cart> findByUserId(Long userId);
}
