package com.chintpower.DingTalk.Utils;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.taobao.api.ApiException;

/**
 * 调用钉钉的Java SDK
 *
 */
public class TokenUtil {
    public static String getAccessToken() {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
            OapiGettokenRequest req = new OapiGettokenRequest();
            req.setAppkey("dingxcmroibxpsmybjm8");
            req.setAppsecret("3G15m6PkqehY5LUdqSP7jkqPURVWa7yjsgY-OlIyRQ77FQD_3iMI7epqPnBMWJtA");
            req.setHttpMethod("GET");
            OapiGettokenResponse rsp = client.execute(req);
            var jsonObject = JSONObject.parseObject(rsp.getBody());
            return jsonObject.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String token = TokenUtil.getAccessToken();
        System.out.println(token);
    }


}
