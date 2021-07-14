package com.car.baselib.mvp;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<V extends IView, M extends BaseModel> {

    protected CompositeDisposable compositeDisposable;

    protected V mView;
    protected M mModel;

    public BasePresenter(V view) {
        this.mView = view;
        compositeDisposable = new CompositeDisposable();
        mModel = getModel();
    }

    protected abstract M getModel();

    public void addSubscribe(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void onCreate() {}
    public void onNewIntent(){}
    public void onStart() {}
    public void onPause() {}
    public void onResume(){}
    public void onStop() {}
    public void onDestroy() {
        compositeDisposable.clear();
    }

}
