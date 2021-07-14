package com.yanxin.home.mvp.presenter;

import com.car.baselib.net.http.HttpParams;
import com.car.baselib.net.http.RemoteDataService;
import com.car.baselib.net.http.ResponseObserver;
import com.car.baselib.utils.RxSchedulersUtil;
import com.yanxin.home.api.NetService;
import com.yanxin.home.beans.req.GoodsReq;
import com.yanxin.home.beans.res.GoodsBean;
import com.yanxin.home.mvp.contract.GoodsContract;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/16
 */
public class GoodsPresenter extends GoodsContract.Presenter {

    public GoodsPresenter(GoodsContract.View view) {
         super(view);
    }

    @Override
    public void queryGoodsList(GoodsReq goodsReq) {
        RemoteDataService.getInstance().getApiService(NetService.class)
                .queryGoodsList(HttpParams.getToken(),goodsReq)
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new ResponseObserver<List<GoodsBean>>(compositeDisposable) {
                    @Override
                    public void onSuccess(List<GoodsBean> goodsBeans) {
                        if (mView != null) {
                            mView.onSuccess(goodsBeans);
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
    protected GoodsContract.Model getModel() {
        return null;
    }
}
