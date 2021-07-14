package com.yanxin.home.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.car.baselib.adapter.BaseQuickAdapter;
import com.car.baselib.adapter.BaseViewHolder;
import com.car.baselib.utils.GlideUtils;
import com.yanxin.home.R;
import com.yanxin.home.beans.res.CourseParentInfoRes;

import java.util.List;

/**
 * @author: xp
 * @date: 2017/7/19
 */

public class SortTrainAdapter extends BaseQuickAdapter<CourseParentInfoRes, BaseViewHolder> {


    public SortTrainAdapter(int layoutResId, @Nullable List<CourseParentInfoRes> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseParentInfoRes item) {
        ImageView img = helper.itemView.findViewById(R.id.home_car_train_img);
        GlideUtils.loadWithDefaultImage(item.getCourseCover(),R.drawable.car_default,img);
        helper.setText(R.id.home_car_train_name,item.getCourseTitle());
    }
}
