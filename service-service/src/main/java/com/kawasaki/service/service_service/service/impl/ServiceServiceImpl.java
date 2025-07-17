package com.kawasaki.service.service_service.service.impl;

import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.exception.BizExceptionCodeEnum;
import com.kawasaki.service.service_service.DTO.CreateServiceRequestDTO;
import com.kawasaki.service.service_service.mapper.ServiceMapper;
import com.kawasaki.service.service_service.service.ServiceService;
import com.kawasaki.service.service_service.model.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {
    @Autowired
    ServiceMapper serviceMapper;

    @Override
    public Service createService(CreateServiceRequestDTO createServiceRequestDTO, long providerId) {
        // instantiate
        Service service = new Service();
        service.setProviderId(providerId);
        service.setTitle(createServiceRequestDTO.getTitle());
        service.setCategoryId(createServiceRequestDTO.getCategoryId());
        service.setDescription(createServiceRequestDTO.getDescription());
        service.setBasePrice(createServiceRequestDTO.getBasePrice());
        service.setIsActive(true);
        service.setCreatedAt(new Date(System.currentTimeMillis()));
        service.setUpdatedAt(new Date(System.currentTimeMillis()));

        int rows = serviceMapper.insert(service);
        if (rows != 1) {
            throw new BizException(BizExceptionCodeEnum.DB_INSERT_FAILED);
        }

        return service;
    }
}
