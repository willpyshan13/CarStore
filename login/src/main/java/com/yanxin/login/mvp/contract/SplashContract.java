package com.yanxin.login.mvp.contract;

import com.car.baselib.mvp.BaseModel;
import com.car.baselib.mvp.BasePresenter;
import com.car.baselib.mvp.IView;

/**
 * @author zhouz
 * @date 2021/1/10
 */
public interface SplashContract {

    interface View extends IView {
        void onSuccess(Object content);
        void onFailed(int code, String message);
    }

    abstract class Model extends BaseModel {

    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void splashConfig(String code);
    }
}
