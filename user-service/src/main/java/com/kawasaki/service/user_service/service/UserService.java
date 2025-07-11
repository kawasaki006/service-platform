package com.kawasaki.service.user_service.service;

import com.kawasaki.service.user_service.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserService {
    List<User> getAllUsers();
}
