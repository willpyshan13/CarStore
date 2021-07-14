package com.yanxin.home.mvp.contract;

import com.car.baselib.mvp.BaseModel;
import com.car.baselib.mvp.BasePresenter;
import com.car.baselib.mvp.IView;
import com.yanxin.home.beans.req.AuditListReq;
import com.yanxin.home.beans.res.IssueBeanRes;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/30
 */
public interface CaseIssueContract {

    interface View extends IView {
        void onSuccessIssueBeanList(List<IssueBeanRes> issueBeanResList);
        void onFailed(int code, String message);
    }

    abstract class Model extends BaseModel {

    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public Presenter(View view) {
            super(view);
        }

        /**
         * 查询可旁听列表
         */
        public abstract void queryAnswerList(AuditListReq auditListReq);
    }
}
