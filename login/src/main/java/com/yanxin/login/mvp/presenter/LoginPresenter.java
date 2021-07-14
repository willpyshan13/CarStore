package com.yanxin.login.mvp.presenter;

import com.car.baselib.cache.SpCache;
import com.car.baselib.net.http.HttpParams;
import com.car.baselib.net.http.RemoteDataService;
import com.car.baselib.net.http.ResponseObserver;
import com.car.baselib.utils.LogUtils;
import com.car.baselib.utils.RxSchedulersUtil;
import com.yanxin.login.api.LoginNetService;
import com.yanxin.login.beans.req.UserLoginReq;
import com.yanxin.login.mvp.contract.LoginContract;
import com.yanxin.common.beans.res.UserLoginRes;
import com.yanxin.common.constants.Config;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * @author zhouz
 * @date 2021/1/10
 */
public class LoginPresenter extends LoginContract.Presenter {

    private int DOWN_TIME_COUNT = 60;
    public LoginPresenter(LoginContract.View view) {
        super(view);
    }

    @Override
    public void getVerificationCode(String mobile) {
        RemoteDataService.getInstance().getApiService(LoginNetService.class)
                .getVerificationCode(mobile,Config.USER_MERCHANT_TERMINAL)
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new ResponseObserver<String>(compositeDisposable) {
                    @Override
                    public void onSuccess(String s) {
                        if (mView != null) {
                            downTime(DOWN_TIME_COUNT);
                            mView.onCodeSuccess(s);
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
    public void login(String mobile, String verificationCode) {
        UserLoginReq userLoginReq = new UserLoginReq();
        userLoginReq.setAccountName(mobile);
        userLoginReq.setCode(verificationCode);
        userLoginReq.setTerminal(Config.USER_MERCHANT_TERMINAL);
        RemoteDataService.getInstance().getApiService(LoginNetService.class)
                .userLogin(userLoginReq)
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new ResponseObserver<UserLoginRes>(compositeDisposable) {
                    @Override
                    public void onSuccess(UserLoginRes userLoginRes) {
                        if (mView != null) {
                            HttpParams.setToken(userLoginRes.getToken());
                            SpCache.get().putString(Config.TOKEN_SP_KEY,userLoginRes.getToken());
                            SpCache.get().putString(Config.USER_TYPE_SP_KEY,userLoginRes.getUserType());
                            SpCache.get().putInt(Config.USER_CHECK_STS_SP_KEY,userLoginRes.getCheckSts());
                            SpCache.get().putString(Config.MOBILE_SP_KEY,mobile);
                            mView.onLoginSuccess(userLoginRes);
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
    protected LoginContract.Model getModel() {
        return null;
    }

    private void downTime(int time){
        Observable.interval(0,1, TimeUnit.SECONDS)
                .take(time + 1)
                .flatMap(new Function<Long, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Long aLong) throws Exception {
                        return observer -> {
                            String str;
                            long residueTime = time - aLong;
                            if (residueTime < 10) {
                                str = "0"+residueTime;
                            } else {
                                str = String.valueOf(residueTime);
                            }
                            observer.onNext(str+"s");
                            observer.onComplete();
                        };
                    }
                })
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addSubscribe(d);
                    }

                    @Override
                    public void onNext(String aLong) {
                        if (mView != null) {
                            mView.onCodeTvStr(aLong);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("onError: "+e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        if (mView != null) {
                            mView.onComplete();
                        }
                    }
                });
    }
}
