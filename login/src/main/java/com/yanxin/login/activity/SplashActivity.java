package com.yanxin.login.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.car.baselib.cache.SpCache;
import com.car.baselib.net.http.HttpParams;
import com.car.baselib.router.Router;
import com.car.baselib.ui.activity.BaseMVPActivity;
import com.car.baselib.utils.LogUtils;
import com.car.baselib.utils.RxSchedulersUtil;
import com.car.baselib.utils.StatusBarUtil;
import com.car.baselib.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.yanxin.login.R;
import com.yanxin.login.R2;
import com.yanxin.login.adapter.SplashBannerAdapter;
import com.yanxin.login.beans.BannerBean;
import com.yanxin.login.mvp.contract.SplashContract;
import com.yanxin.login.mvp.presenter.SplashPresenter;
import com.yanxin.common.constants.Config;
import com.yanxin.common.constants.H5Url;
import com.yanxin.common.home.HomeRouterPath;
import com.yanxin.common.login.LoginRouterPath;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnPageChangeListener;
import com.youth.banner.transformer.MZScaleInTransformer;
import com.youth.banner.util.BannerUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author zhouz
 * @date 2021/1/10
 */
@Route(path = LoginRouterPath.CAR_ROUTER_SPLASH)
public class SplashActivity extends BaseMVPActivity<SplashPresenter> implements SplashContract.View {


    @BindView(R2.id.car_splash_banner)
    Banner<BannerBean, SplashBannerAdapter> banner;
    @BindView(R2.id.login_splash_skip)
    TextView skip;

    private SplashBannerAdapter adapter;
    private List<BannerBean> list;
    private Integer[] res = {R.drawable.login_splash_1,R.drawable.login_splash_2,R.drawable.login_splash_3};

    @Override
    protected SplashPresenter setPresenter() {
        return new SplashPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_splash);
        StatusBarUtil.setStatusBar(this,true);
        init();
        presenter.splashConfig("guide_page_technician_store");
    }

    private void init() {
        list = new ArrayList<>();
        BannerBean bannerBean;
        for (int i = 0;i < res.length;i++) {
            bannerBean = new BannerBean();
            bannerBean.setResId(res[i]);
            list.add(bannerBean);
        }
    }

    private void initBanner() {
        adapter = new SplashBannerAdapter(list);
        banner.setAdapter(adapter)
                .addBannerLifecycleObserver(this)
                .setLoopTime(1000 * 2)
                .setScrollTime(1000 * 1)
                //添加切换效果
                .addPageTransformer(new MZScaleInTransformer())
                //设置指示器
                .setIndicator(new CircleIndicator(this))
//                .setOnBannerListener((OnBannerListener<BannerBean>) (data, position) -> {
//                    goNext();
//                })
//                .setIndicatorNormalColor()
                .setIndicatorSelectedColor(Color.parseColor("#FF1684E3"))
                .setIndicatorSpace((int) BannerUtils.dp2px(8))
                .setIndicatorWidth((int) BannerUtils.dp2px(8),(int) BannerUtils.dp2px(12))
                .setIndicatorMargins(new IndicatorConfig.Margins(0,0,0,300))
                //添加切换监听
                .addOnPageChangeListener(new OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (!list.isEmpty() && (list.size() - 1) == position){
                            banner.isAutoLoop(false);
                            Observable.just("")
                                    .delay(1000 * 2, TimeUnit.MILLISECONDS)
                                    .compose(RxSchedulersUtil.io2mainObservable())
                                    .subscribe(new Observer<String>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {
                                            addSubscribe(d);
                                        }

                                        @Override
                                        public void onNext(String s) {
                                            goNext();
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            LogUtils.e("onError: "+e.getLocalizedMessage());
                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
        skip.setOnClickListener(view ->{
            goNext();
        });
    }

    private void goNext() {
        String token = SpCache.get().getString(Config.TOKEN_SP_KEY);
        int checkSts = SpCache.get().getInt(Config.USER_CHECK_STS_SP_KEY,0);
        if (TextUtils.isEmpty(token)) {
            Router.getInstance().build(LoginRouterPath.CAR_ROUTER_LOGIN).start();
        } else {
            HttpParams.setToken(token);
            if (Config.CHECK_APPROVE != checkSts) {
                String url;
                if (Config.USER_TYPE_TECHNICIAN.equals(SpCache.get().getString(Config.USER_TYPE_SP_KEY))) {
                    url = H5Url.TECHNICIAN_INFO;
                } else {
                    url = H5Url.STORE_INFO;
                }
                Router.getInstance().build(HomeRouterPath.CAR_ROUTER_WEB_VIEW)
                        .withString(H5Url.URL_PARAM_KEY,url)
                        .withBoolean(H5Url.LOGIN_PARAM_KEY,true)
                        .start();
            } else {
                Router.getInstance().build(HomeRouterPath.CAR_ROUTER_HOME).start();
            }
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        banner.destroy();
        super.onDestroy();
    }

    @Override
    public void onSuccess(Object content) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        String loginBeanStr = gson.toJson(content);
        List<BannerBean> beanList = gson.fromJson(loginBeanStr,new TypeToken<List<BannerBean>>(){}.getType());
        if (beanList != null && !beanList.isEmpty()) {
            list.clear();
            list.addAll(beanList);
        }
        initBanner();
    }

    @Override
    public void onFailed(int code, String message) {
        ToastUtil.showToastS(message);
    }
}
