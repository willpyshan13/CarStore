package com.car.baselib.utils;

import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.car.baselib.BaseLibCore;


/**
 * @author zhouz
 * @date 2020/2/28
 */
public class ToastUtil {

    public static void showToastL(@StringRes int resId) {
        Toast.makeText(BaseLibCore.getContext(), resId, Toast.LENGTH_LONG).show();
    }

    public static void showToastL(String text) {
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(BaseLibCore.getContext(), text, Toast.LENGTH_LONG).show();
        }
    }

    public static void showToastS(@StringRes int resId) {
        Toast.makeText(BaseLibCore.getContext(), resId, Toast.LENGTH_SHORT).show();
    }

    public static void showToastS(String text) {
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(BaseLibCore.getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }
}
