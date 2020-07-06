package com.sf.autotest.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.ir.ReturnNode;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Ipt {
    public HttpRequest httpRequest = new HttpRequest();
    private final Logger log = LoggerFactory.getLogger(Ipt.class);
    public SendData sd = new SendData();



    /**
     * @param pid 患者号
     * @return 根据患者号查询住院待审列表
     */
    public JSONObject selNotAuditIptList(String pid) {
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("patientId", pid);
//        hashMap.put("patientId", pid);
        System.out.println(jsonObject);
        return httpRequest.doPostJson(sfContant.auditcenter_url + "/api/v1/ipt/selNotAuditIptList", jsonObject);

    }


    /**
     * @param pid 患者号
     * @param n   同患者的第几个引擎id
     * @return 待审列表获取引擎id, 如果某患者有多条待审任务则会有多个引擎id，n代表取第几个引擎id
     */
    public String getEngineid(String pid, int n) {
        JSONObject res = selNotAuditIptList(pid);
        System.out.println(res);
        Object engineid = JSONObject.parseObject(res.getString("data")).getJSONArray("engineInfos").getJSONObject(n - 1).get("id");
//        System.out.println(engineId);
        String engineId = (String) engineid;
        return engineId;
    }

    public JSONObject auditBatchAgree(Integer... engineIds) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ids", engineIds);
        jsonObject.put("auditType", 3);
        jsonObject.put("auditWay", 2);
        return httpRequest.doPostJson(sfContant.auditcenter_url + "/api/v1/auditBatchAgree", jsonObject);
    }

    public JSONObject iptAudit(String gp, String engineId, int auditType) {
        JSONObject jobj = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject groupOrderList = new JSONObject();
        groupOrderList.put("auditBoList", new JSONArray());
        groupOrderList.put("groupNo", gp);
        groupOrderList.put("engineId", engineId);
        groupOrderList.put("orderType", 1);
        if (auditType == 0) {
            groupOrderList.put("auditInfo", "必须修改");
            groupOrderList.put("auditStatus", 0);
        } else if (auditType == 1) {
            groupOrderList.put("auditInfo", "打回可双签");
            groupOrderList.put("auditStatus", 0);
        } else if (auditType == 2) {
            groupOrderList.put("auditStatus", 1);
        }
        array.add(groupOrderList);
        jobj.put("groupOrderList", array);
        jobj.put("auditType", 1);
        jobj.put("auditWay", 2);
        System.out.println(jobj);
        return httpRequest.doPostJson(sfContant.auditcenter_url + "/api/v1/ipt/auditSingle", jobj);

    }

    public JSONObject orderList(String engineId, int type) {
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        list.add(new BasicNameValuePair("id", engineId));
        if (type == 0) {
            return httpRequest.doGetwithParam(sfContant.auditcenter_url + "/api/v1/ipt/orderList", list);
        } else {
            return httpRequest.doGetwithParam(sfContant.auditcenter_url + "/api/v1/ipt/all/orderList", list);
        }
    }

    public JSONObject herbOrderList(String engineId, int type) {
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        list.add(new BasicNameValuePair("id", engineId));
        if (type == 0) {
            return httpRequest.doGetwithParam(sfContant.auditcenter_url + "/api/v1/ipt/herbOrderList", list);
        } else {
            return httpRequest.doGetwithParam(sfContant.auditcenter_url + "/api/v1/ipt/all/herbOrderList", list);
        }
    }


    public JSONObject mergeEngineMsgList(String engineId, String gp, int type) {
        JSONObject orderList = orderList(engineId, type);
        ArrayList<Object> medicalIds = new ArrayList<>();
        ArrayList<Object> medicalHisIds = new ArrayList<>();
        ArrayList<Object> herbMedicalIds = new ArrayList<>();
        ArrayList<Object> herbMedicalHisIds = new ArrayList<>();
        for (Iterator<Object> iterator = JSONObject.parseObject(orderList.getString("data")).getJSONArray(gp).iterator(); iterator.hasNext(); ) {
            JSONObject next = (JSONObject) iterator.next();
            medicalHisIds.add(next.getString("id"));
        }
        for (Iterator<Object> iterator = JSONObject.parseObject(orderList.getString("data")).getJSONArray(gp).iterator(); iterator.hasNext(); ) {
            JSONObject next = (JSONObject) iterator.next();
            medicalHisIds.add(next.getString("orderId"));
        }
        JSONObject body = new JSONObject();
        body.put("auditWay", 2);
        body.put("engineId", engineId);
        body.put("zoneId", getZoneId());
        body.put("groupNo", gp);
        body.put("medicalIds", medicalIds);
        body.put("medicalHisIds", medicalHisIds);
        body.put("herbMedicalIds", herbMedicalIds);
        body.put("herbMedicalHisIds", herbMedicalHisIds);
        if (type == 0) {
            return httpRequest.doPostJson(sfContant.auditcenter_url + "/api/v1/ipt/mergeEngineMsgList", body);
        } else {
            return httpRequest.doPostJson(sfContant.auditcenter_url + "/api/v1/ipt/all/mergeEngineMsgList", body);
        }

    }


    private String getZoneId() {
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        String sql = "SELECT zone_id FROM medical_org WHERE hospital_code = ?";
        Map<String, Object> map = template.queryForMap(sql, "H0003");
        System.out.println(map);
        Object zone_id = map.get("zone_id");
        return String.valueOf(zone_id);
    }

    @Test
    public void getID() {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject jobj1 = new JSONObject();
        jobj1.put("a", 3);
        jobj1.put("b", 4);
        JSONObject jobj2 = new JSONObject();
        jobj2.put("a", 5);
        jobj2.put("b", 6);
        array.add(jobj1);
        array.add(jobj2);

        jsonObject.put("A", 1);
        jsonObject.put("B", 2);
        jsonObject.put("C", array);
        System.out.println(jsonObject);
        ArrayList<Object> list = new ArrayList<>();
        for (Object key : jsonObject.getJSONArray("C")) {
           JSONObject js =  (JSONObject)key;

            System.out.println(key);
        }

        for (Iterator<Object> iterator = jsonObject.getJSONArray("C").iterator(); iterator.hasNext(); ) {
            JSONObject next = (JSONObject) iterator.next();
//            System.out.println("pic ===>>> " + next.getString("a"));
            list.add(next.getString("a"));
            System.out.println(list);
        }

    }


    public static void main(String[] args) {
        SendData sendData = new SendData();
        String s = sendData.sendXml("ipt.xml");
        Ipt ipt = new Ipt();
        String engineid = ipt.getEngineid(sendData.changeData.get("{{zyhzh}}"), 1);
//        ipt.orderList(String.valueOf(engineid), 0);
        ipt.mergeEngineMsgList(engineid,sendData.changeData.get("{{gp}}"),0);
        String a = "test";
//        ipt.iptAudit(sendData.changeData.get("{{gp}}"), String.valueOf(engineid), 0);
//        JSONObject ss = ipt.postselNotAuditIptList(sendData.changeData.get("{{zyhzh}}"));
//        System.out.println(ss);
//        .replaceAll("null", "\"\"")

//        String data = ss.getString("data");
//        JSONObject json2 = JSONObject.parseObject(data);
//        JSONArray taskNumList = json2.getJSONArray("taskNumList");
//        System.out.println(taskNumList);
//        System.out.println(taskNumList.getJSONObject(0).get("zoneName"));
    }
}
