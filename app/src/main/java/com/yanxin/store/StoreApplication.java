package com.yanxin.store;

import android.app.Application;

import com.car.baselib.BaseLibCore;
import com.car.baselib.net.UrlConstants;
import com.car.baselib.utils.WxApiUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yanxin.common.constants.H5UrlConstants;

/**
 * @author zhouz
 * @date 2021/1/16
 */
public class StoreApplication extends Application {
    private final String APP_ID = "wxe7d526a5f123af21";
    private static IWXAPI api;

    @Override
    public void onCreate() {
        super.onCreate();
        UrlConstants.setServerUrl(BuildConfig.SERVER_URL);
        H5UrlConstants.setH5Url(BuildConfig.H5_URL);
        BaseLibCore.getInstance().onInit(this);
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        api.registerApp(APP_ID);
        WxApiUtils.setmApi(api);
    }

    public static IWXAPI getApi() {
        return api;
    }
}
