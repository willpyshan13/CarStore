package com.yanxin.home.mvp.presenter;

import com.car.baselib.net.http.HttpParams;
import com.car.baselib.net.http.RemoteDataService;
import com.car.baselib.net.http.ResponseObserver;
import com.car.baselib.utils.RxSchedulersUtil;
import com.yanxin.home.api.NetService;
import com.yanxin.home.beans.req.CaseListReq;
import com.yanxin.home.beans.res.CaseListItemRes;
import com.yanxin.home.mvp.contract.OnlineContract;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/23
 */
public class OnlinePresenter extends OnlineContract.Presenter {


    public OnlinePresenter(OnlineContract.View view) {
        super(view);
    }

    @Override
    public void queryCaseList(CaseListReq caseListReq) {
        RemoteDataService.getInstance().getApiService(NetService.class)
                .queryCaseList(HttpParams.getToken(),caseListReq)
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new ResponseObserver<List<CaseListItemRes>>(compositeDisposable) {
                    @Override
                    public void onSuccess(List<CaseListItemRes> caseListItemRes) {
                        if (mView != null) {
                            mView.onSuccess(caseListItemRes);
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
    protected OnlineContract.Model getModel() {
        return null;
    }
}
