package com.kawasaki.service.auth_service.feign;

import com.kawasaki.service.auth_service.DTO.UserDTO;
import com.kawasaki.service.common.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user-service")
public interface UserFeignService {
    @GetMapping("/user/user/find")
    public ApiResponse<UserDTO> findUserByEmail(@RequestParam String email);
}
