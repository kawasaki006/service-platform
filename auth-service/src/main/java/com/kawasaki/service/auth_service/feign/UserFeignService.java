package com.kawasaki.service.auth_service.feign;

import com.kawasaki.service.auth_service.config.FeignInternalClientConfig;
import com.kawasaki.service.auth_service.dto.CreateUserRequestDTO;
import com.kawasaki.service.auth_service.dto.UserDTO;
import com.kawasaki.service.common.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-service")
public interface UserFeignService {
    @GetMapping("/user/user/find")
    public ApiResponse<UserDTO> findUserByEmail(@RequestParam String email);

    @PostMapping("/user/user/create")
    public ApiResponse<UserDTO> createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO);
}
