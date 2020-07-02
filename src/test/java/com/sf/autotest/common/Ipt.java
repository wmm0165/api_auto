package com.sf.autotest.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static void main(String[] args) {
        SendData sendData = new SendData();
        String s = sendData.sendXml("ipt.xml");
        Ipt ipt = new Ipt();
        Object engineid = ipt.getEngineid(sendData.changeData.get("{{zyhzh}}"), 1);
        ipt.iptAudit(sendData.changeData.get("{{gp}}"), String.valueOf(engineid), 0);
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
