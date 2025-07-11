package com.kawasaki.service.provider_service.service;

import com.kawasaki.service.provider_service.model.Provider;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderService {
    Provider findByEmail(String email);

    Provider createProvider(String name, String email, String phone, String password, String bio);
}
