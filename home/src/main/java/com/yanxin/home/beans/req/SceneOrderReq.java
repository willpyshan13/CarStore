package com.yanxin.home.beans.req;

/**
 * @author zhouz
 * @date 2021/3/7
 */
public class SceneOrderReq extends PageReq {

    /**
     * 查询现场订单类型，0：未抢订单，1：已抢订单，2：发布订单
     * */
    private Integer queryType;

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }
}
