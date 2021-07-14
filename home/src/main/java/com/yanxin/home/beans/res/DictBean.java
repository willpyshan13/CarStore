package com.yanxin.home.beans.res;

import com.car.baselib.bean.BaseBean;

/**
 * @author zhouz
 * @date 2021/1/17
 */
public class DictBean extends BaseBean {


    /**
     * uuid : 1010
     * lableType : hot_city
     * lableTypeDesc : 热门城市
     * lableValue : BeiJing
     * lableDesc : 北京
     * lableDescEn : BeiJing
     * sortNum : 0
     */

    /**
     * uuid : 1
     * areaCode : 110000
     * areaName : 北京
     * areaNameEn : BeiJing
     * areaType : 1
     * cityCode : 010
     * parentCode : -1
     * parentUuid : -1
     */

    private String uuid;
    private String lableType;
    private String lableTypeDesc;
    private String lableCode;
    private String lableValue;
    private String lableDesc;
    private String lableDescEn;
    private int sortNum;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLableType() {
        return lableType;
    }

    public void setLableType(String lableType) {
        this.lableType = lableType;
    }

    public String getLableTypeDesc() {
        return lableTypeDesc;
    }

    public void setLableTypeDesc(String lableTypeDesc) {
        this.lableTypeDesc = lableTypeDesc;
    }

    public String getLableCode() {
        return lableCode;
    }

    public void setLableCode(String lableCode) {
        this.lableCode = lableCode;
    }

    public String getLableValue() {
        return lableValue;
    }

    public void setLableValue(String lableValue) {
        this.lableValue = lableValue;
    }

    public String getLableDesc() {
        return lableDesc;
    }

    public void setLableDesc(String lableDesc) {
        this.lableDesc = lableDesc;
    }

    public String getLableDescEn() {
        return lableDescEn;
    }

    public void setLableDescEn(String lableDescEn) {
        this.lableDescEn = lableDescEn;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }
}
