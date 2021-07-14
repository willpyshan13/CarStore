package com.yanxin.login.api;

/**
 * @author zhouz
 * @date 2021/1/16
 */
public interface UrlConstant {

    /**
     * 获取验证码
     */
    String GET_VERIFICATION_CODE = "/account/login/getLoginCode/{accountName}/{terminal}";
    /**
     * 用户登录
     */
    String USER_LOGIN = "/account/login/userLogin";

    String SPLASH_CONFIG = "/manager/setting/getByCode/{code}";
}
