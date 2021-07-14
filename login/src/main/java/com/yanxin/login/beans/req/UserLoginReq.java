package com.yanxin.login.beans.req;

import com.car.baselib.bean.BaseBean;

/**
 * @author zhouz
 * @date 2021/1/17
 */
public class UserLoginReq extends BaseBean {


    /**
     * accountName : 手机号码
     * code : 验证码
     * terminal  : 用户类型 1车主 2 技师 3 店铺
     */

    private String accountName;
    private String code;
    private String terminal ;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }
}
