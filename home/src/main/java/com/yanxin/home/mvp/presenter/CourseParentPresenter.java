package com.yanxin.home.mvp.presenter;

import com.car.baselib.net.http.HttpParams;
import com.car.baselib.net.http.RemoteDataService;
import com.car.baselib.net.http.ResponseObserver;
import com.car.baselib.utils.RxSchedulersUtil;
import com.yanxin.home.api.NetService;
import com.yanxin.home.beans.req.PageReq;
import com.yanxin.home.beans.res.CourseParentInfoRes;
import com.yanxin.home.mvp.contract.CourseParentContract;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/2/27
 */
public class CourseParentPresenter extends CourseParentContract.Presenter {


    public CourseParentPresenter(CourseParentContract.View view) {
        super(view);
    }

    @Override
    public void queryCourseParentList(PageReq pageReq) {
        RemoteDataService.getInstance().getApiService(NetService.class)
                .queryCourseParentList(HttpParams.getToken(),pageReq)
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new ResponseObserver<List<CourseParentInfoRes>>(compositeDisposable) {
                    @Override
                    public void onSuccess(List<CourseParentInfoRes> courseParentInfoRes) {
                        if (mView != null) {
                            mView.onSuccessCourseParentList(courseParentInfoRes);
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
    public void queryCourseParentNewsList(PageReq pageReq) {
        RemoteDataService.getInstance().getApiService(NetService.class)
                .queryCourseParentNewsList(HttpParams.getToken(),pageReq)
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new ResponseObserver<List<CourseParentInfoRes>>(compositeDisposable) {
                    @Override
                    public void onSuccess(List<CourseParentInfoRes> courseParentInfoRes) {
                        if (mView != null) {
                            mView.onSuccessCourseParentNewsList(courseParentInfoRes);
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
    protected CourseParentContract.Model getModel() {
        return null;
    }
}
