package com.chintpower.DingTalk.service;

import com.chintpower.DingTalk.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;



public interface UserService {
    List<User> getAllUserService();
    Object getJSONFromToken();
}
