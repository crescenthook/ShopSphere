package com.shopshere.cart_service.Exception;

public class WishlistItemNotFoundException extends RuntimeException{

    public WishlistItemNotFoundException(String message){
        super(message);
    }
}
