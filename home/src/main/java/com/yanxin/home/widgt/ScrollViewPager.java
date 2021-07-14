package com.yanxin.home.widgt;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * @author zhouz
 * @date 2020/4/9
 */
public class ScrollViewPager extends ViewPager {

    private boolean scrollable = true;

    public ScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewPager(Context context) {
        super(context);
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    public boolean isScrollable() {
        return scrollable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isScrollable()) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isScrollable()) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }
}
