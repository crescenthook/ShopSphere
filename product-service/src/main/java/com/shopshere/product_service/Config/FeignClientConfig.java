package com.shopshere.product_service.Config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class FeignClientConfig {

    @Bean
    public ErrorDecoder errorDecoder(){
        return new FiegnErrorDecoder();
    }
}
