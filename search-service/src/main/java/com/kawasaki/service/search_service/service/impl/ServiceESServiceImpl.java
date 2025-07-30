package com.kawasaki.service.search_service.service.impl;

import com.kawasaki.service.search_service.model.ServiceESDoc;
import com.kawasaki.service.search_service.repository.ServiceESRepository;
import com.kawasaki.service.search_service.service.ServiceESService;
import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.exception.BizExceptionCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ServiceESServiceImpl implements ServiceESService {
    @Autowired
    private ServiceESRepository serviceESRepository;

    @Override
    public ServiceESDoc saveService(ServiceESDoc serviceDoc) {
        try {
            return serviceESRepository.save(serviceDoc);
        } catch (Exception e) {
            log.error("Error saving a service doc: {}", e.getMessage());
            throw new BizException(BizExceptionCodeEnum.FAIL_TO_SAVE_ES_DOC);
        }
    }

    @Override
    public Optional<ServiceESDoc> findByServiceId(Long serviceId) {
        return serviceESRepository.findOptionalByServiceId(serviceId);
    }

    @Override
    public List<ServiceESDoc> searchByTitle(String title) {
        return serviceESRepository.findByTitleContaining(title);
    }

    @Override
    public List<ServiceESDoc> findByCategoryId(Long categoryId) {
        return serviceESRepository.findByCategoryId(categoryId);
    }

    @Override
    public long deleteByServiceId(Long serviceId) {
        return serviceESRepository.deleteByServiceId(serviceId);
    }
}
