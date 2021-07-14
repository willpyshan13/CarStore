package com.yanxin.home.mvp.contract;

import com.car.baselib.mvp.BaseModel;
import com.car.baselib.mvp.BasePresenter;
import com.car.baselib.mvp.IView;
import com.yanxin.home.beans.req.TechnicianListReq;
import com.yanxin.home.beans.res.TechnicianListRes;

import java.util.List;


/**
 * @author zhouz
 * @date 2021/1/27
 */
public interface CaseAnswererContract {

    interface View extends IView {
        void onSuccessTechnicianList(List<TechnicianListRes> technicianListRes);
        void onFailed(int code, String message);
    }

    abstract class Model extends BaseModel {

    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void queryTechnicianList(TechnicianListReq technicianListReq);
    }
}
