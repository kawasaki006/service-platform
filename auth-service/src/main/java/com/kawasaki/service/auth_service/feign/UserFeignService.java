package com.kawasaki.service.auth_service.feign;

import com.kawasaki.service.auth_service.config.FeignInternalClientConfig;
import com.kawasaki.service.auth_service.dto.UserDTO;
import com.kawasaki.service.common.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-service", configuration = FeignInternalClientConfig.class)
public interface UserFeignService {
    @GetMapping("/user/user/find")
    public ApiResponse<UserDTO> findUserByEmail(@RequestParam String email);
}
