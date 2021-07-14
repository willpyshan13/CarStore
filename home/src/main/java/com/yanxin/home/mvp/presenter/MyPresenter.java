package com.yanxin.home.mvp.presenter;

import com.car.baselib.bean.CommonBean;
import com.car.baselib.cache.SpCache;
import com.car.baselib.net.http.HttpParams;
import com.car.baselib.net.http.RemoteDataService;
import com.car.baselib.net.http.ResponseObserver;
import com.car.baselib.net.http.ResponseThrowable;
import com.car.baselib.utils.RxSchedulersUtil;
import com.yanxin.common.beans.res.UserLoginRes;
import com.yanxin.common.constants.Config;
import com.yanxin.home.beans.req.UpdateUserImgReq;
import com.yanxin.home.beans.res.DictBean;
import com.yanxin.home.constants.Constants;
import com.yanxin.home.api.NetService;
import com.yanxin.home.beans.res.AccountInfoRes;
import com.yanxin.home.mvp.contract.MyContract;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author zhouz
 * @date 2021/1/31
 */
public class MyPresenter extends MyContract.Presenter {

    private String userPhotoImgUrl;

    public MyPresenter(MyContract.View view) {
        super(view);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        queryUserPhotoImgUrl();
    }

    @Override
    public void accountSwitchRole() {
        RemoteDataService.getInstance().getApiService(NetService.class)
                .accountSwitchRole(HttpParams.getToken())
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new ResponseObserver<UserLoginRes>(compositeDisposable) {
                    @Override
                    public void onSuccess(UserLoginRes userLoginRes) {
                        HttpParams.setToken(userLoginRes.getToken());
                        SpCache.get().putString(Config.TOKEN_SP_KEY,userLoginRes.getToken());
                        SpCache.get().putInt(Config.USER_CHECK_STS_SP_KEY,userLoginRes.getCheckSts());
                        Constants.setUserType(userLoginRes.getUserType());
                        if (mView != null) {
                            mView.onSuccessSwitch(userLoginRes);
                        }
                        getAccountInfo();
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
    public void getAccountInfo() {
        RemoteDataService.getInstance().getApiService(NetService.class)
                .getAccountInfo(HttpParams.getToken())
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new ResponseObserver<AccountInfoRes>(compositeDisposable) {
                    @Override
                    public void onSuccess(AccountInfoRes accountInfoRes) {
                        if (mView != null) {
                            mView.onSuccessAccountInfo(accountInfoRes);
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
    public void uploadImage(final String path) {

        File file = new File(path);
        RequestBody fileRQ = RequestBody.create(MediaType.parse("file/"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileRQ);
        RemoteDataService.getInstance().getApiService(NetService.class)
                .uploadOneFile(part, "other")
                .flatMap(new Function<CommonBean<String>, ObservableSource<CommonBean<String>>>() {
                    @Override
                    public ObservableSource<CommonBean<String>> apply(CommonBean<String> stringCommonBean) throws Exception {
                        if (stringCommonBean.success) {
                            UpdateUserImgReq updateUserImgReq = new UpdateUserImgReq();
                            userPhotoImgUrl = stringCommonBean.data;
                            updateUserImgReq.setUserPhotoImg(userPhotoImgUrl);
                            return RemoteDataService.getInstance().getApiService(NetService.class)
                                    .updateUserPhotoImg(HttpParams.getToken(), updateUserImgReq);
                        }
                        return Observable.error(new ResponseThrowable(10011,"图片上传失败"));
                    }
                })
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new ResponseObserver<String>(compositeDisposable) {
                    @Override
                    public void onSuccess(String string) {
                        if (mView != null) {
                            mView.updateUserPhotoImg(userPhotoImgUrl);
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
    public void queryUserPhotoImgUrl() {
        RemoteDataService.getInstance().getApiService(NetService.class)
                .queryUserPhotoImgUrl(HttpParams.getToken())
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new ResponseObserver<String>(compositeDisposable) {
                    @Override
                    public void onSuccess(String s) {
                        if (mView != null) {
                            mView.updateUserPhotoImg(s);
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
    public void queryDictByType(String type) {
        RemoteDataService.getInstance().getApiService(NetService.class)
                .queryDictListByType(type)
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new ResponseObserver<List<DictBean>>(compositeDisposable) {
                    @Override
                    public void onSuccess(List<DictBean> dictBeans) {
                        if (mView != null) {
                            mView.onSuccessDict(dictBeans);
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
    protected MyContract.Model getModel() {
        return null;
    }
}
