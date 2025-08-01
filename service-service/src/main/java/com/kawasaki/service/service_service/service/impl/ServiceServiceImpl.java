package com.kawasaki.service.service_service.service.impl;

import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.exception.BizExceptionCodeEnum;
import com.kawasaki.service.service_service.dto.CreateServiceRequestDTO;
import com.kawasaki.service.service_service.dto.ServiceDTO;
import com.kawasaki.service.service_service.mapper.CustomServiceAttributeValueMapper;
import com.kawasaki.service.service_service.mapper.ServiceAttributeValueMapper;
import com.kawasaki.service.service_service.mapper.ServiceMapper;
import com.kawasaki.service.service_service.model.ServiceAttributeValue;
import com.kawasaki.service.service_service.service.ServiceService;
import com.kawasaki.service.service_service.model.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {
    @Autowired
    ServiceMapper serviceMapper;

    @Autowired
    CustomServiceAttributeValueMapper attributeValueMapper;

    @Transactional
    @Override
    public ServiceDTO createService(CreateServiceRequestDTO createServiceRequestDTO, long providerId) {
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

        // save attribute values
        Long serviceId = service.getId();
        List<ServiceAttributeValue> attrValues = createServiceRequestDTO.getAttrs().stream()
                .map(attr -> {
                    ServiceAttributeValue value = new ServiceAttributeValue();
                    value.setServiceId(serviceId);
                    value.setAttributeId(attr.getAttrId());
                    value.setOptionId(attr.getOptionId());
                    return value;
                })
                .collect(Collectors.toList());

        int attrRowCount = attributeValueMapper.batchInsert(attrValues);
        if (attrRowCount != attrValues.size()) {
            throw new BizException(BizExceptionCodeEnum.DB_INSERT_FAILED);
        }

        // pack return value
        ServiceDTO serviceDTO = new ServiceDTO();
        BeanUtils.copyProperties(service, serviceDTO);
        serviceDTO.setAttrs(createServiceRequestDTO.getAttrs());

        return serviceDTO;
    }
}
