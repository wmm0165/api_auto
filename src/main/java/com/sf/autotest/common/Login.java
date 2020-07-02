package com.sf.autotest.common;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jayway.jsonpath.JsonPath;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.applet.Main;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;

public class Login {


    public CloseableHttpClient login() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            String url2 = "http://10.1.1.120:9999/auditcenter/api/v1/startAuditWork";
            HashMap<Object, Object> paramsMap = new HashMap<>();
            paramsMap.put("name", sfContant.username);
            paramsMap.put("password", EncoderByMd5(sfContant.password));
            System.out.println(paramsMap);
            String jsonString = JSONObject.toJSONString(paramsMap);
//            System.out.println(jsonString);
//            String aa ="{\"password\":\"e10adc3949ba59abbe56e057f20f883e\",\"name\":\"wangmm\"}";
            HttpPost httpPost = new HttpPost(sfContant.syscenter_url + "/api/v1/currentUser");
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Accept", "application/json");
            StringEntity entity = new StringEntity(jsonString);
            httpPost.setEntity(entity);
            CloseableHttpResponse httpResponse = null;
            httpResponse = httpClient.execute(httpPost);
            HttpEntity entity1 = httpResponse.getEntity();
            String res = EntityUtils.toString(entity1);
            System.out.println(res);
            HttpGet httpGet = new HttpGet(url2);
            httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpClient;
    }

    public static String EncoderByMd5(String buf) {
        try {
            MessageDigest digist = MessageDigest.getInstance("MD5");
            byte[] rs = digist.digest(buf.getBytes("UTF-8"));
            StringBuffer digestHexStr = new StringBuffer();
            for (int i = 0; i < 16; i++) {
                digestHexStr.append(byteHEX(rs[i]));
            }
            return digestHexStr.toString();
        } catch (Exception e) {
            //logger.error(e.getMessage(), e);
        }
        return null;

    }

    public static String byteHEX(byte ib) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url2 = "http://10.1.1.120:9999/auditcenter/api/v1/startAuditWork";
        HashMap<Object, Object> paramsMap = new HashMap<>();
        paramsMap.put("name", sfContant.username);
        paramsMap.put("password", EncoderByMd5(sfContant.password));
//        System.out.println(paramsMap);
        String jsonString = JSONObject.toJSONString(paramsMap);
//        System.out.println(jsonString);
//            String aa ="{\"password\":\"e10adc3949ba59abbe56e057f20f883e\",\"name\":\"wangmm\"}";
        HttpPost httpPost = new HttpPost(sfContant.syscenter_url + "/api/v1/currentUser");
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Accept", "application/json");
        StringEntity entity = new StringEntity(jsonString);
        httpPost.setEntity(entity);
        CloseableHttpResponse httpResponse = null;
        httpResponse = httpClient.execute(httpPost);
        HttpEntity entity1 = httpResponse.getEntity();
        //(st, SerializerFeature.WriteMapNullValue) EntityUtils.toString(entity1)
//        JSONObject object = new JSONObject(EntityUtils.toString(entity1));

        JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(entity1).replaceAll("null", "\"\""));
//        String s = JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
//        String str = JsonPath.read(jsonObject,
//                "@.data");
//        System.out.println(str);
        String data = jsonObject.getString("data");
        System.out.println(data);
        JSONObject jsondata = JSONObject.parseObject(jsonObject.getString("data"));
        System.out.println(jsondata.getString("realname"));
        System.out.println(jsondata.getString("createUserId")+"aa");

//        System.out.println(jsonObject);
    }


}