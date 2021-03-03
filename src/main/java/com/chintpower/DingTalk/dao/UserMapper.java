package com.chintpower.DingTalk.dao;

import com.chintpower.DingTalk.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    // 获取所有的用户的信息
    List<User> getAllUsers();
}
