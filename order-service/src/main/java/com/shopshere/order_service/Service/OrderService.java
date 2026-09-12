package com.shopshere.order_service.Service;

import com.shopshere.order_service.DTO.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(Long userId);

    OrderResponse getOrderById(Long orderId);

    List<OrderResponse> getOrdersByUserId(Long userId);

    OrderResponse cancelOrder(Long orderId);
}
