package com.yanxin.login.mvp.contract;

import com.car.baselib.mvp.BaseModel;
import com.car.baselib.mvp.BasePresenter;
import com.car.baselib.mvp.IView;
import com.yanxin.common.beans.res.UserLoginRes;

/**
 * @author zhouz
 * @date 2021/1/10
 */
public interface LoginContract {

    interface View extends IView {
        void onCodeSuccess(String code);
        void onLoginSuccess(UserLoginRes userLoginRes);
        void onCodeTvStr(String str);
        void onComplete();
        void onFailed(int code, String message);
    }

    abstract class Model extends BaseModel {

    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public Presenter(View view) {
            super(view);
        }

        /**
         * 获取验证码
         * @param mobile
         */
        public abstract void getVerificationCode(String mobile);

        /**
         * 登录
         * @param mobile
         * @param verificationCode
         */
        public abstract void login(String mobile,String verificationCode);
    }
}
