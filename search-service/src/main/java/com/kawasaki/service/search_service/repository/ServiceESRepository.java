package com.kawasaki.service.search_service.repository;

import com.kawasaki.service.search_service.model.ServiceESDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceESRepository extends ElasticsearchRepository<ServiceESDoc, String> {
    Optional<ServiceESDoc> findOptionalByServiceId(Long serviceId);
    List<ServiceESDoc> findByTitle(String title);
    List<ServiceESDoc> findByTitleContaining(String title);
    List<ServiceESDoc> findByCategoryId(Long categoryId);
    long deleteByServiceId(Long serviceId);
}
