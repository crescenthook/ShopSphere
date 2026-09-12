package com.shopshere.order_service.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @PostMapping("/inventory/{productId}/reserve")
    void reserveStock(@PathVariable Long productId, @RequestParam Integer quantity);
}
