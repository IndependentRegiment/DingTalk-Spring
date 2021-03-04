package com.chintpower.DingTalk.Utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chintpower.DingTalk.entity.UserWithDepts;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiSmartworkHrmEmployeeQueryonjobRequest;
import com.dingtalk.api.request.OapiV2UserListRequest;
import com.dingtalk.api.response.OapiSmartworkHrmEmployeeQueryonjobResponse;
import com.dingtalk.api.response.OapiV2UserListResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


public class UserUtil {
    /**
     * 用来获取当前所有的在职的员工列表
     *
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

    /**
     * 获取指定部门的所有人的信息, 并且不会递归调用这个部门的子部门
     * 注意，一个人可能有很多个部门Id
     * 比如查询财务信息管理部的时候，只会查询出一个人，其他的人都分类在了财务信息管理部的子部门里面
     *
     * 查询的Id为: 163034128
     * 012039413568788585, 张莉, [163034225, 459514659, 163034128], true
     *
     * @param dingTalkToken 钉钉开发的Token
     * @param depId 部门ID
     * @return 用户对象的数组
     *
     *
     */
    public static ArrayList<UserWithDepts> getDepUsersInfo(String dingTalkToken, String depId) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/list");
            OapiV2UserListRequest req = new OapiV2UserListRequest();
            req.setDeptId(Long.parseLong(depId));
            req.setCursor(0L);
            req.setSize(40L);
            req.setLanguage("zh_CN");
            OapiV2UserListResponse rsp = client.execute(req, dingTalkToken);
            JSONObject jsonObject = JSONObject.parseObject(rsp.getBody());  // 获得JSON格式的结果。
            JSONObject resultObj = jsonObject.getJSONObject("result");
            Boolean has_more = resultObj.getBoolean("has_more");
            long next_cursor;
            if (has_more) {
                next_cursor = resultObj.getLong("next_cursor");
            } else {
                // has_more是false，next_cursor在后面不因该被调用，为了后面的while(hash_more)通过编译
                // 在此将其初始化为0L;
                next_cursor = 0L;
            }
            var resList = new ArrayList<UserWithDepts>();  // 放最终的结果
            JSONArray ObjList = resultObj.getJSONArray("list");
            Iterator<Object> integer = ObjList.iterator();
            while (integer.hasNext()) {
                JSONObject userObj = JSONObject.parseObject(integer.next().toString());
                /**
                 * 获取部门id列表
                 */
                // 这里出现Bug了
                // 进行分段修改
                // Arrays.asList(userObj.getJSONArray("dept_id_list").toArray());
                var deptList = new ArrayList<String>();
                Object[] deptIdListArray = userObj.getJSONArray("dept_id_list").toArray();  // // 将JSONArray转成Object[]
                for(Object obj : deptIdListArray) {
                    deptList.add(obj.toString());
                }
                UserWithDepts user = new UserWithDepts(
                        userObj.getString("userid"),
                        userObj.getString("name"),
                        deptList,
                        String.valueOf(true)
                );
                resList.add(user);
            }
            // 通过has_more和next_cursor获取所有的成员
            while (has_more) {
                req.setDeptId(Long.parseLong(depId));
                req.setCursor(next_cursor);
                req.setLanguage("zh_CN");
                rsp = client.execute(req, dingTalkToken);
                jsonObject = JSONObject.parseObject(rsp.getBody());  // 获得JSON格式的结果。
                resultObj = jsonObject.getJSONObject("result");
                has_more = resultObj.getBoolean("has_more");
                if (has_more) {
                    next_cursor = resultObj.getLong("next_cursor");
                } else {
                    // has_more是false，next_cursor在后面不因该被调用，为了后面的while(hash_more)通过编译
                    // 在此将其初始化为0L;
                    next_cursor = 0L;
                }
                ObjList = resultObj.getJSONArray("list");
                integer = ObjList.iterator();
                while (integer.hasNext()) {
                    JSONObject userObj = JSONObject.parseObject(integer.next().toString());
                    /**
                     * 获取部门id列表
                     */
                    // 这里出现Bug了
                    // 进行分段修改
                    // Arrays.asList(userObj.getJSONArray("dept_id_list").toArray());
                    Object[] deptIdListArray = userObj.getJSONArray("dept_id_list").toArray();
                    var deptList = new ArrayList<String>();
                    for (Object obj : deptIdListArray) {
                        deptList.add(obj.toString());
                    }
                    UserWithDepts user = new UserWithDepts(
                            userObj.getString("userid"),
                            userObj.getString("name"),
                            deptList,
                            String.valueOf(true)
                    );
                    resList.add(user);
                }
            }
            return resList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        var userWithDeptArrayList = UserUtil.getDepUsersInfo("b2015b8a651838949746dc6130e2988f", "163034128");
        for (UserWithDepts user : userWithDeptArrayList) {
            System.out.println(user.getUserId() + ", " + user.getName() + ", " + user.getDepIdList() + ", " + user.getEmployStatus());
        }
        System.out.println(userWithDeptArrayList.size());
    }
}
