package com.yanxin.home.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.car.baselib.adapter.BaseQuickAdapter;
import com.car.baselib.adapter.BaseViewHolder;
import com.yanxin.home.R;
import com.yanxin.home.beans.res.DrivingBean;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/20
 */
public class DrivingAdapter extends BaseQuickAdapter<DrivingBean, BaseViewHolder> {


    public DrivingAdapter(int layoutResId, @Nullable List<DrivingBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DrivingBean item) {
        helper.setText(R.id.home_car_driving_price,mContext.getString(R.string.car_home_goods_price,item.getPrice()));
        helper.setText(R.id.home_car_driving_start_time,item.getStartTime());
        helper.setText(R.id.home_car_driving_distance,item.getDistance()+"km");
        helper.setText(R.id.home_car_driving_start_area,item.getStartPoint());
        helper.setText(R.id.home_car_driving_end_destination,item.getEndPoint());
        helper.setText(R.id.home_car_driving_journey_detail,item.getJourney()+"km");

        TextView click = helper.itemView.findViewById(R.id.home_car_driving_click);
    }
}
