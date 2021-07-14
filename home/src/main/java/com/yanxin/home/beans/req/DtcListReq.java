package com.yanxin.home.beans.req;

/**
 * @author zhouz
 * @date 2021/2/19
 */
public class DtcListReq extends PageReq {

    /**
     * dtcCode dtc故障代码 ,
     */
    private String dtcCode;
    private int dtcCheckSts = 1;

    public int getDtcCheckSts() {
        return dtcCheckSts;
    }

    public void setDtcCheckSts(int dtcCheckSts) {
        this.dtcCheckSts = dtcCheckSts;
    }

    public String getDtcCode() {
        return dtcCode;
    }

    public void setDtcCode(String dtcCode) {
        this.dtcCode = dtcCode;
    }
}
