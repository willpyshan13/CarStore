package com.car.baselib.net.http;

public class HttpParams {

    private static String token;
    private static String refreshToken;


    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        HttpParams.token = token;
    }

    public static void setRefreshToken(String refreshToken) {
        HttpParams.refreshToken = refreshToken;
    }

    public static String getRefreshToken() {
        return refreshToken;
    }

}
