package com.chintpower.DingTalk.controller;

import com.chintpower.DingTalk.entity.User;
import com.chintpower.DingTalk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping(value = "/api1.0/user", method = RequestMethod.GET)
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/all")
    public List<User> getAllUsersController() {
        List<User> userList = userService.getAllUserService();
        return userList;
//        for(User user : userList ) {
//            System.out.println(user.getUserId() + " ," + user.getName() + " ," + user.getDepId() + " ," + user.getEmploystatus());
//        }
//        return "获取所有的员工的列表";
    }

    @RequestMapping("/test")
    public Object getUserByToken() {
        Object json = userService.getJSONFromToken();
        return json;
    }

}
