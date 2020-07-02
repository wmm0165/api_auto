package com.sf.testcases;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class TestHello {
    private Logger logger = LoggerFactory.getLogger(TestHello.class);

    @Test
    public void hello() throws Exception {
        int a = 1;
        assert a == 1;
        logger.trace("trace");
        logger.debug("debug");
        logger.info("{}", TestHello.class);
        logger.warn("warn");
        logger.error("error");


        //1.创建properties对象
        Properties pro = new Properties();
        //加载配置文件
        // 获取class目录下的配置文件
        ClassLoader classLoader = TestHello.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream("pro.properties");
        pro.load(is);
//        System.out.println(is);

        //获取配置文件中定义的数据
        String url = pro.getProperty("url");
        String name = pro.getProperty("name");
        System.out.println(url);
        System.out.println(name);


    }

}
