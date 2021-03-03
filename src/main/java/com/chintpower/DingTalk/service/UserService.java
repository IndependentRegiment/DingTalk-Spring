package com.chintpower.DingTalk.service;

import com.chintpower.DingTalk.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserService {
    List<User> getAllUserService();
}
