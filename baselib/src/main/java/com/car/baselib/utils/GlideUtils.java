package com.car.baselib.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.car.baselib.BaseLibCore;

/**
 * glide工具类
 */

public class GlideUtils {

    public static void load(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .into(imageView);
    }

    public static void load(Context context, String url, int defRes,ImageView imageView) {
        Glide.with(context)
                .load(url)
                .error(defRes)
                .centerCrop()
                .into(imageView);
    }

    public static void load(String url, int defRes,ImageView imageView) {
        load(BaseLibCore.getContext(), url,defRes, imageView);
    }

    public static void load(String url, ImageView imageView) {
        load(BaseLibCore.getContext(), url, imageView);
    }

    public static void loadWithDefaultImage(Context context, String url, int defaultImage, ImageView imageView) {
        Glide.with(BaseLibCore.getContext())
                .load(url)
                .placeholder(defaultImage)
                .error(defaultImage)
                .into(imageView);
    }

    public static void loadWithDefaultImage(String url, int defaultImage, ImageView imageView) {
        loadWithDefaultImage(BaseLibCore.getContext(), url, defaultImage, imageView);
    }

    public static void loadByte(Context context, byte[] url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    public static void loadCircleImage(Context context, byte[] url, ImageView imageView) {
        Glide.with(context).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);

    }

    public static void loadCircleDrawableImage(Context context, int resId, ImageView imageView) {
        Glide.with(context).load(resId).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(imageView);
    }

    public static void loadDrawableImage(int resId, ImageView imageView) {
        Glide.with(BaseLibCore.getContext()).load(resId).into(imageView);
    }

    public static void loadCenterCropImage(int resId, ImageView imageView) {
        Glide.with(BaseLibCore.getContext())
                .load(resId)
                .into(imageView);
    }

    public static void loadCenterCropImageUrl(String url, ImageView imageView) {
        Glide.with(BaseLibCore.getContext())
                .load(url)
                .into(imageView);
    }

    public static void loadUrlCircleImage( String url, int defaultimage,ImageView imageView) {
        Glide.with(BaseLibCore.getContext()).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop())).placeholder(defaultimage).into(imageView);

    }

    public static void loadCircleImage(byte[] url, ImageView imageView) {
        loadCircleImage(BaseLibCore.getContext(), url, imageView);
    }

    public static void loadCircleImage(byte[] url, ImageView imageView, int defaultimage) {
        loadCircleImage(BaseLibCore.getContext(), url, imageView, defaultimage);
    }

    public static void loadCircleImage(Context context, byte[] url, ImageView imageView, int defaultimage) {
        Glide.with(context).load(url).apply(RequestOptions.bitmapTransform(new CircleCrop())).placeholder(defaultimage).into(imageView);
    }
}
