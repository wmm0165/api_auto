package com.sf.autotest.testcases;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sf.autotest.common.AlterConfig;
import com.sf.autotest.common.Ipt;
import com.sf.autotest.common.SendData;
import com.sf.autotest.dataProvider.IptStopProvider;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FilenameFilter;

public class TestIptStop {
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

    @DataProvider(name = "stop_1")
    public Object[][] createData() {
        return new Object[][]{{"audit771_44"}, {"audit771_44_2"}};
    }

//    @Parameters({"filename"})
//    @Test(dataProvider = "stop_1")
    @Test(dataProvider = "stop_1",dataProviderClass = IptStopProvider.class)
    public void test_stop_1(String filename) {
        //失效时间大于(当前时间+120),这里的测试数据失效时间为当前时间+180分钟，产生任务
        sendData.sendXml(filename);
        sendData.sendXml(filename);
        JSONObject response = ipt.selNotAuditIptList(sendData.changeData.get("{{zyhzh}}"));
        JSONArray array = JSONObject.parseObject(response.getString("data")).getJSONArray("engineInfos");
        int size = array.size();
        Assert.assertNotNull(array);
        Assert.assertEquals(size,1); //即使重复传也只产生一个任务
    }

    @Test(dataProvider = "stop_2",dataProviderClass = IptStopProvider.class)
    public void test_stop_2(String filename) {
        //长期医嘱/临时医嘱，失效时间小于(当前时间+120),这里的测试数据失效时间为当前时间+60分钟，不产生任务
        //长期医嘱/临时医嘱，失效时间小于当前时间,这里的测试数据失效时间为当前时间-60分钟，不产生任务
        sendData.sendXml(filename);
        sendData.sendXml(filename);
        JSONObject response = ipt.selNotAuditIptList(sendData.changeData.get("{{zyhzh}}"));
        JSONArray array = JSONObject.parseObject(response.getString("data")).getJSONArray("engineInfos");
        Assert.assertNull(array);
        //        String engineid = ipt.getEngineid(sendData.changeData.get("{{zyhzh}}"), 1);
//        ipt.iptAudit(sendData.changeData.get("{{gp}}"), String.valueOf(engineid), 0);
    }

    public void test_stop_3() {
        //长期医嘱/临时医嘱，失效时间小于(当前时间+120),这里的测试数据失效时间为当前时间+60分钟，不产生任务
        //长期医嘱/临时医嘱，失效时间小于当前时间,这里的测试数据失效时间为当前时间-60分钟，不产生任务
        sendData.sendXml("audit771_46");
        sendData.sendXml("audit771_46");
        JSONObject response = ipt.selNotAuditIptList(sendData.changeData.get("{{zyhzh}}"));
        JSONArray array = JSONObject.parseObject(response.getString("data")).getJSONArray("engineInfos");
        Assert.assertNull(array);
        //        String engineid = ipt.getEngineid(sendData.changeData.get("{{zyhzh}}"), 1);
//        ipt.iptAudit(sendData.changeData.get("{{gp}}"), String.valueOf(engineid), 0);
    }

    @Test
    public void testStop_13() {
        //开具医嘱，批量通过后，传停嘱（只修改了失效时间，失效时间>当前时间+配置时间），即使有效也不产生待审任务，后续会被合并
        sendData.sendXml("audit771_15");
        String engineid = ipt.getEngineid(sendData.changeData.get("{{ts}}"), 1);
        ipt.auditBatchAgree(engineid);
        sendData.sendXml("audit771_44");
        sendData.sendXml("audit771_44");
        sendData.sendXml("audit771_15");
        JSONObject response = ipt.selNotAuditIptList(sendData.changeData.get("{{ts}}"));
        JSONArray array = JSONObject.parseObject(response.getString("data")).getJSONArray("engineInfos");
        Assert.assertNull(array);
        sendData.sendXml("audit771_16");
        String engineid1 = ipt.getEngineid(sendData.changeData.get("{{ts}}"), 1);
        JSONObject orderList0 = ipt.orderList(engineid1, 0);
        String expected = sendData.changeData.get("{{tb180}}");
        String actual0 = JSONObject.parseObject(orderList0.getString("data")).getJSONArray(sendData.changeData.get("{{gp}}")).getJSONObject(0).getString("orderInvalidTime");
        Assert.assertEquals(actual0,expected);
        ipt.auditBatchAgree(engineid1);
        JSONObject orderList1 = ipt.orderList(engineid1, 1);
        String actual1 = JSONObject.parseObject(orderList1.getString("data")).getJSONArray(sendData.changeData.get("{{gp}}")).getJSONObject(0).getString("orderInvalidTime");
        Assert.assertEquals(actual1,expected);

    }

    @Test
    public void testStop_14() {
        //开具医嘱，审核打回后，传停嘱（只修改了失效时间，失效时间<当前时间+配置时间），停嘱不产生待审任务，且后续不会被合并
        sendData.sendXml("audit771_15");
        String engineid = ipt.getEngineid(sendData.changeData.get("{{ts}}"), 1);
//        ipt.auditBatchAgree(engineid);
        ipt.iptAudit(sendData.changeData.get("{{gp}}"),engineid,1);
        sendData.sendXml("audit771_45");
        JSONObject response = ipt.selNotAuditIptList(sendData.changeData.get("{{ts}}"));
        JSONArray array = JSONObject.parseObject(response.getString("data")).getJSONArray("engineInfos");
        Assert.assertNull(array);
        sendData.sendXml("audit771_16");
        String engineid1 = ipt.getEngineid(sendData.changeData.get("{{ts}}"), 1);
        JSONObject orderList = ipt.orderList(engineid1, 0);
        Assert.assertEquals(orderList.getString("data"),"{}");
    }
}
