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
        JSONObject response = ipt.selNotAuditIptList(sendData.changeData.get("{{zyhzh}}"));
        JSONArray array = JSONObject.parseObject(response.getString("data")).getJSONArray("engineInfos");
        System.out.println(array);
        Assert.assertNotNull(array);
    }

    @Test(dataProvider = "stop_2",dataProviderClass = IptStopProvider.class)
    public void test_stop_2(String filename) {
        //长期医嘱/临时医嘱，失效时间小于(当前时间+120),这里的测试数据失效时间为当前时间+60分钟，不产生任务
        //长期医嘱/临时医嘱，失效时间小于当前时间,这里的测试数据失效时间为当前时间-60分钟，不产生任务
        sendData.sendXml(filename);
        JSONObject response = ipt.selNotAuditIptList(sendData.changeData.get("{{zyhzh}}"));
        JSONArray array = JSONObject.parseObject(response.getString("data")).getJSONArray("engineInfos");
        Assert.assertNull(array);
        //        String engineid = ipt.getEngineid(sendData.changeData.get("{{zyhzh}}"), 1);
//        ipt.iptAudit(sendData.changeData.get("{{gp}}"), String.valueOf(engineid), 0);
    }

}
