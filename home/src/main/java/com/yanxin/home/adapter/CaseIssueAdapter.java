package com.yanxin.home.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.car.baselib.adapter.BaseQuickAdapter;
import com.car.baselib.adapter.BaseViewHolder;
import com.car.baselib.utils.GlideUtils;
import com.yanxin.home.R;
import com.yanxin.home.beans.res.IssueBeanRes;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/17
 */
public class CaseIssueAdapter extends BaseQuickAdapter<IssueBeanRes, BaseViewHolder> {


    public CaseIssueAdapter(int layoutResId, @Nullable List<IssueBeanRes> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IssueBeanRes item) {
        ImageView img = helper.itemView.findViewById(R.id.home_car_technician_img);
        GlideUtils.loadUrlCircleImage(item.getTechnicianImgUrl(), R.drawable.car_default, img);
        String carOwnerName = (item.getCarOwnerName() == null) ? "-" : item.getCarOwnerName();
        String nameText = item.getTechnicianName() + "回答了" + carOwnerName + "的问题";
        helper.setText(R.id.home_car_issue_name, nameText);
        helper.setText(R.id.home_car_issue_time, item.getCreatedTime());
        helper.setText(R.id.home_car_issue_title, item.getConsultDesc());
        helper.setText(R.id.home_car_answer_detail, item.getAnswerDesc());
        helper.setText(R.id.home_car_audit_price, "¥" + item.getConsultAmt());
        TextView audit = helper.itemView.findViewById(R.id.home_car_click_audit);
    }
}
