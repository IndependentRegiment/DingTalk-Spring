package com.chintpower.DingTalk.Test;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiSmartworkHrmEmployeeQueryonjobRequest;
import com.dingtalk.api.response.OapiSmartworkHrmEmployeeQueryonjobResponse;
import com.taobao.api.ApiException;

public class TestDingTalk {

    public static void main(String[] args) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/smartwork/hrm/employee/queryonjob");
            OapiSmartworkHrmEmployeeQueryonjobRequest req = new OapiSmartworkHrmEmployeeQueryonjobRequest();
            req.setStatusList("3");
            req.setOffset(0L);
            req.setSize(10L);
            OapiSmartworkHrmEmployeeQueryonjobResponse rsp = client.execute(req, "270ad14913a634e7bb552d053a3b9299");
            System.out.println(rsp.getBody());
        } catch (ApiException e) {
            e.printStackTrace();
        }

    }
}
