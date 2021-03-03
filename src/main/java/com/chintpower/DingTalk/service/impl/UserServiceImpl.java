package com.chintpower.DingTalk.service.impl;

import com.chintpower.DingTalk.dao.UserMapper;
import com.chintpower.DingTalk.entity.User;
import com.chintpower.DingTalk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAllUserService() {
        List<User> userList = userMapper.getAllUsers();
        return userList;
    }
}
