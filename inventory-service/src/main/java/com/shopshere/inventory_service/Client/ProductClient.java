package com.shopshere.inventory_service.Client;

import com.shopshere.inventory_service.DTO.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/products/{productId}")
    ProductResponse getProductById(@PathVariable Long productId);
}
