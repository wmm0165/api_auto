package com.sf.autotest.common;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Set;


public class SendData {
    private static Logger log = LoggerFactory.getLogger(SendData.class);
    public HashMap<String, String> changeData = new HashMap<>();

    public SendData() {
        changeData.put("{{zyhzh}}", Util.getTimestamp(0, 0, 0));
        changeData.put("{{mzhzh}}", Util.getTimestamp(0, 0, 0));
        changeData.put("{{ts}}", Util.getTimestamp(0, 0, 0));
        changeData.put("{{tf1}}", Util.getTimestamp(-1, -1, 0));
        changeData.put("{{f5}}", Util.getDate(-5, 0, 0)); //5天前
        changeData.put("{{db180}}", Util.getDate(0, 3, 0)); //今天当前时间+180分钟
        changeData.put("{{tb180}}", Util.getTimestamp(0, 3, 0)); //今天当前时间戳+180分钟
        changeData.put("{{db60}}", Util.getDate(0, 1, 0)); //今天当前时间+60分钟
        changeData.put("{{tb60}}", Util.getTimestamp(0, 1, 0)); //今天当前时间戳+60分钟
        changeData.put("{{df60}}", Util.getDate(0, -1, 0)); //今天当前时间-60分钟
        changeData.put("{{tf60}}", Util.getTimestamp(0, -1, 0)); //今天当前时间戳-60分钟
        changeData.put("{{name}}", Util.getName());
        changeData.put("{{gp}}", Util.getRandom(1000000000));
        changeData.put("{{cgp}}", Util.getRandom(2000000000));
        changeData.put("{{bno}}", Util.getRandom(10000));
        changeData.put("{{d2}}", Util.getDate(0, 0, 0));
        changeData.put("{{docId}}", "wangdoc");
        changeData.put("{{docName}}", "王医生");
    }

    public String sendXml(String resourcePath) {
        //不传url，则默认对内接口
        String xml = "";

        try {
            InputStream input = new ClassPathResource(resourcePath).getInputStream();
            xml = IOUtils.toString(input, StandardCharsets.UTF_8);
            Set<String> keySet = changeData.keySet();
            for (String key : keySet) {
                xml = xml.replace(key, changeData.get(key));
            }
            log.info("请求{}的入参为:\n{}", sfContant.data_url, xml);
            return HttpRequest.doPostXml(sfContant.data_url, xml);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        SendData sendData = new SendData();
        String s = sendData.sendXml("ipt.xml");
        System.out.println(s);
    }
}
