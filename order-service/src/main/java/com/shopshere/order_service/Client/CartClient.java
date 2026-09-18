package com.shopshere.order_service.Client;

import com.shopshere.order_service.DTO.CartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cart-service")
public interface CartClient {

    @GetMapping("/cart")
    CartResponse getCart(@RequestParam("userId") Long userId);

    @DeleteMapping("/cart")
    void clearCart(@RequestParam("userId") Long userId);
}
