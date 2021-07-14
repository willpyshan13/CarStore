package com.car.baselib.utils;

import android.util.Log;

import com.car.baselib.BuildConfig;


/**
 * @author zhouz
 * @date 2019/4/8
 */
public class LogUtils {
    /** 类名 */
    static String className;
    /** 方法名 */
    static String methodName;
    /** 行数 */
    static int lineNumber;
    static final String TAG = LogUtils.class.getName();

    /**
     * 判断是否可以调试
     * @return
     */
    private static boolean isDebuggable() {
        return BuildConfig.DEBUG;
    }

    private static String createLog(String log ) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" ").append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append("): ");
        buffer.append(log);
        return buffer.toString();
    }

    /**
     * 获取文件名、方法名、所在行数
     * @param sElements
     */
    private static void getMethodNames(StackTraceElement[] sElements){
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(String message){
//        if (!isDebuggable())
//            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.e(TAG, createLog(message));
    }

    public static void i(String message){
        if (!isDebuggable()) {
            return;
        }
        getMethodNames(new Throwable().getStackTrace());
        Log.i(TAG, createLog(message));
    }

    public static void d(String message){
        if (!isDebuggable()) {
            return;
        }
        getMethodNames(new Throwable().getStackTrace());
        Log.d(TAG, createLog(message));
    }

    public static void v(String message){
        if (!isDebuggable()) {
            return;
        }
        getMethodNames(new Throwable().getStackTrace());
        Log.v(TAG, createLog(message));
    }

    public static void w(String message){
        if (!isDebuggable()) {
            return;
        }
        getMethodNames(new Throwable().getStackTrace());
        Log.w(TAG, createLog(message));
    }
}
