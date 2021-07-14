package com.yanxin.login.mvp.presenter;

import com.car.baselib.net.http.RemoteDataService;
import com.car.baselib.net.http.ResponseObserver;
import com.car.baselib.utils.RxSchedulersUtil;
import com.yanxin.login.api.LoginNetService;
import com.yanxin.login.mvp.contract.SplashContract;

/**
 * @author zhouz
 * @date 2021/2/16
 */
public class SplashPresenter extends SplashContract.Presenter {


    public SplashPresenter(SplashContract.View view) {
        super(view);
    }

    @Override
    public void splashConfig(String code) {
        RemoteDataService.getInstance().getApiService(LoginNetService.class)
                .splashConfig(code)
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new ResponseObserver<Object>(compositeDisposable) {
                    @Override
                    public void onSuccess(Object content) {
                        if (mView != null) {
                            mView.onSuccess(content);
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
    protected SplashContract.Model getModel() {
        return null;
    }
}
