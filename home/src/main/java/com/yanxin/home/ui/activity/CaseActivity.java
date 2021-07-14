package com.yanxin.home.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.car.baselib.ui.activity.BaseActivity;
import com.car.baselib.ui.fragment.BaseFragment;
import com.google.android.material.tabs.TabLayout;
import com.yanxin.home.R;
import com.yanxin.home.adapter.ViewPagerAdapter;
import com.yanxin.home.ui.fragment.CaseAnswererFragment;
import com.yanxin.home.ui.fragment.CaseIssueFragment;
import com.yanxin.home.widgt.ScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class CaseActivity extends BaseActivity {

    private CaseAnswererFragment answererFragment;
    private CaseIssueFragment issueFragment;
    private List<BaseFragment> mFragments;
    private ViewPagerAdapter viewPagerAdapter;
    private String[] mTitles = {"专家答主", "问题广场"};
    private TabLayout mTab;
    private ScrollViewPager mVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case);
        init();
    }

    private void init() {
        mTab = findViewById(R.id.qa_tab_layout);
        mVp = findViewById(R.id.qa_view_pager);
        mFragments = new ArrayList<>();
        answererFragment = new CaseAnswererFragment();
        mFragments.add(answererFragment);
        issueFragment = new CaseIssueFragment();
        mFragments.add(issueFragment);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0, mFragments, mTitles);
        mTab.setupWithViewPager(mVp);
        mVp.setScrollable(false);
        mVp.setAdapter(viewPagerAdapter);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
