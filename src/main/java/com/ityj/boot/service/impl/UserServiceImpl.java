package com.ityj.boot.service.impl;

import com.ityj.boot.entity.User;
import com.ityj.boot.mapper.UserMapper;
import com.ityj.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }
}
