package com.chintpower.DingTalk.Utils;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiSmartworkHrmEmployeeQueryonjobRequest;
import com.dingtalk.api.response.OapiSmartworkHrmEmployeeQueryonjobResponse;

import java.util.ArrayList;
import java.util.Arrays;


public class UserUtil {
    /**
     * 用来获取当前所有的在职的员工列表
     * @param dingTalkToken 钉钉的token
     * @return 在职员工的Id的数组
     */
    public static ArrayList<Object> getOnJobUserList(String dingTalkToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/smartwork/hrm/employee/queryonjob");
            OapiSmartworkHrmEmployeeQueryonjobRequest req = new OapiSmartworkHrmEmployeeQueryonjobRequest();
            req.setStatusList("3");
            req.setOffset(0L);
            req.setSize(40L);
            OapiSmartworkHrmEmployeeQueryonjobResponse rsp = client.execute(req, dingTalkToken);
            var jsonObject = JSONObject.parseObject(rsp.getBody());

            var resList = new ArrayList<>();  // 放最终的结果

            var next_cursorLong = jsonObject.getJSONObject("result").getLong("next_cursor");
            Object[] data_list = jsonObject.getJSONObject("result").getJSONArray("data_list").toArray();
            resList.addAll(Arrays.asList(data_list));

            // 测试导入数据到resList中

            while (/*null != next_cursorLong*/ true) {
                /**
                 * 再次循环发送请求调用Api获取数据
                 */
                req.setOffset(next_cursorLong);
                req.setSize(40L);
                rsp = client.execute(req, dingTalkToken);
                jsonObject = JSONObject.parseObject(rsp.getBody());
                if (null == jsonObject.getJSONObject("result").getLong("next_cursor")) {
                    break;
                }
                next_cursorLong = jsonObject.getJSONObject("result").getLong("next_cursor");
                data_list = jsonObject.getJSONObject("result").getJSONArray("data_list").toArray();
                resList.addAll(Arrays.asList(data_list));
            }
            return resList;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static void main(String[] args) {
        var res = UserUtil.getOnJobUserList("ccf08970723d3604a01d2c024adc0342");
        for (Object obj : res) {
            System.out.println(obj);
        }
        System.out.println(res.size());
    }
}
