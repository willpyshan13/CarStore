package com.yanxin.store;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.car.baselib.router.Router;
import com.yanxin.common.login.LoginRouterPath;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Router.getInstance().build(LoginRouterPath.CAR_ROUTER_SPLASH).start();
        finish();
    }
}