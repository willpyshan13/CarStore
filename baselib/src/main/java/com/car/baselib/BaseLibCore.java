package com.car.baselib;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.car.baselib.activitystack.ActivityStack;
import com.car.baselib.activitystack.IAppStatusChangeListener;
import com.meituan.android.walle.WalleChannelReader;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

public class BaseLibCore implements IAppStatusChangeListener {

    private static Application context;
    private static BaseLibCore instance;
    private ActivityStack activityStack;

    private BaseLibCore() {
    }

    public static BaseLibCore getInstance(){
        if (instance == null) {
            synchronized (BaseLibCore.class){
                if (instance == null) {
                    instance = new BaseLibCore();
                }
            }
        }
        return instance;
    }
    public void onInit(Application context) {
        BaseLibCore.context = context;
        initArouter();
        initActivityStack();
        //初始化Bugly
        if(!BuildConfig.DEBUG) {
            initBuglyProcessControll();
        }
    }

    public static Context getContext() {
        return context;
    }

    private void initArouter() {
        //ARouter 初始化
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(context);
    }

    private void initActivityStack() {
        activityStack = new ActivityStack();
        registerActivityLifecycleCallback(activityStack.getActivityLifecycle());
        activityStack.setAppStatusChangeListener(this);
    }

    public ActivityStack getActivityStack() {
        return activityStack;
    }

    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        context.registerActivityLifecycleCallbacks(callbacks);
    }


    @Override
    public void onAppMoveForeground(boolean foreground) {

    }

    /**
     * 初始化Bugly
     */
    private void initBuglyProcessControll() {

        //true表示app启动自动初始化升级模块；
        Beta.autoInit = true;
        //true表示初始化时自动检查升级
        Beta.autoCheckUpgrade = true;
        String channel = WalleChannelReader.getChannel(context);
        Bugly.setAppChannel(context,channel);
        Bugly.init(context.getApplicationContext(), "a018475d1e", false);
    }
}
