package com.car.baselib.activitystack;

public interface ActivityStatusChangeListener {
    /**
     * activity堆栈或者状态变化时触发
     */
    void onActivityStatusChanged();

    void onMoveForeground(boolean foreground);
}
