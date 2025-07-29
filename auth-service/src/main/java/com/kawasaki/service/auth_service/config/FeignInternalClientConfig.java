package com.kawasaki.service.auth_service.config;

import com.kawasaki.service.auth_service.provider.InternalTokenProvider;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignInternalClientConfig {
    @Autowired
    private InternalTokenProvider internalTokenProvider;

    @Bean
    public RequestInterceptor internalTokenInterceptor() {
        return requestTemplate -> {
            String token = internalTokenProvider.getInternalToken();
            requestTemplate.header("Authorization", "Bearer " + token);
        };
    }
}
