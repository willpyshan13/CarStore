package com.yanxin.home.beans.req;

import com.car.baselib.bean.BaseBean;

/**
 * @author zhouz
 * @date 2021/3/7
 */
public class PositionReq extends BaseBean {


    /**
     latitude (number, optional): 纬度 ,
     longitude (number, optional): 经度
     */

    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
