package com.yanxin.home.mvp.contract;

import com.car.baselib.mvp.BaseModel;
import com.car.baselib.mvp.BasePresenter;
import com.car.baselib.mvp.IView;
import com.yanxin.home.beans.req.SceneOrderReq;
import com.yanxin.home.beans.res.SceneOrderListRes;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/30
 */
public interface ServiceContract {

    interface View extends IView {
        void onSuccessSceneOrderList(List<SceneOrderListRes> sceneOrderListRes);
        void onSuccessGrabbingOrders(String message);
        void onFailed(int code, String message);
    }

    abstract class Model extends BaseModel {

    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void querySceneOrderList(SceneOrderReq sceneOrderReq);

        public abstract void grabbingOrders(String sceneOrderUuid);
    }
}
