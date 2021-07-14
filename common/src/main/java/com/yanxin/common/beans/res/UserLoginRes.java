package com.yanxin.common.beans.res;


import java.io.Serializable;

/**
 * @author zhouz
 * @date 2021/1/17
 */
public class UserLoginRes implements Serializable {


    /**
     expires_in (integer, optional): 有效期时间 ,
     token (string, optional): 授权token ,
     userType (string, optional): 用户类型 1:车主 2：技师 3：店铺 ,
     uuid (string, optional): 数据uuid
     checkSts (integer, optional): 审核状态(0:待审核 1:审核通过 2:审核驳回) ,
     */

    private int expires_in;
    private String token;
    private String userType;
    private String uuid;
    private int checkSts;

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getCheckSts() {
        return checkSts;
    }

    public void setCheckSts(int checkSts) {
        this.checkSts = checkSts;
    }
}
