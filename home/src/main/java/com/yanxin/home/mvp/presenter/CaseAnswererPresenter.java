package com.yanxin.home.mvp.presenter;

import com.car.baselib.net.http.HttpParams;
import com.car.baselib.net.http.RemoteDataService;
import com.car.baselib.net.http.ResponseObserver;
import com.car.baselib.utils.RxSchedulersUtil;
import com.yanxin.home.api.NetService;
import com.yanxin.home.beans.req.TechnicianListReq;
import com.yanxin.home.beans.res.TechnicianListRes;
import com.yanxin.home.mvp.contract.CaseAnswererContract;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/27
 */
public class CaseAnswererPresenter extends CaseAnswererContract.Presenter {


    public CaseAnswererPresenter(CaseAnswererContract.View view) {
        super(view);
    }

    @Override
    public void queryTechnicianList(TechnicianListReq technicianListReq) {
        RemoteDataService.getInstance().getApiService(NetService.class)
                .queryTechnicianList(HttpParams.getToken(),technicianListReq)
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new ResponseObserver<List<TechnicianListRes>>(compositeDisposable) {
                    @Override
                    public void onSuccess(List<TechnicianListRes> technicianListRes) {
                        if (mView != null) {
                            mView.onSuccessTechnicianList(technicianListRes);
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
    protected CaseAnswererContract.Model getModel() {
        return null;
    }
}
