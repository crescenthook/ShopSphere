package com.shopshere.cart_service.Client;

import com.shopshere.cart_service.DTO.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/products/{id}")
    ProductResponseDto getProductById(@PathVariable("id") Long id);
}
