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

    public  String sendXml(String resourcePath){
        //不传url，则默认对内接口
        changeData.put("{{zyhzh}}", Util.getTimestamp());
        changeData.put("{{name}}", Util.getName());
        changeData.put("{{gp}}", Util.getRandom(1000000000));
        changeData.put("{{cgp}}", Util.getRandom(2000000000));
        changeData.put("{{bno}}", Util.getRandom(10000));
        changeData.put("{{d2}}", Util.getDate(0, 0, 0));
        changeData.put("{{docId}}", "wangdoc");
        changeData.put("{{docName}}", "王医生");
        String xml = "";

        try {
            InputStream input = new ClassPathResource(resourcePath).getInputStream();
            xml = IOUtils.toString(input, StandardCharsets.UTF_8);
            Set<String> keySet = changeData.keySet();
            for (String key : keySet) {
                xml = xml.replace(key, changeData.get(key));
            }
            log.info("请求{}的入参为:\n{}", sfContant.data_url, xml);
            return HttpRequest.doPostXml(sfContant.data_url,xml);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static String sendXml(String url, String resourcePath) throws IOException {
//        changeData.put("{{zyhzh}}", Util.getTimestamp());
//        changeData.put("{{name}}", Util.getName());
//        changeData.put("{{gp}}", Util.getRandom(1000000000));
//        changeData.put("{{bno}}", Util.getRandom(10000));
//        changeData.put("{{d2}}", Util.getDate(0, 0, 0));
//        changeData.put("{{docId}}", "wangdoc");
//        changeData.put("{{docName}}", "王医生");
//        String xml = "";
//
//        try (InputStream input = new ClassPathResource(resourcePath).getInputStream()) {
//            xml = IOUtils.toString(input, StandardCharsets.UTF_8);
//        }
//        Set<String> keySet = changeData.keySet();
//        for (String key : keySet) {
//            xml = xml.replace(key, changeData.get(key));
//        }
//        log.info("请求{}的入参为:\n{}", url, xml);
//        return HttpRequest.doPostXml(url, xml);
//    }


    public static void main(String[] args) throws IOException {
        SendData sendData = new SendData();
        String s = sendData.sendXml("ipt.xml");
        System.out.println(s);


    }
}
