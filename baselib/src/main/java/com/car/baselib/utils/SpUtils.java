package com.car.baselib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.car.baselib.BaseLibCore;
import java.util.Set;

public class SpUtils {

    private SharedPreferences sp;

    public SpUtils(String fileName) {
        sp = BaseLibCore.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public void putString(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public void putInt(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public void putBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public void putLong(String key, long value) {
        sp.edit().putLong(key, value).apply();
    }

    public void putStringSet(String key, Set<String> value) {
        sp.edit().putStringSet(key, value);
    }

    public String getString(String key) {

        return sp.getString(key, null) ;
}

    public int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public Set<String> getStringSet(String key) {
        return sp.getStringSet(key, null);
    }

    public void remove(String key) {
        sp.edit().remove(key).apply();
    }

    public void clear() {
        sp.edit().clear().apply();
    }
}
