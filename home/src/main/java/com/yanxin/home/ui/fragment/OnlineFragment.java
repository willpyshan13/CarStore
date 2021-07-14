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
public class OnlineFragment extends BaseFragment {

    @BindView(R2.id.common_toolbar)
    Toolbar toolbar;
    @BindView(R2.id.common_toolbar_title_tv)
    TextView toolbarTv;

    @BindArray(R2.array.car_online_title)
    String [] carOnlineTitle;
    @BindView(R2.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R2.id.view_pager)
    ScrollViewPager viewPager;

    private CaseFragment caseFragment;
    private AnswererFragment answererFragment;
    private DtcFragment dtcFragment;
    private TrainFragment trainFragment;
    private List<BaseFragment> fragments;
    private ViewPagerAdapter adapter;

    @Override
    protected View getContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_online,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setBackgroundColor(ContextCompat.getColor(mContext,R.color.home_toolbar_color));
        StatusBarUtil.setStatusBar(this,true,R.color.home_toolbar_color);
        toolbarTv.setTextColor(ContextCompat.getColor(mContext,R.color.home_toolbar_title_color));
        toolbarTv.setText(R.string.car_home_online);

        init();

    }

    private void init() {
        fragments = new ArrayList<>();
        caseFragment = new CaseFragment();
        answererFragment = new AnswererFragment();
        dtcFragment = new DtcFragment();
        trainFragment = new TrainFragment();
        fragments.add(caseFragment);
        fragments.add(answererFragment);
        fragments.add(dtcFragment);
        fragments.add(trainFragment);

        tabLayout.addTab( tabLayout.newTab().setText(carOnlineTitle[0]));
        tabLayout.addTab( tabLayout.newTab().setText(carOnlineTitle[1]));
        tabLayout.addTab( tabLayout.newTab().setText(carOnlineTitle[2]));
        tabLayout.addTab( tabLayout.newTab().setText(carOnlineTitle[3]));
        adapter = new ViewPagerAdapter(getChildFragmentManager(),0,fragments,carOnlineTitle);
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
}
