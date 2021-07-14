package com.car.baselib.utils;


import android.os.Handler;
import android.os.Looper;

/**
 * @author zhouz
 * @date 2020/4/22
 */
public class HandlerUtils {

    private Handler handler = new Handler(Looper.getMainLooper());
    private static volatile HandlerUtils instance;

    private HandlerUtils(){}
    public static HandlerUtils getInstance() {
        if (instance == null) {
            synchronized (HandlerUtils.class){
                if (instance == null) {
                    instance = new HandlerUtils();
                }
            }
        }

        return instance;
    }

    public Handler getHandler() {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }

        return handler;
    }
}
