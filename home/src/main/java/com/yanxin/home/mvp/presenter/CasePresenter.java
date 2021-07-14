package com.yanxin.home.mvp.presenter;

import com.car.baselib.net.http.HttpParams;
import com.car.baselib.net.http.RemoteDataService;
import com.car.baselib.net.http.ResponseObserver;
import com.car.baselib.utils.RxSchedulersUtil;
import com.yanxin.common.constants.Config;
import com.yanxin.home.api.NetService;
import com.yanxin.home.beans.FiltrateBean;
import com.yanxin.home.beans.req.CaseForVehicleListRep;
import com.yanxin.home.beans.res.CaseForVehicleListRes;
import com.yanxin.home.beans.res.DictBean;
import com.yanxin.home.beans.res.VehicleConfigBean;
import com.yanxin.home.mvp.contract.CaseContract;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/23
 */
public class CasePresenter extends CaseContract.Presenter {


    public CasePresenter(CaseContract.View view) {
        super(view);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        queryDictListByType(Config.ATTACH_SYS);
        queryVehicleConfigAllList(Config.REPAIR_BRAND);
    }

    @Override
    public void queryDictListByType(String type) {
        RemoteDataService.getInstance().getApiService(NetService.class)
                .queryDictListByType(type)
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new ResponseObserver<List<DictBean>>(compositeDisposable) {
                    @Override
                    public void onSuccess(List<DictBean> dictBeans) {
                        if (!dictBeans.isEmpty()) {
                            List<FiltrateBean> list = new ArrayList<>();
                            FiltrateBean filtrateBean;
                            for (DictBean dictBean : dictBeans) {
                                filtrateBean = new FiltrateBean();
                                filtrateBean.setType(Config.FILTRATE_TYPE_SYS);
                                filtrateBean.setUuid(dictBean.getUuid());
                                filtrateBean.setName(dictBean.getLableDesc());
                                list.add(filtrateBean);
                            }
                            if (mView != null) {
                                filtrateBean = new FiltrateBean();
                                filtrateBean.setType(Config.FILTRATE_TYPE_SYS);
                                filtrateBean.setName("全部系统");
                                list.add(0,filtrateBean);
                                mView.onSuccessSysList(list);
                            }
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
    public void queryVehicleConfigAllList(String parentUuid) {
        RemoteDataService.getInstance().getApiService(NetService.class)
                .queryVehicleConfigAllList(HttpParams.getToken(),parentUuid)
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new ResponseObserver<List<VehicleConfigBean>>(compositeDisposable) {
                    @Override
                    public void onSuccess(List<VehicleConfigBean> vehicleConfigBeans) {
                        if (!vehicleConfigBeans.isEmpty()) {
                            List<FiltrateBean> list = new ArrayList<>();
                            FiltrateBean filtrateBean;
                            int filtrateType = Config.REPAIR_BRAND.equals(parentUuid) ? Config.FILTRATE_TYPE_BRAND : Config.FILTRATE_TYPE_MODEL;
                            for (VehicleConfigBean configBean : vehicleConfigBeans) {
                                filtrateBean = new FiltrateBean();
                                filtrateBean.setType(filtrateType);
                                filtrateBean.setUuid(configBean.getUuid());
                                filtrateBean.setName(configBean.getConfigName());
                                list.add(filtrateBean);
                            }
                            if (mView != null) {
                                filtrateBean = new FiltrateBean();
                                filtrateBean.setType(filtrateType);
                                list.add(0,filtrateBean);
                                if (Config.REPAIR_BRAND.equals(parentUuid)) {
                                    filtrateBean.setName("全部品牌");
                                    mView.onSuccessBrandList(list);
                                } else {
                                    filtrateBean.setName("全部车型");
                                    mView.onSuccessModelList(list);
                                }

                            }
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
    public void queryCaseForVehicleList(CaseForVehicleListRep caseForVehicleListRep) {
        RemoteDataService.getInstance().getApiService(NetService.class)
                .queryVehicleConfigAllList(HttpParams.getToken(),caseForVehicleListRep)
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new ResponseObserver<List<CaseForVehicleListRes>>(compositeDisposable) {
                    @Override
                    public void onSuccess(List<CaseForVehicleListRes> caseForVehicleListRes) {
                        if (mView != null) {
                            mView.onSuccessCaseList(caseForVehicleListRes);
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
    protected CaseContract.Model getModel() {
        return null;
    }
}
