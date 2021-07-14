package com.yanxin.home.adapter;

import androidx.annotation.Nullable;

import com.car.baselib.adapter.BaseQuickAdapter;
import com.car.baselib.adapter.BaseViewHolder;
import com.yanxin.home.R;
import com.yanxin.home.beans.FiltrateBean;

import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/18
 */
public class FiltrateAdapter extends BaseQuickAdapter<FiltrateBean, BaseViewHolder> {


    public FiltrateAdapter(int layoutResId, @Nullable List<FiltrateBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FiltrateBean item) {
        helper.setText(R.id.popup_filtrate_name,item.getName());
    }
}
