package com.car.baselib.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.car.baselib.mvp.BasePresenter;
import com.car.baselib.mvp.IView;


public abstract class BaseMVPActivity<T extends BasePresenter> extends BaseActivity implements IView {

    protected T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = setPresenter();
        presenter.onCreate();
    }

    protected abstract T setPresenter();

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        presenter.onNewIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
