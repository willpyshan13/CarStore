package com.car.baselib.net.http;



import com.car.baselib.BaseLibCore;
import com.car.baselib.net.NetworkUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;

public class RequestInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!NetworkUtils.isConnected(BaseLibCore.getContext())) {
            return new Response.Builder()
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_1)
                    .code(426)
                    .message("当前网络不可用")
                    .body(Util.EMPTY_RESPONSE)
                    .sentRequestAtMillis(-1L)
                    .receivedResponseAtMillis(System.currentTimeMillis())
                    .build();
        }
        final Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
}
