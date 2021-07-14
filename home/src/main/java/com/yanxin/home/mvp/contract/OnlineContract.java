package com.yanxin.home.mvp.contract;

import com.car.baselib.mvp.BaseModel;
import com.car.baselib.mvp.BasePresenter;
import com.car.baselib.mvp.IView;
import com.yanxin.home.beans.req.CaseListReq;
import com.yanxin.home.beans.res.CaseListItemRes;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/23
 */
public interface OnlineContract {

    interface View extends IView {
        void onSuccess(List<CaseListItemRes> caseListItemRes);
        void onFailed(int code, String message);
    }

    abstract class Model extends BaseModel {

    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public Presenter(View view) {
            super(view);
        }

        /**
         * 查询案例列表
         */
        public abstract void queryCaseList(CaseListReq caseListReq);

    }
}
