package com.yanxin.home.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.car.baselib.adapter.BaseQuickAdapter;
import com.car.baselib.adapter.BaseViewHolder;
import com.yanxin.common.constants.Config;
import com.yanxin.home.R;
import com.yanxin.home.beans.res.SceneOrderListRes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouz
 * @date 2021/1/23
 */
public class FieldServiceAdapter extends BaseQuickAdapter<SceneOrderListRes, BaseViewHolder> {
    static Map<Integer,String> map = new HashMap<>();
    static {
        map.put(0,"待支付");
        map.put(1,"已支付");
        map.put(2,"已取消");
        map.put(3,"退款中");
        map.put(4,"退款成功");
        map.put(5,"退款失败");
        map.put(6,"已完成");
    }

    public FieldServiceAdapter(int layoutResId, @Nullable List<SceneOrderListRes> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SceneOrderListRes item) {
        TextView paySts = helper.itemView.findViewById(R.id.home_car_service_sts);
        TextView payPrice = helper.itemView.findViewById(R.id.home_car_service_price);
        TextView onClick = helper.itemView.findViewById(R.id.home_car_service_click);
        if (item.getGrabbingOrdersSts() == Config.SERVICE_ORDER_ACCESS) {
            paySts.setTextColor(ContextCompat.getColor(mContext,R.color.home_price_color));
            paySts.setText(mContext.getString(R.string.car_home_goods_price,item.getTotalAmount()));
            payPrice.setTextColor(ContextCompat.getColor(mContext,R.color.home_brand_color));
            payPrice.setText(mContext.getString(R.string.home_car_service_distance,item.getDistance()));
            if (item.isIsOneself()) {
                onClick.setVisibility(View.GONE);
            } else {
                onClick.setVisibility(View.VISIBLE);
            }
        } else if (item.getGrabbingOrdersSts() == Config.SERVICE_ORDER_ALREADY) {
            paySts.setText(map.get(item.getOrderSts()));
            payPrice.setText(mContext.getString(R.string.car_home_goods_price,item.getTotalAmount()));
            paySts.setTextColor(ContextCompat.getColor(mContext,R.color.home_brand_color));
            payPrice.setTextColor(ContextCompat.getColor(mContext,R.color.home_brand_color));
            onClick.setVisibility(View.GONE);
        } else if (item.getGrabbingOrdersSts() == Config.SERVICE_ORDER_RELEASE) {
            paySts.setText(map.get(item.getOrderSts()));
            payPrice.setText(mContext.getString(R.string.car_home_goods_price,item.getTotalAmount()));
            paySts.setTextColor(ContextCompat.getColor(mContext,R.color.home_brand_color));
            payPrice.setTextColor(ContextCompat.getColor(mContext,R.color.home_brand_color));
            onClick.setVisibility(View.GONE);
        }
        helper.setText(R.id.home_car_service_brand_detail,item.getBrandName());
//        BigDecimal amount = item.getGoodsPrice().multiply(BigDecimal.valueOf(item.getGoodsNum())).add(item.getServicePrice());
//        String detail = mContext.getString(R.string.home_car_service_goods_pay_detail,item.getGoodsPrice()+"x"+item.getGoodsNum(),
//                item.getServicePrice(),amount+"");
        helper.setText(R.id.home_car_service_vehicle_model_detail,item.getCarModelName());
        helper.setText(R.id.home_car_service_error_description_detail,item.getFaultDesc());
        helper.addOnClickListener(R.id.home_car_service_click);
    }
}
