package com.shopshere.order_service.Repository;

import com.shopshere.order_service.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUserId(Long userId);
}
