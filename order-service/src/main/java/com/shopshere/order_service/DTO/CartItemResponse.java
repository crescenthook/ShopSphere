package com.shopshere.order_service.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemResponse {

    private Long productId;
    private Integer quantity;
}