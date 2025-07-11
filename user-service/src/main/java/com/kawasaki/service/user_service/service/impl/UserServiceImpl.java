package com.kawasaki.service.user_service.service.impl;

import com.kawasaki.service.user_service.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kawasaki.service.user_service.mapper.UserMapper;
import com.kawasaki.service.user_service.model.User;
import com.kawasaki.service.user_service.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> getAllUsers() {
        return userMapper.selectByExample(new UserExample());
    }
}
