package com.yanxin.home.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.car.baselib.net.http.RemoteDataService;
import com.car.baselib.ui.activity.BaseActivity;
import com.car.baselib.utils.LogUtils;
import com.car.baselib.utils.RxSchedulersUtil;
import com.yanxin.common.constants.Config;
import com.yanxin.common.home.HomeRouterPath;
import com.yanxin.home.R;
import com.yanxin.home.R2;
import com.yanxin.home.api.NetService;
import com.yanxin.home.beans.QueryHideBean;
import com.yanxin.home.constants.Constants;
import com.yanxin.home.ui.fragment.DrivingFragment;
import com.yanxin.home.ui.fragment.FieldServiceFragment;
import com.yanxin.home.ui.fragment.GoodsFragment;
import com.yanxin.home.ui.fragment.MyFragment;
import com.yanxin.home.ui.fragment.OnlineFragment;
import com.yanxin.home.widgt.BottomBar;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * @author zhouz
 * @date 2021/1/15
 */
@Route(path = HomeRouterPath.CAR_ROUTER_HOME)
public class HomeActivity extends BaseActivity {

    @BindView(R2.id.home_bottom_bar)
    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        checkHide();
    }

    @SuppressLint("CheckResult")
    private void checkHide() {
        RemoteDataService.getInstance().getApiService(NetService.class)
                .queryHideModule(1)
                .compose(RxSchedulersUtil.io2mainObservable())
                .subscribe(new Consumer<QueryHideBean>() {
                    @Override
                    public void accept(QueryHideBean queryHideBean) throws Exception {
                        if (queryHideBean.isSuccess()) {
                            buildBottomBar(queryHideBean.getData().getLableValue());
                        }else{
                            buildBottomBar(1);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.e(throwable.getMessage());
                        buildBottomBar(1);
                    }
                });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkHide();
    }

    public void buildBottomBar(int lableValue) {
        bottomBar.clear();
        bottomBar.setContainer(R.id.home_content)
                .setTitleBeforeAndAfterColor("#FFB2BDCC", "#FF1684E3");
        if(lableValue == 0){
            bottomBar.addItem(DrivingFragment.class,
                    getString(R.string.car_home_driving),
                    R.drawable.home_car_driving_def,
                    R.drawable.home_car_driving_checked);
        }
        bottomBar.addItem(OnlineFragment.class,
                    getString(R.string.car_home_online),
                    R.drawable.home_car_online_def,
                    R.drawable.home_car_online_checked)
                .addItem(FieldServiceFragment.class,
                    getString(R.string.car_home_service),
                    R.drawable.home_car_maintenance_def,
                    R.drawable.home_car_maintenance_checked);
        if (Config.USER_TYPE_STORE.equals(Constants.getUserType())) {
            bottomBar.addItem(GoodsFragment.class,
                    getString(R.string.car_home_goods),
                    R.drawable.home_car_goods_def,
                    R.drawable.home_car_goods_checked);
        }
        bottomBar.addItem(MyFragment.class,
                getString(R.string.car_home_my),
                R.drawable.home_car_my_def,
                R.drawable.home_car_my_checked)
                .build();
    }
}
