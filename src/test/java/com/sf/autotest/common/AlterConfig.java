package com.sf.autotest.common;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlterConfig {
    private final Logger log = LoggerFactory.getLogger(AlterConfig.class);

    public static HttpRequest httpRequest = new HttpRequest();

    public static void alterConfig(String id, String value) {
//        ArrayList<BasicNameValuePair> list = new ArrayList<>();
//        list.add(new BasicNameValuePair("id", id));
//        list.add(new BasicNameValuePair("type", "input"));
//        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
        JSONObject jsonObject = httpRequest.doPut(sfContant.syscenter_url + "/api/v1/config/updateConfig?id=" + id + "&type=input", value);
//        System.out.println(jsonObject);


    }

    public static void main(String[] args) {
        AlterConfig.alterConfig("40009","2");
    }
}
