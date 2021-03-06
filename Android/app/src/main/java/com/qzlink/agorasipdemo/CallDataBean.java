package com.qzlink.agorasipdemo;

import java.io.Serializable;

public class CallDataBean implements Serializable {

    private String roomId;
    private String caller;
    private String phone;
    private String gw;
    private String token;
    private String uid;
    private boolean video;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGw() {
        return gw;
    }

    public void setGw(String gw) {
        this.gw = gw;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }
}
