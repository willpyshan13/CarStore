package com.yanxin.home.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.car.baselib.adapter.BaseQuickAdapter;
import com.car.baselib.adapter.BaseViewHolder;
import com.car.baselib.utils.GlideUtils;
import com.yanxin.common.constants.Config;
import com.yanxin.home.R;
import com.yanxin.home.beans.res.GoodsBean;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/14
 */
public class GoodsAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {


    public GoodsAdapter(int layoutResId, @Nullable List<GoodsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean item) {
        ImageView img = helper.itemView.findViewById(R.id.home_car_goods_img);
        GlideUtils.load(item.getImgList().get(0).getImgPath(),R.drawable.home_car_driving_def,img);
        helper.setText(R.id.home_car_goods_name,item.getGoodsName());

        TextView grade = helper.itemView.findViewById(R.id.home_car_grade);
        if (item.getScore() > 0) {
            grade.setVisibility(View.VISIBLE);
            grade.setText(String.valueOf(item.getScore()));
        } else {
            grade.setVisibility(View.INVISIBLE);
        }

        helper.setText(R.id.home_car_grade_count,mContext.getString(R.string.car_home_grade_count,item.getCommentNum()));
        helper.setText(R.id.home_car_sales_volume,mContext.getString(R.string.car_home_goods_sales_volume,item.getSalesNum()));
        helper.setText(R.id.home_car_repertory,mContext.getString(R.string.car_home_goods_repertory,item.getSurplusNum()));

        List<GoodsBean.DetailListBean> detailList = item.getDetailList();
        BigDecimal price = BigDecimal.ZERO;
        if (detailList != null && !detailList.isEmpty()) {
            for (GoodsBean.DetailListBean detailListBean : detailList) {
                price = price.add(detailListBean.getActAmt());
            }
        }

        BigDecimal manHourCost = (item.getManHourCost() != null) ? item.getManHourCost() : BigDecimal.ZERO;
        BigDecimal materialsExpenses = (item.getMaterialsExpenses() != null) ? item.getMaterialsExpenses() : BigDecimal.ZERO;
        price = price.add(manHourCost);
        price = price.subtract(materialsExpenses);

        helper.setText(R.id.home_car_goods_price,mContext.getString(R.string.car_home_goods_price,price));
        String status = item.getSellSts() == Config.SHOP_RELEASE ? "已上架" : "已下架";
        helper.setText(R.id.home_car_goods_status,status);

    }
}
