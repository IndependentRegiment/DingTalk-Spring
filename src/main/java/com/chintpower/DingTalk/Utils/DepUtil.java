package com.chintpower.DingTalk.Utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chintpower.DingTalk.entity.Department;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiDepartmentListRequest;
import com.dingtalk.api.response.OapiDepartmentListResponse;

import java.util.ArrayList;
import java.util.Iterator;

public class DepUtil {
    public ArrayList<Department> getRootDepList(String dingTalkToken) {
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/department/list");
            OapiDepartmentListRequest req = new OapiDepartmentListRequest();
            req.setLang("zh_CN");
            req.setFetchChild(false);
            req.setId("1");
            req.setHttpMethod("GET");
            OapiDepartmentListResponse rsp = client.execute(req, dingTalkToken);
            var jsonObject = JSONObject.parseObject(rsp.getBody());
            JSONArray depObjArrayList = jsonObject.getJSONArray("department");
            var resList = new ArrayList<Department>();
            Iterator iterator = depObjArrayList.iterator();  // 创建迭代器。
            while(iterator.hasNext()) {
                Object obj = iterator.next();
                JSONObject subJsonObj = JSONObject.parseObject(obj.toString());
                String name = subJsonObj.getString("name");
                String id = subJsonObj.getString("id");
                String parentId = subJsonObj.getString("parentid");
                Department dep = new Department(name, id, parentId);
                resList.add(dep);
            }
            return resList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        ArrayList<Department> depList = new DepUtil().getRootDepList("b2015b8a651838949746dc6130e2988f");
        for (Department depObj : depList) {
            System.out.println(depObj.getName() + " ," + depObj.getId() + " ," + depObj.getParentId());
        }
    }
}
