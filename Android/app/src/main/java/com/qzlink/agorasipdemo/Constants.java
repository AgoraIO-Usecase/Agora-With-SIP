package com.qzlink.agorasipdemo;

public class Constants {

    public static final String requestPhoneCallUrl = "http://" + getIp() + ":" + getPort() + "/api/meet/callAndJoin";
    public static final String requestSipCallUrl = "http://" + getIp() + ":" + getPort() + "/api/meet/callSIP";
    public static final String requestKillCallUrl = "http://" + getIp() + ":" + getPort() + "/api/meet/killCall";

    public static final String KEY_IP = "key_ip";
    public static final String KEY_PORT = "key_port";


    public static String getIp() {
        return SPUtils.getString(KEY_IP, "39.99.148.35");
    }

    public static void setIp(String ip) {
        SPUtils.putString(KEY_IP, ip);
    }

    public static String getPort() {
        return SPUtils.getString(KEY_PORT, "9898");
    }

    public static void setPort(String port) {
        SPUtils.putString(KEY_PORT, port);
    }

}
