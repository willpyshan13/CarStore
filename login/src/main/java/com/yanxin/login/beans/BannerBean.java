package com.yanxin.login.beans;

import com.car.baselib.bean.BaseBean;

/**
 * @author zhouz
 * @date 2021/1/10
 */
public class BannerBean extends BaseBean {


    /**
     * imgUrl : http://tupian
     * title : dhfjkdhjf
     * jumpUrl : http://tiaozhuan
     * describe : 描述
     * sort : 2
     * imgRes
     */

    private String imgUrl;
    private String title;
    private String jumpUrl;
    private String describe;
    private int sort;
    private Integer resId;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }
}
