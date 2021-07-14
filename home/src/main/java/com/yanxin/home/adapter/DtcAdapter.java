package com.yanxin.home.adapter;

import androidx.annotation.Nullable;

import com.car.baselib.adapter.BaseQuickAdapter;
import com.car.baselib.adapter.BaseViewHolder;
import com.yanxin.home.R;
import com.yanxin.home.beans.res.DtcBeanRes;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/23
 */
public class DtcAdapter extends BaseQuickAdapter<DtcBeanRes, BaseViewHolder> {


    public DtcAdapter(int layoutResId, @Nullable List<DtcBeanRes> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DtcBeanRes item) {
        helper.setText(R.id.home_car_fault_code,item.getDtcCode());
        helper.setText(R.id.home_car_fault_catelog,item.getDtcDefinition());
        helper.setText(R.id.car_home_fault_brand,item.getConfigName());
    }
}
