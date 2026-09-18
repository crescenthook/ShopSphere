package com.shopshere.product_service.DTO;

import java.math.BigDecimal;

public record ProductResponseListDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer quantity,
        Long categoryId,
        String imageUrl
) {
}
