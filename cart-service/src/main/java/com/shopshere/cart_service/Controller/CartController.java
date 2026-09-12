package com.shopshere.cart_service.Controller;

import com.shopshere.cart_service.DTO.CartItemRequestDto;
import com.shopshere.cart_service.DTO.CartResponseDto;
import com.shopshere.cart_service.DTO.UpdateCartItemRequestDto;
import com.shopshere.cart_service.Service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @PostMapping("/items")
    public ResponseEntity<String> addItem(@RequestParam Long userId, @Valid @RequestBody CartItemRequestDto request){

        cartService.addItem(userId, request.getProductId(), request.getQuantity());

        return ResponseEntity.status(HttpStatus.OK).body("Item added to cart");
    }

    @GetMapping
    public ResponseEntity<CartResponseDto> getCart(@RequestParam Long userId){

        CartResponseDto cart = cartService.getCart(userId);

        return ResponseEntity.status(HttpStatus.OK).body(cart);
    }

    @PutMapping("/items/{productId}")
    public ResponseEntity<String> updateItem(@RequestParam Long userId, @PathVariable Long productId, @RequestBody UpdateCartItemRequestDto request){

        cartService.updateItem(userId,productId, request.getQuantity());

        return ResponseEntity.status(HttpStatus.OK).body("Cart Item updated");
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeItem(@RequestParam Long userId,@PathVariable Long productId){

        cartService.removeItem(userId,productId);

        return ResponseEntity.status(HttpStatus.OK).body("Item removed from cart");
    }

    @DeleteMapping
    public ResponseEntity<String> clearCart(@RequestParam Long userId){

        cartService.clearCart(userId);

        return ResponseEntity.status(HttpStatus.OK).body("Cart Cleared");
    }
}
