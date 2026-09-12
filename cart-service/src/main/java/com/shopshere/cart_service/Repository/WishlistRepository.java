package com.shopshere.cart_service.Repository;

import com.shopshere.cart_service.Entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist,Long> {

    Optional<Wishlist> findByUserId(Long userId);
}
