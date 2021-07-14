package com.yanxin.home.beans.res;

import com.car.baselib.bean.BaseBean;

import java.math.BigDecimal;

/**
 * @author zhouz
 * @date 2021/1/20
 */
public class DrivingBean extends BaseBean {


    private BigDecimal price;
    private String startTime;
    private String startPoint;
    private String endPoint;
    private double distance;
    private double journey;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getJourney() {
        return journey;
    }

    public void setJourney(double journey) {
        this.journey = journey;
    }
}
