package com.yanxin.home.beans.res;

import com.car.baselib.bean.BaseBean;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/27
 */
public class TechnicianListRes extends BaseBean {


    /**
     * answerAmt : 0
     * brandResList : [{"brandName":"string","brandUuid":"string","technicianUuid":"string","uuid":"string"}]
     * name : string
     * photoImgUrl : string
     * qaCount : 0
     * score : 0
     * technologyType : string
     * uuid  : string
     */

    private double answerAmt;
    private String name;
    private String uuid;
    private String photoImgUrl;
    private int qaCount;
    private int score;
    private int cybAuth;
    private String technologyType;
    private List<BrandResListBean> brandResList;

    public int getCybAuth() {
        return cybAuth;
    }

    public void setCybAuth(int cybAuth) {
        this.cybAuth = cybAuth;
    }

    public double getAnswerAmt() {
        return answerAmt;
    }

    public void setAnswerAmt(double answerAmt) {
        this.answerAmt = answerAmt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPhotoImgUrl() {
        return photoImgUrl;
    }

    public void setPhotoImgUrl(String photoImgUrl) {
        this.photoImgUrl = photoImgUrl;
    }

    public int getQaCount() {
        return qaCount;
    }

    public void setQaCount(int qaCount) {
        this.qaCount = qaCount;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTechnologyType() {
        return technologyType;
    }

    public void setTechnologyType(String technologyType) {
        this.technologyType = technologyType;
    }

    public List<BrandResListBean> getBrandResList() {
        return brandResList;
    }

    public void setBrandResList(List<BrandResListBean> brandResList) {
        this.brandResList = brandResList;
    }

    public static class BrandResListBean extends BaseBean {
        /**
         * brandName : string
         * brandUuid : string
         * technicianUuid : string
         * uuid : string
         */

        private String brandName;
        private String brandUuid;
        private String technicianUuid;
        private String uuid;

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getBrandUuid() {
            return brandUuid;
        }

        public void setBrandUuid(String brandUuid) {
            this.brandUuid = brandUuid;
        }

        public String getTechnicianUuid() {
            return technicianUuid;
        }

        public void setTechnicianUuid(String technicianUuid) {
            this.technicianUuid = technicianUuid;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }
    }
}
