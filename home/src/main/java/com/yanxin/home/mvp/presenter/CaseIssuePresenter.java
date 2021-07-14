package com.yanxin.home.mvp.presenter;

import com.car.baselib.net.http.HttpParams;
import com.car.baselib.net.http.RemoteDataService;
import com.car.baselib.net.http.ResponseObserver;
import com.car.baselib.utils.RxSchedulersUtil;
import com.yanxin.home.api.NetService;
import com.yanxin.home.beans.req.AuditListReq;
import com.yanxin.home.beans.res.IssueBeanRes;
import com.yanxin.home.mvp.contract.CaseIssueContract;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/30
 */
public class CaseIssuePresenter extends CaseIssueContract.Presenter {


    public CaseIssuePresenter(CaseIssueContract.View view) {
        super(view);
    }

    @Override
    public void queryAnswerList(AuditListReq auditListReq) {
        RemoteDataService.getInstance().getApiService(NetService.class)
                .queryAnswerList(HttpParams.getToken(),auditListReq)
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new ResponseObserver<List<IssueBeanRes>>(compositeDisposable) {
                    @Override
                    public void onSuccess(List<IssueBeanRes> issueBeanResList) {
                        if (mView != null) {
                            mView.onSuccessIssueBeanList(issueBeanResList);
                        }
                    }

                    @Override
                    public void onFailed(int code, String message) {
                        if (mView != null) {
                            mView.onFailed(code,message);
                        }
                    }
                });
    }

    @Override
    protected CaseIssueContract.Model getModel() {
        return null;
    }
}
