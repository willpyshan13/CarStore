package com.yanxin.home.mvp.contract;

import com.car.baselib.mvp.BaseModel;
import com.car.baselib.mvp.BasePresenter;
import com.car.baselib.mvp.IView;
import com.yanxin.home.beans.FiltrateBean;
import com.yanxin.home.beans.req.CaseForVehicleListRep;
import com.yanxin.home.beans.res.CaseForVehicleListRes;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/23
 */
public interface CaseContract {
    interface View extends IView {
        void onSuccessBrandList(List<FiltrateBean> brandList);
        void onSuccessModelList(List<FiltrateBean> modelList);
        void onSuccessSysList(List<FiltrateBean> sysList);
        void onSuccessCaseList(List<CaseForVehicleListRes> caseForVehicleListRes);
        void onFailed(int code, String message);
    }

    abstract class Model extends BaseModel {

    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public Presenter(View view) {
            super(view);
        }

        /**
         * 根据字典类型查询字典集合
         * @param type
         */
        public abstract void queryDictListByType(String type);

        /**
         * 查询所有车辆配置信息(车型)
         */
        public abstract void queryVehicleConfigAllList(String parentUuid);

        /**
         * 查询所有技师案例
         */
        public abstract void queryCaseForVehicleList(CaseForVehicleListRep caseForVehicleListRep);

    }
}
