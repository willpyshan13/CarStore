package com.yanxin.home.constants;

import android.text.TextUtils;

import com.car.baselib.cache.SpCache;
import com.yanxin.common.constants.Config;

/**
 * @author zhouz
 * @date 2021/2/5
 */
public class Constants {
    /** 用户类型 2 技师 3 店铺*/
    public static String getUserType() {
        return SpCache.get().getString(Config.USER_TYPE_SP_KEY);
    }

    public static void setUserType(String userType) {
        SpCache.get().putString(Config.USER_TYPE_SP_KEY,userType);
    }
}
