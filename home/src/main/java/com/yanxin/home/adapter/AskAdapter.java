package com.yanxin.home.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.car.baselib.adapter.BaseQuickAdapter;
import com.car.baselib.adapter.BaseViewHolder;
import com.car.baselib.utils.GlideUtils;
import com.yanxin.home.R;
import com.yanxin.home.beans.res.CaseForVehicleListRes;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/17
 */
public class AskAdapter extends BaseQuickAdapter<CaseForVehicleListRes, BaseViewHolder> {


    public AskAdapter(int layoutResId, @Nullable List<CaseForVehicleListRes> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CaseForVehicleListRes item) {
        ImageView img = helper.itemView.findViewById(R.id.home_car_case_img);
        GlideUtils.loadUrlCircleImage(item.getImgUrl(), R.drawable.car_default, img);
        helper.setText(R.id.home_car_case_name, item.getTitle());
        helper.setText(R.id.home_car_case_technician_name, "技师:" + item.getTechnicianName());
        helper.setText(R.id.home_car_case_price, String.format("¥%.2f", item.getAmt()));
        TextView workingYear = helper.itemView.findViewById(R.id.home_car_technician_seniority);
        if (item.getWorkingYear() == null) {
            workingYear.setVisibility(View.GONE);
        } else {
            workingYear.setVisibility(View.VISIBLE);
            workingYear.setText("工龄:" + item.getWorkingYear());
        }
        TextView purchase = helper.itemView.findViewById(R.id.home_car_case_purchase);
    }
}
