package com.yanxin.home.mvp.contract;

import com.car.baselib.mvp.BaseModel;
import com.car.baselib.mvp.BasePresenter;
import com.car.baselib.mvp.IView;
import com.yanxin.home.beans.req.GoodsReq;
import com.yanxin.home.beans.res.GoodsBean;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/16
 */
public interface GoodsContract {

    interface View extends IView {
        void onSuccess(List<GoodsBean> goodsBeans);
        void onFailed(int code, String message);
    }

    abstract class Model extends BaseModel {

    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void queryGoodsList(GoodsReq goodsReq);

    }
}
