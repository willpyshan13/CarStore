package com.yanxin.home.beans.req;

import com.car.baselib.bean.BaseBean;

/**
 * @author zhouz
 * @date 2021/2/11
 */
public class UpdateUserImgReq extends BaseBean {


    /**
     * userPhotoImg : string
     */

    private String userPhotoImg;

    public String getUserPhotoImg() {
        return userPhotoImg;
    }

    public void setUserPhotoImg(String userPhotoImg) {
        this.userPhotoImg = userPhotoImg;
    }
}
