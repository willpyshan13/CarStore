package com.car.baselib.utils;


import androidx.annotation.ColorRes;

import com.car.baselib.ui.activity.BaseActivity;
import com.car.baselib.ui.fragment.BaseFragment;
import com.zackratos.ultimatebarx.library.UltimateBarX;

/**
 * @author zhouz
 * @date 2021/1/10
 */
public class StatusBarUtil {

    public static void setStatusBar (BaseActivity activity,boolean isLight){
        setStatusBar(activity,isLight,false);
    }

    public static void setStatusBar (BaseActivity activity,boolean isLight,boolean isFit){
        UltimateBarX.with(activity)
                .light(isLight)
                .fitWindow(isFit)
                .applyStatusBar();
    }

    public static void setStatusBar (BaseFragment fragment, boolean isLight){
        UltimateBarX.with(fragment)
                .light(isLight)
                .applyStatusBar();
    }

    public static void setStatusBar (BaseFragment fragment, boolean isLight,@ColorRes int color){
        UltimateBarX.with(fragment)
                .light(isLight)
                .colorRes(color)
                .applyStatusBar();
    }
}
