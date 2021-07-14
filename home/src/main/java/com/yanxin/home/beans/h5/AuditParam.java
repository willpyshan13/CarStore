package com.yanxin.home.beans.h5;

import com.car.baselib.bean.BaseBean;

/**
 * @author zhouz
 * @date 2021/1/31
 */
public class AuditParam extends BaseBean {

    private String uuid;
    private String orderUuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }
}
