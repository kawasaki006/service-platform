package com.kawasaki.service.user_service.service;

import com.kawasaki.service.user_service.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User findByEmail(String email);
}
