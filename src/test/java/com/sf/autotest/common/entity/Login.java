package com.sf.autotest.common.entity;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * @Description
 * @Author wangmengmeng
 * @Date 2020-07-14 11:06
 */
public class Login implements Serializable {
    private String name;
    private String password;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void main(String[] args) {
        Login login = new Login();
        login.setName("wangmm");
        login.setPassword("123456");
        System.out.println(login);
        System.out.println(JSONObject.toJSON(login).toString());
    }
}
