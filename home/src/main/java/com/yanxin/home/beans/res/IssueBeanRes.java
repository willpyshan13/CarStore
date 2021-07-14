package com.yanxin.home.beans.res;

import com.car.baselib.bean.BaseBean;

/**
 * @author zhouz
 * @date 2021/1/17
 */
public class IssueBeanRes extends BaseBean {


    /**
     * uuid : 28bbff33193e4df289397b8ce228f054
     * title : X5行车记录仪空白
     * technicianName : 黄辉
     * technicianImgUrl : null
     * carOwnerName : null
     * consultDesc : 行车记录仪里的芯片卡取出来在电脑上看，里面一片空白。
     * answerDesc : ghhreweewh
     * createdTime : 2021-02-17
     * consultAmt : 1
     * orderUuid : 37b55704af2a4f39b3736045c23b7681
     */

    private String uuid;
    private String title;
    private String technicianName;
    private String technicianImgUrl;
    private String carOwnerName;
    private String consultDesc;
    private String answerDesc;
    private String createdTime;
    private int consultAmt;
    private String orderUuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }

    public String getTechnicianImgUrl() {
        return technicianImgUrl;
    }

    public void setTechnicianImgUrl(String technicianImgUrl) {
        this.technicianImgUrl = technicianImgUrl;
    }

    public String getCarOwnerName() {
        return carOwnerName;
    }

    public void setCarOwnerName(String carOwnerName) {
        this.carOwnerName = carOwnerName;
    }

    public String getConsultDesc() {
        return consultDesc;
    }

    public void setConsultDesc(String consultDesc) {
        this.consultDesc = consultDesc;
    }

    public String getAnswerDesc() {
        return answerDesc;
    }

    public void setAnswerDesc(String answerDesc) {
        this.answerDesc = answerDesc;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getConsultAmt() {
        return consultAmt;
    }

    public void setConsultAmt(int consultAmt) {
        this.consultAmt = consultAmt;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }
}
