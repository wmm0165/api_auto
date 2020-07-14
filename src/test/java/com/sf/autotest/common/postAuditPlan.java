package com.sf.autotest.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author wangmengmeng
 * @Date 2020-07-14 18:02
 */
public class postAuditPlan {
    private HttpRequest httpRequest = new HttpRequest();


    public void postAuditPlan() {
        JSONObject plan = new JSONObject();
        JSONArray deptList = new JSONArray();
        JSONArray groupList = new JSONArray();
        JSONArray iptWardList = new JSONArray();
        ArrayList<Object> weekList = new ArrayList<>();
        JSONArray doctorList = new JSONArray();
        plan.put("name", "住院");
        plan.put("category", 2);
        plan.put("createdTime", 1594739619244L);
        plan.put("modifiedTime", 1594739619244L);
        plan.put("recipeSource", "0");
        plan.put("deptList", deptList);
        plan.put("groupList", groupList);
        plan.put("isOuvas", 0);
        plan.put("isPivas", 0);
        plan.put("minStay", "");
        plan.put("maxStay", "");
        plan.put("minAge", "");
        plan.put("maxAge", "");
        plan.put("ageUnit", "岁");
        plan.put("costTypes", "");
        plan.put("patientCondition", "");
        plan.put("iptWardList", iptWardList);
        plan.put("weekList", weekList);
        plan.put("doctorList", doctorList);
        plan.put("startTime", "00:00");
        plan.put("endTime", "23:59");
        JSONObject res = httpRequest.doPostJson(sfContant.auditcenter_url + "/api/v1/auditPlan", plan);
        System.out.println(res);

    }

    public static void main(String[] args) {
        postAuditPlan auditPlan = new postAuditPlan();
        auditPlan.postAuditPlan();
    }

}
