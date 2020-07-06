package com.sf.autotest.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class HttpRequest {
    public CloseableHttpClient client;
    private final Logger log = LoggerFactory.getLogger(HttpRequest.class);


    public HttpRequest() {
        Login login = new Login();
        this.client = login.login();
    }

    //入参为xml格式的post请求
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
     * @param jsonObject 入参为json格式的post请求
     * @return 返回结果为json对象
     */
    public JSONObject doPostJson(String url, JSONObject jsonObject) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Accept", "application/json");
        String jsonString = jsonObject.toString();
        try {
            StringEntity entity = new StringEntity(jsonString, "UTF-8");
            httpPost.setEntity(entity);
            CloseableHttpResponse respose = client.execute(httpPost);
            HttpEntity httpEntity = respose.getEntity();
            String str = EntityUtils.toString(httpEntity);
            log.info("接口{}的响应结果：{}", url, str);
            return JSONObject.parseObject(str);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //put请求
    public JSONObject doPut(String url, String data) {
        HttpPut httpPut = new HttpPut(url);
        StringEntity stringEntity = new StringEntity(data, "UTF-8");
        httpPut.setEntity(stringEntity);
        try {
            CloseableHttpResponse response = client.execute(httpPut);
            HttpEntity responseEntity = response.getEntity();
            return JSONObject.parseObject(EntityUtils.toString(responseEntity));

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }


    public JSONObject doGet(String api) {
        HttpGet httpGet = new HttpGet(sfContant.auditcenter_url + api);
        CloseableHttpResponse respose = null;
        try {
            respose = client.execute(httpGet);
            HttpEntity httpEntity = respose.getEntity();
            String str = EntityUtils.toString(httpEntity);
            log.info("接口{}的响应结果：{}", sfContant.auditcenter_url + api, str);
            return JSONObject.parseObject(str);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public JSONObject doGetwithParam(String url, List<BasicNameValuePair> list) {
//        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
//        list.add(new BasicNameValuePair("username", "cgx"));
//        list.add(new BasicNameValuePair("password", "123456"));
        CloseableHttpResponse respose = null;
        String str = null;
        try {
            String params = EntityUtils.toString(new UrlEncodedFormEntity(list, Consts.UTF_8));
            HttpGet httpGet = new HttpGet(url + "?" + params);
            respose = client.execute(httpGet);
            if (respose.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = respose.getEntity();
                if (httpEntity != null) {
                    str = EntityUtils.toString(httpEntity);
                    log.info("接口{}的响应结果：{}", url, str);
                }
            }
            return JSONObject.parseObject(str);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }


    public static void main(String[] args) {
        HttpRequest request = new HttpRequest();
        request.doGet("/api/v1/collect/getTagList");//   /api/v1/newTaskHint
        request.doGet("/api/v1/newTaskHint");


    }

}
