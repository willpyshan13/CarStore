package com.car.baselib.ui.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.car.baselib.mvp.BasePresenter;
import com.car.baselib.mvp.IView;

public abstract class BaseMVPFragment<T extends BasePresenter> extends BaseFragment implements IView {

    protected T presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = setPresenter();
        presenter.onCreate();
    }

    protected abstract T setPresenter();

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
