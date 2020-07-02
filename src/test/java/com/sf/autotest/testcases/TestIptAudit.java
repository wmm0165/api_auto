package com.sf.autotest.testcases;

import com.sf.autotest.common.Ipt;
import com.sf.autotest.common.SendData;
import jdk.nashorn.internal.ir.ReturnNode;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestIptAudit {
    private SendData sendData;

    @BeforeMethod
    public void getSd(){
        sendData =  new SendData();
    }


    @Test
    public void test_audit_1() {
//        SendData sendData = new SendData();
        sendData.sendXml("ipt.xml");
        sendData.sendXml("ipt2.xml");
//        Ipt ipt = new Ipt();
//        Object engineid = ipt.getEngineid(sendData.changeData.get("{{zyhzh}}"), 1);
//        ipt.iptAudit(sendData.changeData.get("{{gp}}"), String.valueOf(engineid), 0);
    }
    @Test
    public void test_audit_2() {
        String s = sendData.sendXml("ipt.xml");
    }

}
