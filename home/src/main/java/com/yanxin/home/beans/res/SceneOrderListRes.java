package com.yanxin.home.beans.res;

import com.car.baselib.bean.BaseBean;

import java.math.BigDecimal;

/**
 * @author zhouz
 * @date 2021/3/7
 */
public class SceneOrderListRes extends BaseBean {


    /**
     brandName (string, optional): 品牌名称 ,
     carModelName (string, optional): 车型名称 ,
     faultDesc (string, optional): 故障描述 ,
     grabbingOrdersSts (integer, optional): 抢单状态 0未抢，1已抢 ,
     isOneself (boolean, optional): 是否是本人发布，true：是， false：否 ,
     issuerUuid (string, optional): 发布者uuid ,
     orderSts (integer, optional): 订单状态 0 待支付 1 已支付 2: 已取消 3:退款中 4:退款成功 5:退款失败 6：已完成 ,
     payType (integer, optional): 支付方式 0 微信支付 1 支付宝支付 ,
     totalAmount (number, optional): 总支付费用 ,
     uuid (string, optional): uuid
     distance (number, optional): 距离
     */

    private String brandName;
    private String carModelName;
    private String faultDesc;
    private int grabbingOrdersSts;
    private boolean isOneself;
    private String issuerUuid;
    private int orderSts;
    private int payType;
    private BigDecimal totalAmount;
    private String uuid;
    private double distance;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCarModelName() {
        return carModelName;
    }

    public void setCarModelName(String carModelName) {
        this.carModelName = carModelName;
    }

    public String getFaultDesc() {
        return faultDesc;
    }

    public void setFaultDesc(String faultDesc) {
        this.faultDesc = faultDesc;
    }

    public int getGrabbingOrdersSts() {
        return grabbingOrdersSts;
    }

    public void setGrabbingOrdersSts(int grabbingOrdersSts) {
        this.grabbingOrdersSts = grabbingOrdersSts;
    }

    public boolean isIsOneself() {
        return isOneself;
    }

    public void setIsOneself(boolean isOneself) {
        this.isOneself = isOneself;
    }

    public String getIssuerUuid() {
        return issuerUuid;
    }

    public void setIssuerUuid(String issuerUuid) {
        this.issuerUuid = issuerUuid;
    }

    public int getOrderSts() {
        return orderSts;
    }

    public void setOrderSts(int orderSts) {
        this.orderSts = orderSts;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
