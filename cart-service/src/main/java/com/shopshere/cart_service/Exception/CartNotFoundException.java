package com.shopshere.cart_service.Exception;

public class CartNotFoundException extends RuntimeException{

    public CartNotFoundException(String message){
        super(message);
    }
}
