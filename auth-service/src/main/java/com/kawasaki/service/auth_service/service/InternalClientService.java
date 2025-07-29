package com.kawasaki.service.auth_service.service;

import java.util.List;

public interface InternalClientService {
    boolean validateClient(String clientId, String clientSecret);

    List<String> getClientScopes(String clientId);
}
