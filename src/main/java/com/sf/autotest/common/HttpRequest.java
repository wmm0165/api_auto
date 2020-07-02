package com.sf.autotest.common;

import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.ir.ReturnNode;
import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.awt.image.Kernel;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class HttpRequest {
    public CloseableHttpClient client;
    private final Logger log = LoggerFactory.getLogger(HttpRequest.class);


    public HttpRequest() {
        Login login = new Login();
        this.client = login.login();
    }

    public static String doPostXml(String url, String xml) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        StringEntity requestEntity = new StringEntity(xml, "UTF-8");
        requestEntity.setContentType("text/plain");
        requestEntity.setContentEncoding("UTF-8");
        httpPost.setEntity(requestEntity);
        CloseableHttpResponse respose = httpClient.execute(httpPost);
        HttpEntity entity = respose.getEntity();
        return EntityUtils.toString(entity);
    }

    /**
     * @param jsonObject post请求的body入参
     * @return 返回结果为json对象
     */
    public JSONObject doPostJson(String url, JSONObject jsonObject) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Accept", "application/json");
        String jsonString = jsonObject.toString();
        try {
            StringEntity entity = new StringEntity(jsonString);
            httpPost.setEntity(entity);
            CloseableHttpResponse respose = client.execute(httpPost);
            HttpEntity httpEntity = respose.getEntity();
            return JSONObject.parseObject(EntityUtils.toString(httpEntity));

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void doGet(String api) {
        HttpGet httpGet = new HttpGet(sfContant.auditcenter_url + api);
        CloseableHttpResponse respose = null;
        try {
            respose = client.execute(httpGet);
            HttpEntity entity = respose.getEntity();
            String enertyString = EntityUtils.toString(entity);
            log.info("接口{}的响应结果：{}", api, enertyString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void doGetwithParam(String url, List<BasicNameValuePair> list) throws IOException {
//        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
//        list.add(new BasicNameValuePair("username", "cgx"));
//        list.add(new BasicNameValuePair("password", "123456"));
        String params = EntityUtils.toString(new UrlEncodedFormEntity(list, Consts.UTF_8));
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse respose = null;
        try {
            respose = client.execute(httpGet);
            HttpEntity entity = respose.getEntity();
            String enertyString = EntityUtils.toString(entity);
            log.info("接口{}的响应结果：{}", url, enertyString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        HttpRequest request = new HttpRequest();
        request.doGet("/api/v1/collect/getTagList");//   /api/v1/newTaskHint
        request.doGet("/api/v1/newTaskHint");


    }

}
