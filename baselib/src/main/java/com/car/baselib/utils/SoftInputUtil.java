package com.car.baselib.utils;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;

/**
 *  * 软键盘管理器
 *  * 
 *  
 */
public class SoftInputUtil {
    /**
     * 隱藏软键盘
     *  
     *
     * @param mContext
     */
    public static void hideSoftInput(Activity mContext) {
        try {
            ((InputMethodManager) mContext
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(mContext.getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 显示软键盘
     *  
     *
     * @param mContext
     */
    public static void showSoftInput(Activity mContext) {
        try {
            ((InputMethodManager) mContext
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isShouldHideInput(@NonNull View v, @NonNull MotionEvent event) {
        if (v != null && event != null && v instanceof EditText) {
            int[] leftTop = new int[]{0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return event.getX() <= (float)left || event.getX() >= (float)right || event.getY() <= (float)top || event.getY() >= (float)bottom;
        } else {
            return false;
        }
    }
}
