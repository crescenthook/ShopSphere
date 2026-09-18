package com.shopshere.payment_service.Service;

import com.shopshere.payment_service.DTO.PaymentRequest;
import com.shopshere.payment_service.DTO.PaymentResponse;
import com.shopshere.payment_service.Entity.Payment;
import com.shopshere.payment_service.Entity.PaymentStatus;
import com.shopshere.payment_service.Repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {

        Payment payment = new Payment();

        payment.setOrderId(request.getOrderId());
        payment.setUserId(request.getUserId());
        payment.setAmount(request.getAmount());
        payment.setStatus(PaymentStatus.PENDING);

        LocalDateTime now = LocalDateTime.now();

        payment.setCreatedAt(now);
        payment.setUpdatedAt(now);

        Payment savedPayment = paymentRepository.save(payment);

        savedPayment.setStatus(PaymentStatus.SUCCESS);
        savedPayment.setUpdatedAt(LocalDateTime.now());

        savedPayment = paymentRepository.save(savedPayment);

        return mapToResponse(savedPayment);
    }

    @Override
    public PaymentResponse getPaymentById(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found: " + paymentId));

        return mapToResponse(payment);
    }

    private PaymentResponse mapToResponse(Payment payment) {

        return new PaymentResponse(
                payment.getId(),
                payment.getOrderId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getCreatedAt(),
                payment.getUpdatedAt()
        );
    }
}
