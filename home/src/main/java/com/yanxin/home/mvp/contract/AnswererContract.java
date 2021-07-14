package com.yanxin.home.mvp.contract;

import com.car.baselib.mvp.BaseModel;
import com.car.baselib.mvp.BasePresenter;
import com.car.baselib.mvp.IView;
import com.yanxin.home.beans.req.GoodsReq;
import com.yanxin.home.beans.req.PageReq;
import com.yanxin.home.beans.res.AnswererRes;
import com.yanxin.home.beans.res.GoodsBean;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/30
 */
public interface AnswererContract {

    interface View extends IView {
        void onSuccessAnswererList(List<AnswererRes> answererRes);
        void onConsultOrderSuccess(String uuid);
        void onFailed(int code, String message);
    }

    abstract class Model extends BaseModel {

    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void queryPreAnswerList(PageReq pageReq);

        public abstract void consultOrderSnapUp(String uuid,String orderUuid);

    }
}
