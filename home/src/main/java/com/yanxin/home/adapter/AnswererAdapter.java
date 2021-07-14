package com.yanxin.home.adapter;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.car.baselib.adapter.BaseQuickAdapter;
import com.car.baselib.adapter.BaseViewHolder;
import com.yanxin.common.constants.Config;
import com.yanxin.home.R;
import com.yanxin.home.beans.res.AnswererRes;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/23
 */
public class AnswererAdapter extends BaseQuickAdapter<AnswererRes, BaseViewHolder> {


    public AnswererAdapter(int layoutResId, @Nullable List<AnswererRes> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnswererRes item) {
        helper.setText(R.id.home_car_qa_name,item.getTitle());
        helper.setText(R.id.home_car_qa_consultDesc,item.getConsultDesc());
        helper.setText(R.id.home_car_qa_price,mContext.getString(R.string.car_home_goods_price,item.getConsultAmt()));
        if (Config.HOME_CAR_CONSULT_TECHNICIAN_TYPE == item.getConsultType()) {
            if (Config.HOME_CAR_QA_STATUS == item.getAnswerSts()) {
                helper.setText(R.id.home_car_qa,R.string.car_home_case_qa);
            } else {
                helper.setText(R.id.home_car_qa,R.string.car_home_case_qa_already);
            }
        } else if (Config.HOME_CAR_CONSULT_NATIONWIDE_TYPE == item.getConsultType()) {
            if (TextUtils.isEmpty(item.getTechnicianUuid())) {
                helper.setText(R.id.home_car_qa,R.string.car_home_case_answer);
            } else {
                if (Config.HOME_CAR_QA_STATUS != item.getAnswerSts()) {
                    helper.setText(R.id.home_car_qa,R.string.car_home_case_qa_already);
                } else {
                    helper.setText(R.id.home_car_qa,R.string.car_home_case_already_answer);
                }
            }
        }

//        FriendsCircleImageLayout imageLayout = helper.itemView.findViewById(R.id.home_car_qa_images);
//        imageLayout.setImageUrls(item.getConsultImgList());
    }
}
