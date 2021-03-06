package com.sf.autotest.testcases;

import com.sf.autotest.common.AlterConfig;
import com.sf.autotest.common.Ipt;
import com.sf.autotest.common.SendData;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

/**
 * @Description
 * @Author wangmengmeng
 * @Date 2020-07-06 15:12
 */

public class TestMerge {
    private SendData sendData;
    private Ipt ipt;

    @BeforeClass
    public void beforeClass() {
        AlterConfig.alterConfig("40003","1");
        AlterConfig.alterConfig("40106","120");
    }

    @BeforeMethod
    public void beforeMethod(){
        sendData =  new SendData();
        ipt = new Ipt();
    }
}
