package com.yanxin.home.adapter;

import androidx.annotation.Nullable;

import com.car.baselib.adapter.BaseQuickAdapter;
import com.car.baselib.adapter.BaseViewHolder;
import com.yanxin.home.R;
import com.yanxin.home.beans.res.CaseListItemRes;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/23
 */
public class CaseAdapter extends BaseQuickAdapter<CaseListItemRes, BaseViewHolder> {


    public CaseAdapter(int layoutResId, @Nullable List<CaseListItemRes> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CaseListItemRes item) {
        helper.setText(R.id.home_car_case_name,item.getTitle());
        helper.setText(R.id.home_car_case_earnings_num,item.getAmt()+"");
        helper.setText(R.id.home_car_case_sales_volume_num,item.getNum()+"");
    }
}
