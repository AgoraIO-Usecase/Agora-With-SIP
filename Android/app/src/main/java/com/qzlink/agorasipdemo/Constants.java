package com.qzlink.agorasipdemo;

public class Constants {

    public static String requestCallInfoUrl = "http://" + getIp() + ":" + getPort() + "/api/app/getTokenForRoomid";
    public static String requestPhoneCallUrl = "http://" + getIp() + ":" + getPort() + "/api/meet/callAndJoin";
    public static String requestSipCallUrl = "http://" + getIp() + ":" + getPort() + "/api/meet/callSIP";
    public static String requestKillCallUrl = "http://" + getIp() + ":" + getPort() + "/api/meet/killCall";

    public static final String KEY_IP = "key_ip";
    public static final String KEY_PORT = "key_port";
    public static final String KEY_AGORA_APP_ID = "key_agora_app_id";


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

    public static String getAgoraAppid() {
        return SPUtils.getString(KEY_AGORA_APP_ID, "89a88081d17e4348900c6054595ef75a");
    }

    public static void setAgoraAppid(String appid) {
        SPUtils.putString(KEY_AGORA_APP_ID, appid);
    }

}
