package com.sf.autotest.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import jdk.nashorn.internal.ir.ReturnNode;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SystemPropertyUtils;

import java.io.IOException;
import java.util.HashMap;

import static java.lang.Thread.sleep;

public class Ipt {
    public HttpRequest httpRequest = new HttpRequest();
    private final Logger log = LoggerFactory.getLogger(Ipt.class);

    /**
     * @param pid 患者号
     * @return 根据患者号查询住院待审列表
     */
    public JSONObject postselNotAuditIptList(String pid) {
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
    public Object getEngineid(String pid, int n) {
        JSONObject res = postselNotAuditIptList(pid);
        System.out.println(res);
        Object engineId = JSONObject.parseObject(res.getString("data")).getJSONArray("engineInfos").getJSONObject(n - 1).get("id");
        System.out.println(engineId);
        return engineId;
    }

//    public JSONObject iptAudit(String gp,String engineId,String auditType){
//        HashMap<Object, Object> hashMap = new HashMap<>();
////        hashMap.put("patientId", pid);
//        return httpRequest.doPostJson(sfContant.auditcenter_url + "/api/v1/ipt/selNotAuditIptList", hashMap);
//
//    }

    public static void main(String[] args) {
        SendData sendData = new SendData();
        String s = sendData.sendXml("ipt.xml");
        Ipt ipt = new Ipt();
        ipt.getEngineid(sendData.changeData.get("{{zyhzh}}"), 1);
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
