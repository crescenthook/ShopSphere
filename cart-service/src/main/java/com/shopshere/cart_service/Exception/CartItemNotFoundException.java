package com.shopshere.cart_service.Exception;

public class CartItemNotFoundException extends RuntimeException{

    public CartItemNotFoundException(String message){
        super(message);
    }
}
