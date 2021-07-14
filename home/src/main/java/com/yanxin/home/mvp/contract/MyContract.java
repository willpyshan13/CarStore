package com.yanxin.home.mvp.contract;

import com.car.baselib.mvp.BaseModel;
import com.car.baselib.mvp.BasePresenter;
import com.car.baselib.mvp.IView;
import com.yanxin.common.beans.res.UserLoginRes;
import com.yanxin.home.beans.req.PageReq;
import com.yanxin.home.beans.res.AccountInfoRes;
import com.yanxin.home.beans.res.AnswererRes;
import com.yanxin.home.beans.res.DictBean;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/31
 */
public interface MyContract {

    interface View extends IView {
        void onSuccessSwitch(UserLoginRes userLoginRes);
        void updateUserPhotoImg(String userPhotoImgUrl);
        void onSuccessAccountInfo(AccountInfoRes accountInfoRes);
        void onSuccessDict(List<DictBean> dictBeanList);
        void onFailed(int code, String message);
    }

    abstract class Model extends BaseModel {

    }

    abstract class Presenter extends BasePresenter<View, Model> {

        public Presenter(View view) {
            super(view);
        }

        /**
         * 技师店铺角色切换
         */
        public abstract void accountSwitchRole();

        /**
         * 获取账户信息
         */
        public abstract void getAccountInfo();

        /**
         * 上传图片
         */
        public abstract void uploadImage(String filePath);

        public abstract void queryUserPhotoImgUrl();

        /**
         * 字典类型查询字典 查询客服电话
         * @param type
         */
        public abstract void queryDictByType(String type);

    }
}
