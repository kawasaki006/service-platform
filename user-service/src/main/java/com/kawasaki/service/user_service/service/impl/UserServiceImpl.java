package com.kawasaki.service.user_service.service.impl;

import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.exception.BizExceptionCodeEnum;
import com.kawasaki.service.user_service.dto.CreateUserRequestDTO;
import com.kawasaki.service.user_service.model.UserExample;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kawasaki.service.user_service.mapper.UserMapper;
import com.kawasaki.service.user_service.model.User;
import com.kawasaki.service.user_service.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> getAllUsers() {
        return userMapper.selectByExample(new UserExample());
    }

    @Override
    public User findByEmail(String email) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        criteria.andEmailEqualTo(email);

        List<User> result = userMapper.selectByExample(example);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public User createUser(CreateUserRequestDTO createUserRequestDTO) {
        // check if email exists
        User existingUser = findByEmail(createUserRequestDTO.getEmail());
        if (!Objects.isNull(existingUser)) {
            throw new BizException(BizExceptionCodeEnum.EMAIL_EXISTS);
        }

        User user = new User();
        BeanUtils.copyProperties(createUserRequestDTO, user);
        user.setCreatedAt(new Date(System.currentTimeMillis()));
        user.setUpdatedAt(new Date(System.currentTimeMillis()));

        int rows = userMapper.insert(user);
        if (rows != 1) {
            throw new BizException(BizExceptionCodeEnum.DB_INSERT_FAILED);
        }

        return user;
    }


}
