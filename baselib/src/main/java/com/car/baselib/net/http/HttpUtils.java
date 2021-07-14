package com.car.baselib.net.http;




import com.car.baselib.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpUtils {

    private static OkHttpClient httpClient;
    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    private static Authenticator authenticator;
    private static Interceptor interceptor;


    public static void buildHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new RequestInterceptor())
//                .addInterceptor(interceptor)
                .authenticator(authenticator)
//                .sslSocketFactory(HttpsUtils.getSSLSocketFactory(),HttpsUtils.getX509TrustManager() )
//                .hostnameVerifier(new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String hostname, SSLSession session) {
//
//                        return HttpIP.getIpAddress().equals(hostname);
//                    }
//                })
                .retryOnConnectionFailure(true);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor LoginInterceptor = new HttpLoggingInterceptor();
            LoginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //添加retrofit日志打印
            builder.addInterceptor(LoginInterceptor);
        }
        httpClient = builder.build();
    }

    public static void setAuthenticator(Authenticator authenticator) {
        HttpUtils.authenticator = authenticator;
    }

    public static void addInterceptor(Interceptor interceptor) {
        HttpUtils.interceptor = interceptor;
    }

    public static OkHttpClient getHttpClient() {
        if (httpClient == null) {
            buildHttpClient();
        }
        return httpClient;
    }

//    public static void doHttpGet(HttpRequest httpRequest) {
//        doHttpRequest(httpRequest, "GET");
//    }
//
//    public static void doHttpPost(HttpRequest httpRequest) {
//        doHttpRequest(httpRequest, "POST");
//    }
//
//    public static void doHttpPut(HttpRequest httpRequest) {
//        doHttpRequest(httpRequest, "PUT");
//    }
//
//    public static void doHttpDelete(HttpRequest httpRequest) {
//        doHttpRequest(httpRequest, "DELETE");
//    }
//
//    private static void doHttpRequest(HttpRequest httpRequest, String method) {
//        if (httpRequest == null) {
//            return;
//        }
//        Call call = getCall(httpRequest, method);
//        call.enqueue(httpRequest.getHttpCallBack());
//    }
//
//    public static Response doHttpRequestSync(HttpRequest httpRequest, String method) throws IOException {
//        if (httpRequest == null) {
//            return null;
//        }
//        Call call = getCall(httpRequest, method);
//        return call.execute();
//    }
//
//    private static Call getCall(HttpRequest httpRequest, String method) {
//        if (!isSupportedMethod(method)) {
//            throw new RuntimeException("not supported http method for " + method);
//        }
//        Request.Builder requestBuilder = new Request.Builder().url(httpRequest.getUrl());
//        requestBuilder.tag(httpRequest);
//        Log.d(HttpConstants.TAG, "--------Http Request " + httpRequest.getHttpId() + "--------");
//        Log.d(HttpConstants.TAG, "url: " + httpRequest.getUrl());
//        Log.d(HttpConstants.TAG, "method: " + method);
//        Log.d(HttpConstants.TAG, "params:" + httpRequest.getParamsString());
//
//        if (method.equals("POST")
//                || method.equals("PUT")
//                || method.equals("DELETE")) {
//            RequestBody requestBody = null;
//            String paramsString = httpRequest.getParamsString();
//            if (paramsString == null) {
//                paramsString = "{}";
//            }
//            requestBody = RequestBody.create(JSON, paramsString);
//            requestBuilder.method(method.toUpperCase(), requestBody);
//        }
//        int timeOut = httpRequest.getTimeOutInMilliseconds();
//        OkHttpClient client = httpClient;
//        if (timeOut > 0) {
//            client = httpClient.newBuilder().connectTimeout(timeOut, TimeUnit.MILLISECONDS)
//                    .writeTimeout(timeOut, TimeUnit.MILLISECONDS)
//                    .readTimeout(timeOut, TimeUnit.MILLISECONDS).build();
//        }
//        Call call = client.newCall(requestBuilder.build());
//        return  call;
//    }
//
//    public static void postMultipartsRequest(HttpRequest httpRequest) {
//        if (httpRequest == null) {
//            return;
//        }
//        MultipartBody.Builder mBuilder = new MultipartBody.Builder();
//        mBuilder.setType(MultipartBody.FORM);
//        Map<String, Object> params = httpRequest.getParamsMap();
//        if (params != null && !params.isEmpty()) {
//            for (String key : params.keySet()) {
//                Object value = params.get(key);
//                if (value instanceof File) {
//                    File file = (File) value;
//                    mBuilder.addFormDataPart(key, file.getName(), RequestBody.create(null, file));
//                } else {
//                    if(value != null){
//                        mBuilder.addFormDataPart(key, value.toString());
//                    }
//                }
//            }
//        }
//        RequestBody body = mBuilder.build();
//        Request request = new Request.Builder().url(httpRequest.getUrl()).post(body).tag(httpRequest).build();
//        OkHttpClient client = httpClient.newBuilder().writeTimeout(60, TimeUnit.SECONDS).build();
//        Call call = client.newCall(request);
//        call.enqueue(httpRequest.getHttpCallBack());
//    }

    public static boolean isSupportedMethod(String method) {
        return "GET".equalsIgnoreCase(method)
                || "POST".equalsIgnoreCase(method)
                || "PUT".equalsIgnoreCase(method)
                || "DELETE".equalsIgnoreCase(method);
    }
}
