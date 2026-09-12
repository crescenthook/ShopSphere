package com.shopshere.cart_service.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WishlistItemRequestDto {

    @NotNull
    private Long productId;
}
