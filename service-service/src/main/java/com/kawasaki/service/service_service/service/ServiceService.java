package com.kawasaki.service.service_service.service;

import com.kawasaki.service.service_service.dto.CreateServiceRequestDTO;
import com.kawasaki.service.service_service.dto.ServiceDTO;
import com.kawasaki.service.service_service.model.Service;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceService {
    public ServiceDTO createService(CreateServiceRequestDTO createServiceRequestDTO, long providerId);
}
