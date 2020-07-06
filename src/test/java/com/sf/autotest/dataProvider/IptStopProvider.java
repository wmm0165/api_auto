package com.sf.autotest.dataProvider;

import org.testng.annotations.DataProvider;

/**
 * @Description
 * @Author wangmengmeng
 * @Date 2020-07-06 16:49
 */
public class IptStopProvider {
    @DataProvider(name = "stop_1")
    public static Object[][] createData1() {
        return new Object[][] {{"audit771_44"}, {"audit771_44_2"}
        };
    }

    @DataProvider(name = "stop_2")
    public static Object[][] createData2() {
        return new Object[][] {{"audit771_45"}, {"audit771_45_2"},{"audit771_46"}, {"audit771_46_2"}
        };
    }
}

