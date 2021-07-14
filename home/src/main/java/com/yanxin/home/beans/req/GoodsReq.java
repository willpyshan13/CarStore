package com.yanxin.home.beans.req;

import com.car.baselib.bean.BaseBean;

/**
 * @author zhouz
 * @date 2021/1/16
 */
public class GoodsReq extends BaseBean {


    private int pageNum;
    private int pageSize;
    private int sellSts;


    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSellSts() {
        return sellSts;
    }

    public void setSellSts(int sellSts) {
        this.sellSts = sellSts;
    }


}
