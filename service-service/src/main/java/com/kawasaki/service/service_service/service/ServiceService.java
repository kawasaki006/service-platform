package com.kawasaki.service.service_service.service;

import com.kawasaki.service.service_service.DTO.CreateServiceRequestDTO;
import com.kawasaki.service.service_service.model.Service;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceService {
    public Service createService(CreateServiceRequestDTO createServiceRequestDTO, long providerId);
}
