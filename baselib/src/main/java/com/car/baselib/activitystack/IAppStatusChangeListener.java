package com.car.baselib.activitystack;

public interface IAppStatusChangeListener {

    //app前后台切换监听
    void onAppMoveForeground(boolean foreground);
}
