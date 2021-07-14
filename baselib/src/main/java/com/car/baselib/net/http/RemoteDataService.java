package com.car.baselib.net.http;

import com.car.baselib.net.UrlConstants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RemoteDataService {

    static {
        HttpUtils.setAuthenticator(new TokenAuthenticator());
        RetrofitManager.getInstance().setBaseUrl(UrlConstants.getServerUrl());
        RetrofitManager.getInstance().initRetrofit();
    }

    private static RemoteDataService instance;
    private Map<String, Object> apiService = new ConcurrentHashMap<>();

    public static RemoteDataService getInstance() {
        if (instance == null) {
            synchronized (RemoteDataService.class) {
                if (instance == null) {
                    instance = new RemoteDataService();
                }
            }
        }
        return instance;
    }

    public  synchronized <T> T getApiService(Class<T> reqServer) {
        Object obj = apiService.get(reqServer.getName());
        if (obj == null) {
            obj = RetrofitManager.getInstance().createReq(reqServer);
            apiService.put(reqServer.getName(),obj);
        }
        return (T) obj;
    }
}
