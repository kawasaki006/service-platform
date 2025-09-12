package com.kawasaki.service.search_service.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.kawasaki.service.search_service.model.ServiceESDoc;
import com.kawasaki.service.search_service.repository.ServiceESRepository;
import com.kawasaki.service.search_service.service.ServiceESService;
import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.exception.BizExceptionCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ServiceESServiceImpl implements ServiceESService {
    @Autowired
    private ServiceESRepository serviceESRepository;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

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
    public List<ServiceESDoc> findByCategoryName(String categoryName) {
        try {
            SearchResponse<ServiceESDoc> response = elasticsearchClient.search(s -> s
                    .index("service")
                    .query(q -> q
                            .match(m -> m
                                    .field("categoryName")
                                    .query(categoryName)
                                    .fuzziness("AUTO")
                            )
                    ),
            ServiceESDoc.class);

            return response.hits().hits().stream()
                    .map(Hit::source)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new BizException(BizExceptionCodeEnum.SEARCH_CATEGORY_NAME_ERROR);
        }
    }

    @Override
    public long deleteByServiceId(Long serviceId) {
        return serviceESRepository.deleteByServiceId(serviceId);
    }
}
