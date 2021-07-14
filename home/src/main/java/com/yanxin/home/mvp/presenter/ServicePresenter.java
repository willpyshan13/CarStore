package com.yanxin.home.mvp.presenter;

import com.car.baselib.net.http.HttpParams;
import com.car.baselib.net.http.RemoteDataService;
import com.car.baselib.net.http.ResponseObserver;
import com.car.baselib.utils.RxSchedulersUtil;
import com.yanxin.home.api.NetService;
import com.yanxin.home.beans.req.SceneOrderReq;
import com.yanxin.home.beans.res.SceneOrderListRes;
import com.yanxin.home.mvp.contract.ServiceContract;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/3/7
 */
public class ServicePresenter extends ServiceContract.Presenter {


    public ServicePresenter(ServiceContract.View view) {
        super(view);
    }

    @Override
    public void querySceneOrderList(SceneOrderReq sceneOrderReq) {
        RemoteDataService.getInstance().getApiService(NetService.class)
                .querySceneOrderList(HttpParams.getToken(),sceneOrderReq)
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new ResponseObserver<List<SceneOrderListRes>>(compositeDisposable) {
                    @Override
                    public void onSuccess(List<SceneOrderListRes> sceneOrderListRes) {
                        if (mView != null) {
                            mView.onSuccessSceneOrderList(sceneOrderListRes);
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
    public void grabbingOrders(String sceneOrderUuid) {
        RemoteDataService.getInstance().getApiService(NetService.class)
                .grabbingOrders(HttpParams.getToken(),sceneOrderUuid)
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new ResponseObserver<String>(compositeDisposable) {
                    @Override
                    public void onSuccess(String s) {
                        if (mView != null) {
                            mView.onSuccessGrabbingOrders(s);
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
    protected ServiceContract.Model getModel() {
        return null;
    }
}
