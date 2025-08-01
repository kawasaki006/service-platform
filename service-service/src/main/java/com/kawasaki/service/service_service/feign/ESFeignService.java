package com.kawasaki.service.service_service.feign;

import com.kawasaki.service.common.utils.ApiResponse;
import com.kawasaki.service.service_service.es.model.ServiceESDoc;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "search-service")
public interface ESFeignService {
    @PostMapping("/search/service/save")
    ApiResponse<ServiceESDoc> saveServiceDoc(@RequestBody ServiceESDoc serviceESDoc);
}
