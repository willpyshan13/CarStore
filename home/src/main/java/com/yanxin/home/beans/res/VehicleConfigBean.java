package com.yanxin.home.beans.res;

import com.car.baselib.bean.BaseBean;

/**
 * @author zhouz
 * @date 2021/1/23
 */
public class VehicleConfigBean extends BaseBean {


    /**
     configName (string, optional): 配置名称 ,
     configType (string, optional): 配置类型 1：车辆类型 2：车辆品牌 3：车辆型号 ,
     parentCode (string, optional): 父类编码，根节点默认：-1 ,
     sortNum (integer, optional): 排序 ,
     uuid (string, optional): 主键ID
     */

    private String configName;
    private String configType;
    private String parentCode;
    private int sortNum;
    private String uuid;

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
