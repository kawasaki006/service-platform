package com.kawasaki.service.search_service.controller;

import com.kawasaki.service.search_service.model.ServiceESDoc;
import com.kawasaki.service.search_service.service.ServiceESService;
import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.exception.BizExceptionCodeEnum;
import com.kawasaki.service.common.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search/service")
public class ServiceESController {
    @Autowired
    private ServiceESService serviceESService;

    @PostMapping("/save")
    public ApiResponse<ServiceESDoc> saveServiceDoc(@RequestBody ServiceESDoc serviceESDoc) {
        // save service
        ServiceESDoc saved = serviceESService.saveService(serviceESDoc);
        return ApiResponse.success(saved);
    }

    @GetMapping("/title")
    public ApiResponse<List<ServiceESDoc>> searchByTitle(@RequestParam String title) {
        List<ServiceESDoc> results = serviceESService.searchByTitle(title);
        return ApiResponse.success(results);
    }

    @GetMapping("/category/{categoryId}")
    public ApiResponse<List<ServiceESDoc>> findByCategoryId(@PathVariable Long categoryId) {
        List<ServiceESDoc> results = serviceESService.findByCategoryId(categoryId);
        return ApiResponse.success(results);
    }

    @GetMapping("/{serviceId}")
    public ApiResponse<ServiceESDoc> findByServiceId(@PathVariable Long serviceId) {
        ServiceESDoc result = serviceESService.findByServiceId(serviceId)
                .orElseThrow(() -> new BizException(BizExceptionCodeEnum.INVALID_SERVICE_ID_ES));
        return ApiResponse.success(result);
    }

    @DeleteMapping("/{serviceId}")
    public ApiResponse<Long> deleteByServiceId(@PathVariable Long serviceId) {
        Long numDeleted = serviceESService.deleteByServiceId(serviceId);
        return ApiResponse.success(numDeleted);
    }
}
