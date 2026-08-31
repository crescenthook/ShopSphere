package com.shopshere.product_service.Client;

import com.shopshere.product_service.Config.FeignClientConfig;
import com.shopshere.product_service.DTO.CategoryResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "category-service",configuration = FeignClientConfig.class)
public interface CategoryClient {

    @GetMapping("/categories/{id}")
    CategoryResponseDto getCategoryById(@PathVariable Long id);
}
