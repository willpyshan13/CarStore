package com.car.baselib.activitystack;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.List;
import java.util.Vector;

/**
 * Activity生命周期管理类
 */
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    private final Vector<ActivityStatus> activityList = new Vector<>();
    private ActivityStatusChangeListener activityStatusChangeListener;

    private int startCount;

    public ActivityLifecycle() {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        ActivityStatus as = new ActivityStatus(activity, ActivityStatus.ONCREATE);
        if (contains(activityList, as)) {
            as.setStatus(ActivityStatus.ONCREATE);
            activityList.remove(as);
        } else {
            activityList.add(as);
        }
        onActivityStatusChanged();
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ActivityStatus as  = find(activityList, activity);
        if (as != null) {
            as.setStatus(ActivityStatus.ONSTART);
        } else {
            as = new ActivityStatus(activity, ActivityStatus.ONSTART);
            activityList.add(as);
        }
        onActivityStatusChanged();
        startCount ++;
        if (startCount == 1) {
            if (activityStatusChangeListener != null) {
                activityStatusChangeListener.onMoveForeground(true);
            }
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        ActivityStatus as  = find(activityList, activity);
        if (as != null) {
            as.setStatus(ActivityStatus.ONRESUME);
        } else {
            as = new ActivityStatus(activity, ActivityStatus.ONRESUME);
            activityList.add(as);
        }
        onActivityStatusChanged();
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ActivityStatus as  = find(activityList, activity);
        if (as != null) {
            as.setStatus(ActivityStatus.ONPAUSE);
        } else {
            as = new ActivityStatus(activity, ActivityStatus.ONPAUSE);
            activityList.add(as);
        }
        onActivityStatusChanged();
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ActivityStatus as  = find(activityList, activity);
        if (as != null) {
            as.setStatus(ActivityStatus.ONSTOP);
        } else {
            as = new ActivityStatus(activity, ActivityStatus.ONSTOP);
            activityList.add(as);
        }
        onActivityStatusChanged();
        startCount --;
        if (startCount == 0) {
            if (activityStatusChangeListener != null) {
                activityStatusChangeListener.onMoveForeground(false);
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityStatus as  = find(activityList, activity);
        if (as != null) {
            activityList.remove(as);
        }
        onActivityStatusChanged();
    }

    public Vector<ActivityStatus> getActivityList() {
        return activityList;
    }

    public void setActivityStatusChangeListener(ActivityStatusChangeListener activityStatusChangeListener) {
        this.activityStatusChangeListener = activityStatusChangeListener;
    }

    public boolean isForeground() {
        return startCount > 0;
    }

    private boolean contains(List<ActivityStatus> activityList, ActivityStatus as) {
        for (ActivityStatus activityStatus: activityList) {
            if (as.activity == activityStatus.activity) {
                return true;
            }
        }
        return false;
    }

    private ActivityStatus find(List<ActivityStatus> activityList, Activity activity) {
        for (ActivityStatus activityStatus: activityList) {
            if (activity == activityStatus.activity) {
                return activityStatus;
            }
        }
        return null;
    }

    private void onActivityStatusChanged() {
        if (activityStatusChangeListener != null) {
            activityStatusChangeListener.onActivityStatusChanged();
        }
    }

    static class ActivityStatus {
        public static final int ONCREATE = 1;
        public static final int ONSTART = 2;
        public static final int ONRESUME = 3;
        public static final int ONPAUSE = 4;
        public static final int ONSTOP = 5;
        public static final int ONDESTROY = 6;

        private final String[] STASUS = {"Unknown", "onCreate", "onStart", "onResume", "onPause", "onStop", "onDestroy"};

        private Activity activity;
        private int status;

        public ActivityStatus(Activity activity, int status) {
            this.activity = activity;
            this.status = status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }

        public void setActivity(Activity activity) {
            this.activity = activity;
        }

        public Activity getActivity() {
            return activity;
        }

        public boolean is(Activity activity) {
            return this.activity == activity;
        }

        public String getStatusString(int status) {
            if (status >= ONCREATE && status <= ONDESTROY) {
                return STASUS[status];
            }
            return STASUS[0];
        }
    }
}
