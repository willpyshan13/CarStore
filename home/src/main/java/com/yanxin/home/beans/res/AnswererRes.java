package com.yanxin.home.beans.res;

import com.car.baselib.bean.BaseBean;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/30
 */
public class AnswererRes extends BaseBean {


    /**
     answerCheckSts (integer, optional): 回答审核状态 0 待审核 1 审核通过 2 审核驳回 ,
     answerDesc (string, optional): 回答描述 ,
     answerImgList (Array[string], optional): 回答图片列表 ,
     answerSts (integer, optional): 答复状态 0 未答复 1 已答复 ,
     answerTime (string, optional): 答复时间 ,
     carOwnerImgUrl (string, optional): 车主头像 ,
     carOwnerMobile (string, optional): 车主手机号 ,
     carOwnerName (string, optional): 车主姓名 ,
     carOwnerUuid (string, optional): 车主uuid ,
     consultAmt (number, optional): 咨询金额 ,
     consultCheckSts (integer, optional): 支咨询审核状态 0 待审核 1 审核通过 2 审核驳回 ,
     consultDesc (string, optional): 咨询描述 ,
     consultImgList (Array[string], optional): 咨询图片列表 ,
     consultType (integer, optional): 咨询类型 1：技师提问 2：全国技师提问 ,
     createdTime (string, optional): 咨询时间 ,
     orderSts (integer, optional): 订单状态 0 待支付 1 已支付 2: 已取消 3:退款中 4:退款成功 5:退款失败 ,
     orderUuid (string, optional): 订单uuid ,
     technicianImgUrl (string, optional): 技师头像 ,
     technicianMobile (string, optional): 技师手机号 ,
     technicianName (string, optional): 技师姓名 ,
     technicianUuid (string, optional): 技师uuid ,
     title (string, optional): 咨询标题 ,
     uuid (string, optional): 咨询uuid
     */

    private int answerCheckSts;
    private String answerDesc;
    private int answerSts;
    private String answerTime;
    private String carOwnerImgUrl;
    private String carOwnerMobile;
    private String carOwnerName;
    private String carOwnerUuid;
    private double consultAmt;
    private int consultCheckSts;
    private String consultDesc;
    private String createdTime;
    private int orderSts;
    private String orderUuid;
    private String technicianImgUrl;
    private int consultType;
    private String technicianMobile;
    private String technicianName;
    private String technicianUuid;
    private String title;
    private String uuid;
    private List<String> answerImgList;
    private List<String> consultImgList;

    public int getAnswerCheckSts() {
        return answerCheckSts;
    }

    public void setAnswerCheckSts(int answerCheckSts) {
        this.answerCheckSts = answerCheckSts;
    }

    public String getAnswerDesc() {
        return answerDesc;
    }

    public void setAnswerDesc(String answerDesc) {
        this.answerDesc = answerDesc;
    }

    public int getAnswerSts() {
        return answerSts;
    }

    public void setAnswerSts(int answerSts) {
        this.answerSts = answerSts;
    }

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

    public String getCarOwnerImgUrl() {
        return carOwnerImgUrl;
    }

    public void setCarOwnerImgUrl(String carOwnerImgUrl) {
        this.carOwnerImgUrl = carOwnerImgUrl;
    }

    public String getCarOwnerMobile() {
        return carOwnerMobile;
    }

    public void setCarOwnerMobile(String carOwnerMobile) {
        this.carOwnerMobile = carOwnerMobile;
    }

    public String getCarOwnerName() {
        return carOwnerName;
    }

    public void setCarOwnerName(String carOwnerName) {
        this.carOwnerName = carOwnerName;
    }

    public String getCarOwnerUuid() {
        return carOwnerUuid;
    }

    public void setCarOwnerUuid(String carOwnerUuid) {
        this.carOwnerUuid = carOwnerUuid;
    }

    public double getConsultAmt() {
        return consultAmt;
    }

    public void setConsultAmt(double consultAmt) {
        this.consultAmt = consultAmt;
    }

    public int getConsultCheckSts() {
        return consultCheckSts;
    }

    public void setConsultCheckSts(int consultCheckSts) {
        this.consultCheckSts = consultCheckSts;
    }

    public String getConsultDesc() {
        return consultDesc;
    }

    public void setConsultDesc(String consultDesc) {
        this.consultDesc = consultDesc;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getOrderSts() {
        return orderSts;
    }

    public void setOrderSts(int orderSts) {
        this.orderSts = orderSts;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }

    public String getTechnicianImgUrl() {
        return technicianImgUrl;
    }

    public void setTechnicianImgUrl(String technicianImgUrl) {
        this.technicianImgUrl = technicianImgUrl;
    }

    public int getConsultType() {
        return consultType;
    }

    public void setConsultType(int consultType) {
        this.consultType = consultType;
    }

    public String getTechnicianMobile() {
        return technicianMobile;
    }

    public void setTechnicianMobile(String technicianMobile) {
        this.technicianMobile = technicianMobile;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }

    public String getTechnicianUuid() {
        return technicianUuid;
    }

    public void setTechnicianUuid(String technicianUuid) {
        this.technicianUuid = technicianUuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<String> getAnswerImgList() {
        return answerImgList;
    }

    public void setAnswerImgList(List<String> answerImgList) {
        this.answerImgList = answerImgList;
    }

    public List<String> getConsultImgList() {
        return consultImgList;
    }

    public void setConsultImgList(List<String> consultImgList) {
        this.consultImgList = consultImgList;
    }
}
