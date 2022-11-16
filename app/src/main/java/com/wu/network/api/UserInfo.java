package com.wu.network.api;

/**
 * 作者:吴奎庆
 * <p>
 * 时间:9/30/22
 * <p>
 * 用途:
 */


public class UserInfo {
    int id;
    int vip_number;
    int is_vip;
    int daysFreeNumber;
    String nickname;
    String headimgurl;
    String create_time;
    String vip_time;
    String vip_desc;
    String notFreeUseErrorMsg;
    boolean isFreeUse;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVip_number() {
        return vip_number;
    }

    public void setVip_number(int vip_number) {
        this.vip_number = vip_number;
    }

    public int getIs_vip() {
        return is_vip;
    }

    public void setIs_vip(int is_vip) {
        this.is_vip = is_vip;
    }

    public int getDaysFreeNumber() {
        return daysFreeNumber;
    }

    public void setDaysFreeNumber(int daysFreeNumber) {
        this.daysFreeNumber = daysFreeNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getVip_time() {
        return vip_time;
    }

    public void setVip_time(String vip_time) {
        this.vip_time = vip_time;
    }

    public String getVip_desc() {
        return vip_desc;
    }

    public void setVip_desc(String vip_desc) {
        this.vip_desc = vip_desc;
    }

    public String getNotFreeUseErrorMsg() {
        return notFreeUseErrorMsg;
    }

    public void setNotFreeUseErrorMsg(String notFreeUseErrorMsg) {
        this.notFreeUseErrorMsg = notFreeUseErrorMsg;
    }

    public boolean isFreeUse() {
        return isFreeUse;
    }

    public void setFreeUse(boolean freeUse) {
        isFreeUse = freeUse;
    }
}
