package com.kawasaki.service.service_service.controller;

import com.kawasaki.service.common.utils.ApiResponse;
import com.kawasaki.service.service_service.dto.CreateServiceRequestDTO;
import com.kawasaki.service.service_service.dto.ServiceDTO;
import com.kawasaki.service.service_service.es.model.ServiceESDoc;
import com.kawasaki.service.service_service.es.service.ServiceESService;
import com.kawasaki.service.service_service.model.Service;
import com.kawasaki.service.service_service.service.ServiceService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/service/service")
public class ServiceController {
    @Autowired
    ServiceService serviceService;

    @Autowired
    ServiceESService serviceESService;

    @PostMapping("/create")
    public ApiResponse<ServiceDTO> create(@Valid @RequestBody CreateServiceRequestDTO createServiceRequestDTO) {
        //TODO: get provider id from security context holder (currently hard coded)
        long providerId = Long.valueOf(2);
        ServiceDTO service = serviceService.createService(createServiceRequestDTO, providerId);

        // save to es
        ServiceESDoc esDoc = serviceESService.saveService(service);

        ServiceDTO serviceDTO = new ServiceDTO();
        BeanUtils.copyProperties(service, serviceDTO);

        return ApiResponse.success(serviceDTO);
    }
}
