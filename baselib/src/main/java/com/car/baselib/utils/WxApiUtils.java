package com.car.baselib.utils;

import com.tencent.mm.opensdk.openapi.IWXAPI;

public class WxApiUtils {

    private static IWXAPI mApi;

    public static IWXAPI getmApi() {
        return mApi;
    }

    public static void setmApi(IWXAPI mApi) {
        WxApiUtils.mApi = mApi;
    }
}
