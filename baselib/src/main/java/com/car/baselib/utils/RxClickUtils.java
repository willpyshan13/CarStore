package com.car.baselib.utils;

import android.view.View;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author zhouz
 * @date 2020/2/26
 */
public class RxClickUtils {
    private static long mClickDuration = 1000;
    private static long mLastClickTime;

    public interface OnViewClickListener {
        /**
         * 点击事件回调
         *
         * @param view
         */
        void onRxViewClick(View view);
    }

    /**
     * 普通方式过滤多次点击事件
     */
    public static boolean isDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - mLastClickTime;
        if (0 < timeD && timeD < mClickDuration) {
            return true;
        }
        mLastClickTime = time;
        return false;
    }

    public static Disposable setRxClickListener(final OnViewClickListener viewClick, final View... views) {
        Disposable disposable = Observable.fromArray(views).flatMap(new Function<View, ObservableSource<View>>() {
            @Override
            public ObservableSource<View> apply(final View view) throws Exception {
                return Observable.create(new ObservableOnSubscribe<View>() {
                    @Override
                    public void subscribe(final ObservableEmitter<View> emitter) throws Exception {
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                emitter.onNext(view);
                            }
                        });
                    }
                });
            }
        }).throttleFirst(mClickDuration, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<View>() {
                    @Override
                    public void accept(View v) throws Exception {
                        viewClick.onRxViewClick(v);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.e(throwable.getMessage());
                    }
                });
        return disposable;
    }
}
