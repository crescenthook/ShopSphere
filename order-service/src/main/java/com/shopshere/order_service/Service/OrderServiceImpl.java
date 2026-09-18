package com.shopshere.order_service.Service;

import com.shopshere.order_service.Client.CartClient;
import com.shopshere.order_service.Client.InventoryClient;
import com.shopshere.order_service.Client.PaymentClient;
import com.shopshere.order_service.Client.ProductClient;
import com.shopshere.order_service.DTO.*;
import com.shopshere.order_service.Entity.Order;
import com.shopshere.order_service.Entity.OrderItem;
import com.shopshere.order_service.Entity.OrderStatus;
import com.shopshere.order_service.Event.OrderCreatedEvent;
import com.shopshere.order_service.Event.OrderItemEvent;
import com.shopshere.order_service.Exception.OrderNotFoundException;
import com.shopshere.order_service.Producer.OrderEventProducer;
import com.shopshere.order_service.Repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartClient cartClient;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;
    private final PaymentClient paymentClient;
    private final OrderEventProducer orderEventProducer;

    @Transactional
    public OrderResponse createOrder(Long userId) {

        CartResponse cart = cartClient.getCart(userId);

        if(cart.getItems() == null || cart.getItems().isEmpty()){
            throw new RuntimeException("Cannot create order from an empty cart");
        }

        Order order = new Order();

        order.setUserId(userId);
        order.setStatus(OrderStatus.PENDING);

        LocalDateTime now = LocalDateTime.now();
        order.setCreatedAt(now);
        order.setUpdatedAt(now);

        BigDecimal totalAmount = BigDecimal.ZERO;

        for(CartItemResponse cartItem : cart.getItems()){

            ProductResponse product = productClient.getProductById(cartItem.getProductId());

            //inventoryClient.reserveStock(product.getId(), cartItem.getQuantity());

            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());

            orderItem.setSubtotal(subtotal);
            order.getItems().add(orderItem);

            totalAmount = totalAmount.add(subtotal);
        }

        //Set Amount
        order.setTotalAmount(totalAmount);

        //Save Order to OrderRepository
        Order savedOrder = orderRepository.save(order);

        //Publish OrderCreatedEvent
        List<OrderItemEvent> items = savedOrder.getItems().stream().map(x -> new OrderItemEvent(x.getProductId(), x.getQuantity())).toList();
        OrderCreatedEvent event = new OrderCreatedEvent(savedOrder.getId(), items);
        orderEventProducer.publishOrderCreated(event);

        //Create PaymentRequest
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setUserId(userId);
        paymentRequest.setAmount(savedOrder.getTotalAmount());
        paymentRequest.setOrderId(savedOrder.getId());

        PaymentResponse paymentResponse = paymentClient.createPayment(paymentRequest);

        if (paymentResponse.getStatus() == PaymentStatus.SUCCESS) {

            savedOrder.setStatus(OrderStatus.CONFIRMED);
            savedOrder = orderRepository.save(savedOrder);

            cartClient.clearCart(userId);
        } else {

            savedOrder.setStatus(OrderStatus.CANCELLED);
            savedOrder = orderRepository.save(savedOrder);
        }

        return mapToResponse(savedOrder);
    }

    public OrderResponse getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found: " + orderId));

        return mapToResponse(order);
    }

    public List<OrderResponse> getOrdersByUserId(Long userId) {

        return orderRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    public OrderResponse cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found: " + orderId));

        if(order.getStatus() == OrderStatus.CANCELLED){
            throw new RuntimeException("Order is already cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);
        Order savedOrder = orderRepository.save(order);

        return mapToResponse(savedOrder);
    }

    private OrderResponse mapToResponse(Order order){

        List<OrderItemResponse> items = order.getItems()
                .stream()
                .map(item -> new OrderItemResponse(
                        item.getProductId(),
                        item.getProductName(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getSubtotal()
                ))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt(),
                items
        );
    }
}
