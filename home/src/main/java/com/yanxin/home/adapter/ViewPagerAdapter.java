package com.yanxin.home.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.car.baselib.ui.fragment.BaseFragment;

import java.util.List;

/**
 * @author zhouz
 * @date 2020/4/9
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mList;
    private String [] carAiaTitle;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior, List<BaseFragment> list, String [] carAiaTitle) {
        super(fm, behavior);
        mList = list;
        this.carAiaTitle = carAiaTitle;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (carAiaTitle == null) {
            return super.getPageTitle(position);
        }
        return carAiaTitle[position];
    }
}
