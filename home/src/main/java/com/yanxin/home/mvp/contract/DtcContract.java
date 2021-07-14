package com.yanxin.home.mvp.contract;

import com.car.baselib.mvp.BaseModel;
import com.car.baselib.mvp.BasePresenter;
import com.car.baselib.mvp.IView;
import com.yanxin.home.beans.req.DtcListReq;
import com.yanxin.home.beans.req.PageReq;
import com.yanxin.home.beans.res.AnswererRes;
import com.yanxin.home.beans.res.DtcBeanRes;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/30
 */
public interface DtcContract {

    interface View extends IView {
        void onSuccessDtcList(List<DtcBeanRes> dtcBeanResList);
        void onFailed(int code, String message);
    }

    abstract class Model extends BaseModel {

    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void queryDtcList(DtcListReq dtcListReq);

    }
}
