package com.yanxin.home.beans.res;

import com.car.baselib.bean.BaseBean;

/**
 * @author zhouz
 * @date 2021/1/23
 */
public class CaseForVehicleListRes extends BaseBean {


    /**
     amt (number, optional): 案例金额 ,
     imgUrl (string, optional): 案例图片 ,
     technicianName (string, optional): 技师名称 ,
     title (string, optional): 案例唯名称 ,
     uuid (string, optional): 案例唯一标识 ,
     workingYear (integer, optional): 工龄

     */

    private double amt;
    private String imgUrl;
    private String technicianName;
    private String title;
    private String uuid;
    private Integer workingYear;

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
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

    public Integer getWorkingYear() {
        return workingYear;
    }

    public void setWorkingYear(Integer workingYear) {
        this.workingYear = workingYear;
    }
}
