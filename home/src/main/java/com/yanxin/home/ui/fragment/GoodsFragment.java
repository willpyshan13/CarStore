package com.yanxin.home.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;

import com.car.baselib.cache.SpCache;
import com.car.baselib.router.Router;
import com.car.baselib.ui.fragment.BaseFragment;
import com.car.baselib.utils.DisplayUtil;
import com.car.baselib.utils.RxClickUtils;
import com.car.baselib.utils.StatusBarUtil;
import com.car.baselib.utils.ToastUtil;
import com.google.android.material.tabs.TabLayout;
import com.yanxin.common.constants.Config;
import com.yanxin.common.constants.H5Url;
import com.yanxin.common.home.HomeRouterPath;
import com.yanxin.home.R;
import com.yanxin.home.R2;
import com.yanxin.home.adapter.ViewPagerAdapter;
import com.yanxin.home.constants.Constants;
import com.yanxin.home.widgt.ScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * @author zhouz
 * @date 2021/1/16
 */
public class GoodsFragment extends BaseFragment implements RxClickUtils.OnViewClickListener {

    @BindView(R2.id.common_toolbar)
    Toolbar toolbar;
    @BindView(R2.id.common_toolbar_title_tv)
    TextView toolbarTv;

    @BindView(R2.id.car_home_release_goods)
    TextView releaseGoods;

    @BindArray(R2.array.car_goods_title)
    String [] carGoodsTitle;
    @BindView(R2.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R2.id.view_pager)
    ScrollViewPager viewPager;

    private GoodsStatusFragment releaseFragment;
    private GoodsStatusFragment unReleaseFragment;
    private List<BaseFragment> fragments;
    private ViewPagerAdapter adapter;

    @Override
    protected View getContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_goods,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setBackgroundColor(ContextCompat.getColor(mContext,R.color.home_toolbar_color));
        StatusBarUtil.setStatusBar(this,true,R.color.home_toolbar_color);
        toolbarTv.setTextColor(ContextCompat.getColor(mContext,R.color.home_toolbar_title_color));
        toolbarTv.setText(R.string.car_home_goods);

        RxClickUtils.setRxClickListener(this,releaseGoods);
        init();
    }

    private void init() {

        fragments = new ArrayList<>();
        releaseFragment = GoodsStatusFragment.newInstance(Config.SHOP_RELEASE);
        unReleaseFragment = GoodsStatusFragment.newInstance(Config.SHOP_UN_RELEASE);
        fragments.add(releaseFragment);
        fragments.add(unReleaseFragment);

        tabLayout.addTab( tabLayout.newTab().setText(carGoodsTitle[0]));
        tabLayout.addTab( tabLayout.newTab().setText(carGoodsTitle[1]));
        adapter = new ViewPagerAdapter(getChildFragmentManager(),0,fragments,carGoodsTitle);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setScrollable(false);

        TextView title = (TextView)(((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(0)).getChildAt(1));
        title.setTextSize(DisplayUtil.px2dip(mContext,getResources().getDimension(R.dimen.tab_layout_text_size_checked)));
        TextViewCompat.setTextAppearance(title, R.style.TabLayoutTextStyleBold);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                TextView title = (TextView)(((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                title.setTextSize(DisplayUtil.px2dip(mContext,getResources().getDimension(R.dimen.tab_layout_text_size_checked)));
                TextViewCompat.setTextAppearance(title, R.style.TabLayoutTextStyleBold);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                TextView title = (TextView)(((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
                title.setTextSize(DisplayUtil.px2dip(mContext,getResources().getDimension(R.dimen.tab_layout_text_size_def)));
                TextViewCompat.setTextAppearance(title, R.style.TabLayoutTextStyleNormal);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onRxViewClick(View view) {
        int id = view.getId();
        if (id == R.id.car_home_release_goods) {
            if (Config.USER_TYPE_STORE.equals(Constants.getUserType())) {
                if (viewPager.getCurrentItem() == 0) {
                    //上架商品项
                    releaseFragment.setSkip(true);
                }
                //发布商品 跳转webView
                Router.getInstance().build(HomeRouterPath.CAR_ROUTER_WEB_VIEW)
                        .withString(H5Url.URL_PARAM_KEY,H5Url.PUT_AWAY_PRODUCT)
                        .start();
            } else {
                ToastUtil.showToastS(R.string.car_home_release_good_hint);
            }

        }
    }
}
