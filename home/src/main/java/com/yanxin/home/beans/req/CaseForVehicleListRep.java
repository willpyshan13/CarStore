package com.yanxin.home.beans.req;

import com.car.baselib.bean.BaseBean;

/**
 * @author zhouz
 * @date 2021/1/23
 */
public class CaseForVehicleListRep extends BaseBean {

    /**
     * attachSys : string
     * brandUuid : string
     * model : string
     * pageNum : 0
     * pageSize : 0
     */

    private String attachSys;
    private String brandUuid;
    private String model;
    private int pageNum;
    private int pageSize;

    public String getAttachSys() {
        return attachSys;
    }

    public void setAttachSys(String attachSys) {
        this.attachSys = attachSys;
    }

    public String getBrandUuid() {
        return brandUuid;
    }

    public void setBrandUuid(String brandUuid) {
        this.brandUuid = brandUuid;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
