package com.shopshere.api_gateway.Exception;

import javax.naming.AuthenticationException;

public class JwtAuthenticationException extends RuntimeException {

    public JwtAuthenticationException(String message){
        super(message);
    }
}
