package com.car.baselib.net.http;



import com.car.baselib.bean.CommonBean;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class ResponseObserver<T> implements Observer<CommonBean<T>> {

    private CompositeDisposable compositeDisposable;

    public ResponseObserver(CompositeDisposable disposable) {
        this.compositeDisposable = disposable;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (compositeDisposable != null) {
            compositeDisposable.add(d);
        }
    }

    @Override
    public void onNext(CommonBean<T> result) {
        if ("0000".equals(result.code)) {
            if (availableData(result)) {
                onSuccess(result.data);
            } else {
                onSuccess(null);
            }
        } else {
            int code = 1000;
            try {
                code = Integer.valueOf(result.code);
            } catch (Exception e) {
                e.printStackTrace();
            }
            onFailed(code, result.msg);
        }
    }

    @Override
    public void onError(Throwable e) {
        ResponseThrowable ex = null;
        if (e instanceof ResponseThrowable) {
            ex = (ResponseThrowable) e;
        } else {
             ex = ExceptionHandle.handleException(e);
        }
        onFailed(ex.code, ex.message);
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T t);

    protected boolean availableData(CommonBean<T> result) {
        return true;
    }

    public abstract void onFailed(int code, String message);
}
