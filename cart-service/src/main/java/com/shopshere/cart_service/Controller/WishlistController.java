package com.shopshere.cart_service.Controller;

import com.shopshere.cart_service.DTO.WishlistItemRequestDto;
import com.shopshere.cart_service.DTO.WishlistResponseDto;
import com.shopshere.cart_service.Service.WishlistService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping
    public ResponseEntity<String> addItem(@RequestBody @Valid WishlistItemRequestDto request, @RequestParam Long userId){

        wishlistService.addItem(userId, request.getProductId());
        return ResponseEntity.status(HttpStatus.OK).body("Item added to Wishlist");
    }

    @GetMapping
    public ResponseEntity<WishlistResponseDto> getWishlist(@RequestParam Long userId){

        WishlistResponseDto response = wishlistService.getWishlist(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeItem(@RequestParam Long userId, @PathVariable Long productId) {

        wishlistService.removeItem(userId, productId);

        return ResponseEntity.ok("Item removed from wishlist");
    }
}
