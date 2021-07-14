package com.car.baselib.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.Postcard;

import java.io.Serializable;

/**
 * 第三方Router的封装
 */
public class RouterData {

    private Postcard data;
    private Context mContext;
    private Activity mActivity;
    private int requestCode;

    public RouterData(Postcard data){
        this.data = data;
    }

    public RouterData(Postcard data, Context context){
        this.data = data;
        this.mContext = context;
    }

    public RouterData(Postcard data, Activity activity, int requestCode){
        this.data = data;
        this.mActivity = activity;
        this.requestCode = requestCode;
    }

    /**
     * Set normal transition anim
     * @param enterAnim
     * @param exitAnim
     * @return
     */
    public RouterData withTransition(int enterAnim, int exitAnim) {
        data.withTransition(enterAnim,exitAnim);
        return this;
    }

    /**
     * Inserts a Bundle value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     * @param key
     * @param value
     * @return
     */
    public RouterData withBundle(@Nullable String key, @Nullable Bundle value) {
        data.withBundle(key, value);
        return this;
    }

    /**
     * Inserts an int value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     * @param key
     * @param value
     * @return
     */
    public RouterData withInt(@Nullable String key, int value) {
        data.withInt(key, value);
        return this;
    }

    /**
     * Inserts a long value into the mapping of this Bundle, replacing
     * any existing value for the given key.
     *
     * @param key   a String, or null
     * @param value a long
     * @return current
     */
    public RouterData withLong(@Nullable String key, long value) {
        data.withLong(key, value);
        return this;
    }

    /**
     * Inserts a String value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a String, or null
     * @return current
     */
    public RouterData withString(@Nullable String key, @Nullable String value) {
        data.withString(key, value);
        return this;
    }

    /**
     * Inserts a Boolean value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a boolean
     * @return current
     */
    public RouterData withBoolean(@Nullable String key, boolean value) {
        data.withBoolean(key, value);
        return this;
    }

    /**
     * Set object value, the value will be convert to string by 'Fastjson'
     *
     * @param key   a String, or null
     * @param value a Object, or null
     * @return current
     */
    public RouterData withObject(@Nullable String key, @Nullable Object value) {
        data.withObject(key, value);
        return this;
    }

    /**
     * Inserts a Serializable value into the mapping of this Bundle, replacing
     * any existing value for the given key.  Either key or value may be null.
     *
     * @param key   a String, or null
     * @param value a Serializable object, or null
     * @return current
     */
    public RouterData withSerializable(@Nullable String key, @Nullable Serializable value) {
        data.withSerializable(key, value);
        return this;
    }

    /**
     * Set special flags controlling how this intent is handled.  Most values
     * here depend on the type of component being executed by the Intent,
     * specifically the FLAG_ACTIVITY_* flags are all for use with
     * {@link Context#startActivity Context.startActivity()} and the
     * FLAG_RECEIVER_* flags are all for use with
     * {@link Context#sendBroadcast(Intent) Context.sendBroadcast()}.
     */
    public RouterData withFlags(int flag) {
        data.withFlags(flag);
        return this;
    }

    /**
     * BE ATTENTION TO THIS METHOD WAS <P>SET, NOT ADD!</P>
     */
    public RouterData with(Bundle bundle) {
        data.with(bundle);
        return this;
    }

    public RouterData setUri(Uri uri) {
        data.setUri(uri);
        return this;
    }

    public void start(){
        if(data != null){
            if(mContext != null){
                data.navigation(mContext);
            } else if (mActivity != null){
                if(requestCode == -1){
                    data.navigation(mActivity);
                }else{
                    data.navigation(mActivity,requestCode);
                }
            }else {
                data.navigation();
            }
        }
    }

}
