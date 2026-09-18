package com.shopshere.cart_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class WishlistResponseDto {

    private Long wishlistId;
    private Long userId;
    private List<WishlistItemResponseDto> items;
}
