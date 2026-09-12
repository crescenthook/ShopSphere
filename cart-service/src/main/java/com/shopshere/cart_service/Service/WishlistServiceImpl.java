package com.shopshere.cart_service.Service;

import com.shopshere.cart_service.Client.ProductClient;
import com.shopshere.cart_service.DTO.ProductResponseDto;
import com.shopshere.cart_service.DTO.WishlistItemResponseDto;
import com.shopshere.cart_service.DTO.WishlistResponseDto;
import com.shopshere.cart_service.Entity.Wishlist;
import com.shopshere.cart_service.Entity.WishlistItem;
import com.shopshere.cart_service.Exception.ProductNotFoundException;
import com.shopshere.cart_service.Exception.WishlistItemNotFoundException;
import com.shopshere.cart_service.Exception.WishlistNotFoundException;
import com.shopshere.cart_service.Repository.WishlistItemRepository;
import com.shopshere.cart_service.Repository.WishlistRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService{

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final ProductClient productClient;

    public void addItem(Long userId, Long productId) {

        try{
            ProductResponseDto product = productClient.getProductById(productId);
        }
        catch(FeignException.NotFound ex){
            throw new ProductNotFoundException("Product not found: " + productId);
        }

        Wishlist wishlist = wishlistRepository.findByUserId(userId).orElseGet(() -> createWishlist(userId));

        Optional<WishlistItem> existingItem = wishlistItemRepository.findByWishlistIdAndProductId(wishlist.getId(), productId);

        if(existingItem.isEmpty()){
            WishlistItem item = new WishlistItem();
            item.setWishlist(wishlist);
            item.setProductId(productId);

            wishlistItemRepository.save(item);
        }

    }

    @Transactional
    public WishlistResponseDto getWishlist(Long userId) {

        Wishlist wishlist = wishlistRepository.findByUserId(userId).orElseThrow(() -> new WishlistNotFoundException("Wishlist not found for user: " + userId));

        List<WishlistItemResponseDto> items = wishlist.getItems().stream().map(item -> new WishlistItemResponseDto(item.getProductId())).toList();

        return new WishlistResponseDto(wishlist.getId(), wishlist.getUserId(), items);
    }

    @Transactional
    public void removeItem(Long userId, Long productId) {
        Wishlist wishlist = wishlistRepository.findByUserId(userId).orElseThrow(() -> new WishlistNotFoundException( "Wishlist not found for user: " + userId));

        WishlistItem existingItem = wishlistItemRepository.findByWishlistIdAndProductId(wishlist.getId(), productId).orElseThrow(() -> new WishlistItemNotFoundException("Product not found in wishlist: " + productId));

        wishlist.getItems().remove(existingItem);
        wishlistItemRepository.delete(existingItem);
    }

    private Wishlist createWishlist(Long userId) {

        Wishlist wishlist = new Wishlist();
        wishlist.setUserId(userId);
        wishlist.setCreatedAt(LocalDateTime.now());

        return wishlistRepository.save(wishlist);
    }
}
