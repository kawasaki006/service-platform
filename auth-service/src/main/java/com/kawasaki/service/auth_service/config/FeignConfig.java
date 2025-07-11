package com.kawasaki.service.auth_service.config;

import com.kawasaki.service.auth_service.feign.CustomFeignErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public ErrorDecoder customErrorDecoder() {
        return new CustomFeignErrorDecoder();
    }
}
