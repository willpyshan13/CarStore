package com.yanxin.home.beans.res;

import com.car.baselib.bean.BaseBean;

/**
 * @author zhouz
 * @date 2021/2/2
 */
public class AccountInfoRes extends BaseBean {


    /**
     accountAmt (number, optional): 账户余额 ,
     aviWithdrawAmt (number, optional): 可提现金额 ,
     ingAmt (number, optional): 待入账金额 ,
     orderNum (integer, optional): 订单总数 ,
     withdrawAmt (number, optional): 已提现金额
     */

    private double accountAmt;
    private double aviWithdrawAmt;
    private double ingAmt;
    private int orderNum;
    private double withdrawAmt;

    public double getAccountAmt() {
        return accountAmt;
    }

    public void setAccountAmt(double accountAmt) {
        this.accountAmt = accountAmt;
    }

    public double getAviWithdrawAmt() {
        return aviWithdrawAmt;
    }

    public void setAviWithdrawAmt(double aviWithdrawAmt) {
        this.aviWithdrawAmt = aviWithdrawAmt;
    }

    public double getIngAmt() {
        return ingAmt;
    }

    public void setIngAmt(double ingAmt) {
        this.ingAmt = ingAmt;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public double getWithdrawAmt() {
        return withdrawAmt;
    }

    public void setWithdrawAmt(double withdrawAmt) {
        this.withdrawAmt = withdrawAmt;
    }
}
