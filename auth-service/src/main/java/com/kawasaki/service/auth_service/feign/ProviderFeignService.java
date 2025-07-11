package com.kawasaki.service.auth_service.feign;

import com.kawasaki.service.auth_service.DTO.CreateProviderRequestDTO;
import com.kawasaki.service.auth_service.DTO.ProviderDTO;
import com.kawasaki.service.common.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("provider-service")
public interface ProviderFeignService {
    @GetMapping("/provider/provider/find")
    public ApiResponse<ProviderDTO> findProviderByEmail(@RequestParam String email);

    @PostMapping("/provider/provider/create")
    public ApiResponse<ProviderDTO> createProvider(@RequestBody CreateProviderRequestDTO createProviderRequestDTO);
}
