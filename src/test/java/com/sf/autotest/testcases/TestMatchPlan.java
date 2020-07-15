package com.sf.autotest.testcases;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sf.autotest.common.HttpRequest;
import com.sf.autotest.common.Ipt;
import com.sf.autotest.common.SendData;
import com.sf.autotest.common.sfContant;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * @Description
 * @Author wangmengmeng
 * @Date 2020-07-15 11:19
 */

public class TestMatchPlan {
    private HttpRequest httpRequest;
    private SendData sendData;
    private Ipt ipt;

    @BeforeClass
    public void beforeClass() {
        httpRequest = new HttpRequest();
    }

    @BeforeMethod
    public void beforeMethod() {
        sendData = new SendData();
        ipt = new Ipt();
    }

    @Parameters({"planId"})
    @Test
    public void test01(int planId) {
        //修改审方方案--方案过滤条件为空
        //开具医嘱，并断言
        JSONObject plan = new JSONObject();
        JSONArray deptList = new JSONArray();
        JSONArray groupList = new JSONArray();
        JSONArray iptWardList = new JSONArray();
        ArrayList<Object> weekList = new ArrayList<>();
        JSONArray doctorList = new JSONArray();
        plan.put("id", planId);
        plan.put("name", "住院修改");
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
        JSONObject res = httpRequest.doPutJson(sfContant.auditcenter_url + "/api/v1/auditPlan/" + planId, plan);
        sendData.sendXml("ipt.xml");
        JSONObject response = ipt.selNotAuditIptList(sendData.changeData.get("{{zyhzh}}"));
        JSONArray array = JSONObject.parseObject(response.getString("data")).getJSONArray("engineInfos");
        Assert.assertNotNull(array);
    }

    @Parameters({"planId"})
    @Test
    public void test02(int planId) {
        //修改审方方案--方案过滤条件为 仅审核PIVAS医嘱
        //开具医嘱，并断言
        JSONObject plan = new JSONObject();
        JSONArray deptList = new JSONArray();
        JSONArray groupList = new JSONArray();
        JSONArray iptWardList = new JSONArray();
        ArrayList<Object> weekList = new ArrayList<>();
        JSONArray doctorList = new JSONArray();
        plan.put("id", planId);
        plan.put("name", "住院修改");
        plan.put("category", 2);
        plan.put("createdTime", 1594739619244L);
        plan.put("modifiedTime", 1594739619244L);
        plan.put("recipeSource", "0");
        plan.put("deptList", deptList);
        plan.put("groupList", groupList);
        plan.put("isOuvas", 0);
        plan.put("isPivas", 1);
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
        JSONObject res = httpRequest.doPutJson(sfContant.auditcenter_url + "/api/v1/auditPlan/" + planId, plan);
        sendData.sendXml("ipt.xml");
        JSONObject response = ipt.selNotAuditIptList(sendData.changeData.get("{{zyhzh}}"));
        JSONArray array = JSONObject.parseObject(response.getString("data")).getJSONArray("engineInfos");
        Assert.assertNotNull(array);
    }
    @Parameters({"planId"})
    @Test
    public void test03(int planId) {
        //修改审方方案--方案过滤条件为 不勾选仅审核PIVAS医嘱
        //开具医嘱，并断言
        JSONObject plan = new JSONObject();
        JSONArray deptList = new JSONArray();
        JSONArray groupList = new JSONArray();
        JSONArray iptWardList = new JSONArray();
        ArrayList<Object> weekList = new ArrayList<>();
        JSONArray doctorList = new JSONArray();
        plan.put("id", planId);
        plan.put("name", "住院修改");
        plan.put("category", 2);
        plan.put("createdTime", 1594739619244L);
        plan.put("modifiedTime", 1594739619244L);
        plan.put("recipeSource", "0");
        plan.put("deptList", deptList);
        plan.put("groupList", groupList);
        plan.put("isOuvas", 0);
        plan.put("isPivas", 1);
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
        JSONObject res = httpRequest.doPutJson(sfContant.auditcenter_url + "/api/v1/auditPlan/" + planId, plan);
        sendData.sendXml("ipt.xml");
        JSONObject response = ipt.selNotAuditIptList(sendData.changeData.get("{{zyhzh}}"));
        JSONArray array = JSONObject.parseObject(response.getString("data")).getJSONArray("engineInfos");
        Assert.assertNotNull(array);

    }
}
