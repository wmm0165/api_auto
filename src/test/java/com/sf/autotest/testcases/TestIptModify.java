package com.sf.autotest.testcases;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sf.autotest.common.AlterConfig;
import com.sf.autotest.common.Ipt;
import com.sf.autotest.common.SendData;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @Description
 * @Author wangmengmeng
 * @Date 2020-07-06 14:43
 */
public class TestIptModify {
    private SendData sendData;
    private Ipt ipt;

    @BeforeClass
    public void beforeClass() {
        AlterConfig.alterConfig("40003", "1");
        AlterConfig.alterConfig("40106", "120");
    }

    @BeforeMethod
    public void beforeMethod() {
        sendData = new SendData();
        ipt = new Ipt();
    }

    @Test
    public void testModify01() {
        //(AUDIT-1213)新增(2个药)-审核打回，传修改（共2个药，但是只修改了其中一个药），产生待审任务（包含2个药）
        sendData.sendXml("audit771_36");
        String engineid = ipt.getEngineid(sendData.changeData.get("{{zyhzh}}"), 1);
//        ipt.auditBatchAgree(engineid);
        ipt.iptAudit(sendData.changeData.get("{{gp}}"),engineid,1);
        sendData.sendXml("audit771_37");
        JSONObject response = ipt.selNotAuditIptList(sendData.changeData.get("{{zyhzh}}"));
        JSONArray array = JSONObject.parseObject(response.getString("data")).getJSONArray("engineInfos");
        Assert.assertNotNull(array);
        String engineid1 = ipt.getEngineid(sendData.changeData.get("{{zyhzh}}"), 1);
        JSONObject orderList0 = ipt.orderList(engineid1, 0);
        int actual0 = JSONObject.parseObject(orderList0.getString("data")).getJSONArray(sendData.changeData.get("{{gp}}")).size();
        Assert.assertEquals(actual0,2);
        ipt.auditBatchAgree(engineid1);
        JSONObject orderList1 = ipt.orderList(engineid1, 1);
        int actual1 = JSONObject.parseObject(orderList1.getString("data")).getJSONArray(sendData.changeData.get("{{gp}}")).size();
        Assert.assertEquals(actual1,2);

    }

    @Test
    public void testModify02() {
        //临时医嘱新增-审核打回，重复传，不产生待审任务，且已审页面只展示审核记录，不会展示修改记录
        sendData.sendXml("audit771_36");
        String engineid = ipt.getEngineid(sendData.changeData.get("{{zyhzh}}"), 1);
//        ipt.auditBatchAgree(engineid);
        ipt.iptAudit(sendData.changeData.get("{{gp}}"),engineid,1);
        sendData.sendXml("audit771_36");
        JSONObject response = ipt.selNotAuditIptList(sendData.changeData.get("{{zyhzh}}"));
        JSONArray array = JSONObject.parseObject(response.getString("data")).getJSONArray("engineInfos");
        Assert.assertNull(array);
        JSONObject engineMsgList = ipt.mergeEngineMsgList(sendData.changeData.get("{{gp}}"), engineid, 1);
        int size = JSONObject.parseObject(engineMsgList.getString("data")).getJSONArray("groupAudits").size();
        Assert.assertEquals(size,1);
    }
}
