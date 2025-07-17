package com.kawasaki.service.booking_service.service;

import com.kawasaki.service.booking_service.dto.CreateRequestDTO;
import com.kawasaki.service.booking_service.model.Request;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestService {
    public Request createRequest(CreateRequestDTO createRequestDTO, long userId);
}
