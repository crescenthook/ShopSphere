package com.shopshere.cart_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartItemResponseDto {

    private Long productId;
    private Integer quantity;
}
