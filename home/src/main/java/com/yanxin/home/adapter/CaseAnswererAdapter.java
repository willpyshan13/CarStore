package com.yanxin.home.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.car.baselib.adapter.BaseQuickAdapter;
import com.car.baselib.adapter.BaseViewHolder;
import com.car.baselib.utils.GlideUtils;
import com.yanxin.home.R;
import com.yanxin.home.beans.res.TechnicianListRes;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/14
 */
public class CaseAnswererAdapter extends BaseQuickAdapter<TechnicianListRes, BaseViewHolder> {


    public CaseAnswererAdapter(int layoutResId, @Nullable List<TechnicianListRes> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TechnicianListRes item) {
        ImageView img = helper.itemView.findViewById(R.id.home_car_answerer_img);
        GlideUtils.loadUrlCircleImage(item.getPhotoImgUrl(), R.drawable.car_default, img);
        helper.setText(R.id.home_car_answerer_name, item.getName());
        helper.setText(R.id.home_car_grade, String.valueOf(item.getScore()));
        helper.setText(R.id.home_car_consult_count, item.getQaCount() + "次咨询");
        helper.setText(R.id.home_car_consult, item.getTechnologyType());
        String tag = "";
        if (item.getCybAuth() == 1) {
            tag = "专家技师";
        } else {
            tag = "普通技师";
        }

        helper.setText(R.id.home_car_answerer_tag, tag);
        List<TechnicianListRes.BrandResListBean> brandResList = item.getBrandResList();
        if (brandResList == null || brandResList.isEmpty()) {
            helper.setText(R.id.home_car_maintain_adept, "擅长:");
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < brandResList.size(); i++) {
                TechnicianListRes.BrandResListBean brandBean = brandResList.get(i);
                builder.append(brandBean.getBrandName());
                if (i < (brandResList.size() - 1)) {
                    builder.append("、");
                }
            }
            helper.setText(R.id.home_car_maintain_adept, "擅长:" + builder.toString());
        }

        //添加点击事件 todo
        TextView click = helper.itemView.findViewById(R.id.home_car_click_consult);

        helper.setText(R.id.home_car_consult_price, String.format("¥%.2f", item.getAnswerAmt()));
    }
}
