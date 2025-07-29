package com.kawasaki.service.auth_service.service.impl;

import com.kawasaki.service.auth_service.service.InternalClientService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InternalClientServiceImpl implements InternalClientService {
    private final Map<String, String> INTERNAL_CLIENTS = Map.of(
            "auth-service", "auth-service-secret"
    );

    private final Map<String, List<String>> INTERNAL_CLIENT_SCOPES= Map.of(
            "auth-service", Arrays.asList("user.read", "user.write")
    );

    @Override
    public boolean validateClient(String clientId, String clientSecret) {
        return INTERNAL_CLIENTS.containsKey(clientId) &&
                INTERNAL_CLIENTS.get(clientId).equals(clientSecret);
    }

    @Override
    public List<String> getClientScopes(String clientId) {
        return INTERNAL_CLIENT_SCOPES.getOrDefault(clientId, Collections.EMPTY_LIST);
    }
}
