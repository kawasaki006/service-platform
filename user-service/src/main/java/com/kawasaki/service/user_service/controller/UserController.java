package com.kawasaki.service.user_service.controller;

import com.kawasaki.service.user_service.model.User;
import com.kawasaki.service.user_service.service.UserService;
import com.kawasaki.service.common.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ApiResponse<List<User>> listAll() {
        List<User> data = userService.getAllUsers();
        return ApiResponse.success(data);
    }
}
