package com.kawasaki.service.provider_service.controller;

import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.exception.BizExceptionCodeEnum;
import com.kawasaki.service.provider_service.DTO.CreateProviderRequestDTO;
import com.kawasaki.service.provider_service.DTO.ProviderDTO;
import com.kawasaki.service.provider_service.model.Provider;
import com.kawasaki.service.provider_service.service.ProviderService;
import com.kawasaki.service.common.utils.ApiResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/provider/provider")
public class ProviderController {
    @Autowired
    private ProviderService providerService;

    @GetMapping("/find")
    public ApiResponse<ProviderDTO> findProviderByEmail(@RequestParam String email) {
        Provider provider = providerService.findByEmail(email);
        if (provider == null) {
            throw new BizException(BizExceptionCodeEnum.INVALID_EMAIL_PASSWORD);
        }
        ProviderDTO dto = new ProviderDTO();
        // expose dto fields
        BeanUtils.copyProperties(provider, dto);
        return ApiResponse.success(dto);
    }

    @PostMapping("/create")
    public ApiResponse<ProviderDTO> createProvider(@RequestBody CreateProviderRequestDTO createProviderRequestDTO) {
        String name = createProviderRequestDTO.getName();
        String email = createProviderRequestDTO.getEmail();
        String phone = createProviderRequestDTO.getPhone();
        String password_hash = createProviderRequestDTO.getPasswordHash();
        String bio = createProviderRequestDTO.getBio();

        Provider provider = providerService.createProvider(name, email, phone, password_hash, bio);

        ProviderDTO dto = new ProviderDTO();
        BeanUtils.copyProperties(provider, dto);
        return ApiResponse.success(dto);
    }
}
