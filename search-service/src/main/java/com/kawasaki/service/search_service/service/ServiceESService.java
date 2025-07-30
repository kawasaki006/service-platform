package com.kawasaki.service.search_service.service;

import com.kawasaki.service.search_service.model.ServiceESDoc;

import java.util.List;
import java.util.Optional;

public interface ServiceESService {
    ServiceESDoc saveService(ServiceESDoc serviceDoc);
    //Optional<ServiceESDoc> findById(String Id);
    Optional<ServiceESDoc> findByServiceId(Long serviceId);
    List<ServiceESDoc> searchByTitle(String title);
    List<ServiceESDoc> findByCategoryId(Long categoryId);
    long deleteByServiceId(Long serviceId);
}
