package com.shopshere.cart_service.Exception;

public class WishlistNotFoundException extends RuntimeException{

    public WishlistNotFoundException(String message){
        super(message);
    }
}
