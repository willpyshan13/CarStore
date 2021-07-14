package com.car.baselib.activitystack;

import android.app.Activity;
import android.util.Log;


import com.car.baselib.BuildConfig;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Vector;

/**
 * Activity堆栈管理类
 */
public class ActivityStack {

    private final String TAG = "EBActivityStack";
    private Vector<ActivityLifecycle.ActivityStatus> activityList;
    private ActivityLifecycle activityLifecycle;
    //app前后台切换监听器
    private IAppStatusChangeListener appStatusChangeListener;

    private ActivityStatusChangeListener activityStatusChangeListener = new ActivityStatusChangeListener() {
        @Override
        public void onActivityStatusChanged() {
            if (BuildConfig.DEBUG) {
                printActivityStack();
            }
        }

        @Override
        public void onMoveForeground(boolean foreground) {
            if (appStatusChangeListener != null) {
                appStatusChangeListener.onAppMoveForeground(foreground);
            }
        }
    };

    public ActivityStack() {
        activityLifecycle = new ActivityLifecycle();
        activityList = activityLifecycle.getActivityList();
        activityLifecycle.setActivityStatusChangeListener(activityStatusChangeListener);
    }

    public ActivityLifecycle getActivityLifecycle() {
        return activityLifecycle;
    }

    /**
     * 获取栈顶Activity对象
     * @return
     */
    public Activity getTopActivity() {
        try {
            ActivityLifecycle.ActivityStatus as = activityList.lastElement();
            return as.getActivity();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * 获取栈顶ActivityStatus对象
     * @return
     */
    public ActivityLifecycle.ActivityStatus getTop() {
        try {
            return activityList.lastElement();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * 当前app是否可见，
     * @return
     */
    public boolean isVisible() {
        ActivityLifecycle.ActivityStatus top = getTop();
        return top.getStatus() != ActivityLifecycle.ActivityStatus.ONSTOP
                && top.getStatus() != ActivityLifecycle.ActivityStatus.ONDESTROY;
    }

    /**
     * 当前应用是否在前台
     * @return
     */
    public boolean isForeground() {
        return activityLifecycle.isForeground();
    }

    /**
     * 设置app前后台切换回调
     * @param appStatusChangeListener
     */
    public void setAppStatusChangeListener(IAppStatusChangeListener appStatusChangeListener) {
        this.appStatusChangeListener = appStatusChangeListener;
    }

    /**
     * 关闭堆栈中所有activity
     */
    public void finishAll() {
        List<ActivityLifecycle.ActivityStatus> list = new Vector<>();
        list.addAll(activityList);
        Collections.reverse(list);
        Iterator<ActivityLifecycle.ActivityStatus> iterator = list.iterator();
        while (iterator.hasNext()) {
            iterator.next().getActivity().finish();
        }
    }

    private void printActivityStack() {
        Log.d(TAG, "---当前堆栈中的Activity:---");
        for (ActivityLifecycle.ActivityStatus as : activityList) {
            String activityName = as.getActivity().getLocalClassName();
            String status = as.getStatusString(as.getStatus());
            Log.d(TAG, activityName + " : " + status);
        }
        Log.d(TAG, "-------------------------");
    }
}
