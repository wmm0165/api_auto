package com.sf.autotest.common;

public class sfContant {

    public static final String ipAddress = "http://10.1.1.122:9999";
    public static final String syscenter_url = ipAddress + "/syscenter";
    public static final String auditcenter_url = ipAddress + "/auditcenter";
    public static final String data_url = ipAddress + "/auditcenter/api/v1/auditcenter";//如果要使用统一接口，将该地址改掉即可


    public static final String username = "auto";
    public static final String password = "123456";


    public static void main(String[] args) {
        System.out.println(sfContant.syscenter_url);
        System.out.println(sfContant.auditcenter_url);
        System.out.println(sfContant.username);
        System.out.println(sfContant.password);
    }

}
