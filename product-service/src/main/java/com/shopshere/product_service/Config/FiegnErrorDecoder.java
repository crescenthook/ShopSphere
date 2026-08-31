package com.shopshere.product_service.Config;

import com.shopshere.product_service.Exception.CategoryNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FiegnErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        if(response.status() == 404){
            throw new CategoryNotFoundException("Category Not Found");
        }

        return new RuntimeException("Error occurred while calling Category Service");
    }
}
