package com.shopshere.payment_service.Repository;

import com.shopshere.payment_service.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    Optional<Payment> findByOrderId(Long orderId);
}
