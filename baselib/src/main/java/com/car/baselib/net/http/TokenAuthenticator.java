package com.car.baselib.net.http;


import com.car.baselib.BaseLibCore;
import com.car.baselib.cache.SpCache;
import com.car.baselib.router.Router;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * @author zhouz
 * @date 2020/5/26
 */
public class TokenAuthenticator implements Authenticator {

    private static final String TAG = "TokenAuthenticator";

    @Override
    public Request authenticate(Route route, final Response response) throws IOException {

        gotoLogin();
        return null;
    }

    private void gotoLogin() {
        BaseLibCore.getInstance().getActivityStack().finishAll();
        Router.getInstance().build("/login/login")
                .start();
    }

}
