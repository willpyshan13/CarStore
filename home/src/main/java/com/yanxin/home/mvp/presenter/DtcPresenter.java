package com.yanxin.home.mvp.presenter;

import com.car.baselib.net.http.HttpParams;
import com.car.baselib.net.http.RemoteDataService;
import com.car.baselib.net.http.ResponseObserver;
import com.car.baselib.utils.RxSchedulersUtil;
import com.yanxin.home.api.NetService;
import com.yanxin.home.beans.req.DtcListReq;
import com.yanxin.home.beans.res.DtcBeanRes;
import com.yanxin.home.mvp.contract.DtcContract;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/2/19
 */
public class DtcPresenter extends DtcContract.Presenter {


    public DtcPresenter(DtcContract.View view) {
        super(view);
    }

    @Override
    public void queryDtcList(DtcListReq dtcListReq) {
        RemoteDataService.getInstance().getApiService(NetService.class)
                .queryDtcList(HttpParams.getToken(),dtcListReq)
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new ResponseObserver<List<DtcBeanRes>>(compositeDisposable) {
                    @Override
                    public void onSuccess(List<DtcBeanRes> dtcBeanResList) {
                        if (mView != null) {
                            mView.onSuccessDtcList(dtcBeanResList);
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
    protected DtcContract.Model getModel() {
        return null;
    }
}
