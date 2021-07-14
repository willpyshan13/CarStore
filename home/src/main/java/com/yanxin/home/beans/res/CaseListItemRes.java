package com.yanxin.home.beans.res;

import com.car.baselib.bean.BaseBean;

/**
 * @author zhouz
 * @date 2021/1/23
 */
public class CaseListItemRes extends BaseBean {


    /**
     amt (number, optional): 案例收益 ,
     caseUuid (string, optional): 案例唯一标识 ,
     num (integer, optional): 案例销量 ,
     profitType (integer, optional): 收益类型1维修2案例3问答 ,
     title (string, optional): 案例名称 ,
     uuid (string, optional): 案例收益唯一标识
     */

    private int amt;
    private String caseUuid;
    private int num;
    private int profitType;
    private String title;
    private String uuid;

    public int getAmt() {
        return amt;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }

    public String getCaseUuid() {
        return caseUuid;
    }

    public void setCaseUuid(String caseUuid) {
        this.caseUuid = caseUuid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getProfitType() {
        return profitType;
    }

    public void setProfitType(int profitType) {
        this.profitType = profitType;
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
}
