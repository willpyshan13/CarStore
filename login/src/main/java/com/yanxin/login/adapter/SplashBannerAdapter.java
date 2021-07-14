package com.yanxin.login.adapter;


import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.car.baselib.adapter.BaseViewHolder;
import com.car.baselib.utils.GlideUtils;
import com.yanxin.login.R;
import com.yanxin.login.beans.BannerBean;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/10
 */
public class SplashBannerAdapter extends BannerAdapter<BannerBean, BaseViewHolder> {


    public SplashBannerAdapter(List<BannerBean> datas) {
        super(datas);
    }

    @Override
    public BaseViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.banner_item_layout, parent, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindView(BaseViewHolder holder, BannerBean data, int position, int size) {
        ImageView bannerImg = holder.itemView.findViewById(R.id.login_banner_img);
        if (TextUtils.isEmpty(data.getImgUrl())) {
            GlideUtils.loadCenterCropImage(data.getResId(),bannerImg);
        } else {
            GlideUtils.loadCenterCropImageUrl(data.getImgUrl(),bannerImg);
        }

    }
}
