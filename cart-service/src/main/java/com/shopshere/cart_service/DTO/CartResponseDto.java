package com.shopshere.cart_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CartResponseDto {

    private Long userId;
    private Long cartId;

    private List<CartItemResponseDto> items;
}
