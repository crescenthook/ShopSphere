package com.shopshere.order_service.Controller;

import com.shopshere.order_service.DTO.OrderResponse;
import com.shopshere.order_service.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestParam Long userId){

        OrderResponse response = orderService.createOrder(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {

        OrderResponse response = orderService.getOrderById(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@RequestParam Long userId) {

        List<OrderResponse> response = orderService.getOrdersByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId) {

        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }
}
