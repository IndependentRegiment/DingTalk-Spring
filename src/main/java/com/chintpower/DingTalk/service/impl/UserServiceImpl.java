package com.chintpower.DingTalk.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chintpower.DingTalk.dao.UserMapper;
import com.chintpower.DingTalk.entity.User;
import com.chintpower.DingTalk.service.UserService;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiSmartworkHrmEmployeeQueryonjobRequest;
import com.dingtalk.api.response.OapiSmartworkHrmEmployeeQueryonjobResponse;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAllUserService() {
        List<User> userList = userMapper.getAllUsers();
        return userList;
    }

    /**
     * 测试代码，后续不再使用
     * {"errcode":0,"errmsg":"ok","result":{"data_list":["01232107672623020263","2731070253710408","114013212138013510","046928540629098789","186611423720155714","012319562538776658","153210405024247712","256632251824728884","01082513195934342523","073257343020186703"],"next_cursor":10},"success":true,"request_id":"5dlenjfyhksz"}
     *
     * @return
     */
    @Override
    public Object getJSONFromToken() {
        JSONObject jsonObject = null;
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/smartwork/hrm/employee/queryonjob");
            OapiSmartworkHrmEmployeeQueryonjobRequest req = new OapiSmartworkHrmEmployeeQueryonjobRequest();
            req.setStatusList("3");
            req.setOffset(0L);
            req.setSize(10L);
            OapiSmartworkHrmEmployeeQueryonjobResponse rsp = client.execute(req, "270ad14913a634e7bb552d053a3b9299");
            var res = rsp.getBody();
            jsonObject = JSONObject.parseObject(res);
//            System.out.println(jsonObject.getString("msg"));
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
