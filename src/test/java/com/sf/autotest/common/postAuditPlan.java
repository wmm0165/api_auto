package com.sf.autotest.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @Description
 * @Author wangmengmeng
 * @Date 2020-07-14 18:02
 */
public class postAuditPlan {
    private HttpRequest httpRequest = new HttpRequest();

    public void postAuditPlan(){
        JSONObject plan = new JSONObject();
        plan.put("name","住院");
        plan.put("category","");
        plan.put("createdTime","");
        plan.put("modifiedTime","");
        plan.put("recipeSource","");
        plan.put("deptList","");
        plan.put("groupList","");
        plan.put("isOuvas","");
        plan.put("isPivas","");
        plan.put("minStay","");
        plan.put("maxStay","");
        plan.put("minAge","");
        plan.put("maxAge","");
        plan.put("ageUnit","");
        plan.put("costTypes","");
        plan.put("patientCondition","");
        plan.put("iptWardList","");
        plan.put("weekList","");
        plan.put("doctorList","");
        plan.put("startTime","");
        plan.put("endTime","");



    }

}
