package com.shopshere.payment_service.Service;

import com.shopshere.payment_service.DTO.PaymentRequest;
import com.shopshere.payment_service.DTO.PaymentResponse;

public interface PaymentService {

    PaymentResponse createPayment(PaymentRequest request);

    PaymentResponse getPaymentById(Long paymentId);
}
