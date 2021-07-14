package com.yanxin.home.mvp.presenter;

import com.car.baselib.net.http.HttpParams;
import com.car.baselib.net.http.RemoteDataService;
import com.car.baselib.net.http.ResponseObserver;
import com.car.baselib.utils.RxSchedulersUtil;
import com.yanxin.home.api.NetService;
import com.yanxin.home.beans.req.PageReq;
import com.yanxin.home.beans.res.AnswererRes;
import com.yanxin.home.mvp.contract.AnswererContract;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/30
 */
public class AnswererPresenter extends AnswererContract.Presenter {


    public AnswererPresenter(AnswererContract.View view) {
        super(view);
    }

    @Override
    public void queryPreAnswerList(PageReq pageReq) {
        RemoteDataService.getInstance().getApiService(NetService.class)
                .queryPreAnswerList(HttpParams.getToken(),pageReq)
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new ResponseObserver<List<AnswererRes>>(compositeDisposable) {
                    @Override
                    public void onSuccess(List<AnswererRes> answererRes) {
                        if (mView != null) {
                            mView.onSuccessAnswererList(answererRes);
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
    public void consultOrderSnapUp(String uuid,String orderUuid) {
        RemoteDataService.getInstance().getApiService(NetService.class)
                .consultOrderSnapUp(HttpParams.getToken(),uuid)
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new ResponseObserver<String>(compositeDisposable) {
                    @Override
                    public void onSuccess(String uuid) {
                        if (mView != null) {
                            mView.onConsultOrderSuccess(orderUuid);
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
    protected AnswererContract.Model getModel() {
        return null;
    }
}
