package com.kawasaki.service.service_service.controller;

import com.kawasaki.service.common.utils.ApiResponse;
import com.kawasaki.service.service_service.dto.CreateServiceRequestDTO;
import com.kawasaki.service.service_service.dto.ServiceDTO;
import com.kawasaki.service.service_service.model.Service;
import com.kawasaki.service.service_service.service.ServiceService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service/service")
public class ServiceController {
    @Autowired
    ServiceService serviceService;

    @PostMapping("/create")
    public ApiResponse<ServiceDTO> create(
            @Valid @RequestBody CreateServiceRequestDTO createServiceRequestDTO,
            @RequestHeader("X_User_Id") String providerIdStr) {
        long providerId = Long.valueOf(providerIdStr);
        Service service = serviceService.createService(createServiceRequestDTO, providerId);
        ServiceDTO serviceDTO = new ServiceDTO();
        BeanUtils.copyProperties(service, serviceDTO);

        return ApiResponse.success(serviceDTO);
    }
}
