package com.yanxin.home.beans.res;

import com.car.baselib.bean.BaseBean;

import java.math.BigDecimal;

/**
 * @author zhouz
 * @date 2021/2/10
 */
public class DtcBeanRes extends BaseBean {


    /**
     configName (string, optional): 品牌名称 ,
     createdBy (string, optional): 创建人 ,
     createdTime (string, optional): 创建时间 ,
     dtcAmount (number, optional): dtc购买金额 ,
     dtcBrandUuid (string, optional): dtc发布关联品牌(对应车辆品牌uuid) ,
     dtcCode (string, optional): dtc故障代码 ,
     dtcDefinition (string, optional): dtc标题 ,
     dtcIssuerUuid (string, optional): 发布人uuid ,
     uuid (string, optional): uuid
     */

    private String uuid;
    private String dtcCode;
    private String dtcDefinition;
    private String dtcBrandUuid;
    private BigDecimal dtcAmount;
    private String createdTime;
    private String createdBy;
    private String configName;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDtcCode() {
        return dtcCode;
    }

    public void setDtcCode(String dtcCode) {
        this.dtcCode = dtcCode;
    }

    public String getDtcDefinition() {
        return dtcDefinition;
    }

    public void setDtcDefinition(String dtcDefinition) {
        this.dtcDefinition = dtcDefinition;
    }

    public String getDtcBrandUuid() {
        return dtcBrandUuid;
    }

    public void setDtcBrandUuid(String dtcBrandUuid) {
        this.dtcBrandUuid = dtcBrandUuid;
    }

    public BigDecimal getDtcAmount() {
        return dtcAmount;
    }

    public void setDtcAmount(BigDecimal dtcAmount) {
        this.dtcAmount = dtcAmount;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }
}
