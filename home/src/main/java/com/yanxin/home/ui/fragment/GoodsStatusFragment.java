package com.yanxin.home.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.car.baselib.router.Router;
import com.car.baselib.ui.fragment.BaseFragment;
import com.car.baselib.ui.fragment.BaseMVPFragment;
import com.car.baselib.utils.LogUtils;
import com.car.baselib.utils.ToastUtil;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yanxin.common.constants.Config;
import com.yanxin.common.constants.H5Url;
import com.yanxin.common.home.HomeRouterPath;
import com.yanxin.home.R;
import com.yanxin.home.R2;
import com.yanxin.home.adapter.GoodsAdapter;
import com.yanxin.home.beans.h5.ProductParam;
import com.yanxin.home.beans.req.GoodsReq;
import com.yanxin.home.beans.req.PageReq;
import com.yanxin.home.beans.res.GoodsBean;
import com.yanxin.home.mvp.contract.GoodsContract;
import com.yanxin.home.mvp.presenter.GoodsPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author zhouz
 * @date 2021/1/16
 */
public class GoodsStatusFragment extends BaseMVPFragment<GoodsPresenter> implements GoodsContract.View {

    @BindView(R2.id.home_car_goods_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R2.id.home_car_goods_recycler_view)
    RecyclerView rv;

    private List<GoodsBean> list;
    private GoodsAdapter adapter;
    private int status;
    private int pageNum = Config.PAGE_NUM;

    public void setSkip(boolean skip) {
        isSkip = skip;
    }

    /**是否有跳转activity  处理viewpager 嵌套 fragment不回掉 onHiddenChanged 问题*/
    private boolean isSkip;

    public static GoodsStatusFragment newInstance(int goodsStatus){
        Bundle bundle = new Bundle();
        GoodsStatusFragment goodsStatusFragment = new GoodsStatusFragment();
        bundle.putInt(Config.GOODS_STATUS_KEY,goodsStatus);
        goodsStatusFragment.setArguments(bundle);
        return goodsStatusFragment;
    }

    @Override
    protected GoodsPresenter setPresenter() {
        return new GoodsPresenter(this);
    }


    @Override
    protected View getContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_goods_status,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        Bundle arguments = getArguments();
        if (arguments != null) {
            status = arguments.getInt(Config.GOODS_STATUS_KEY);
        }
    }

    @Override
    protected void onVisibilityChanged(boolean visible) {
        super.onVisibilityChanged(visible);
        LogUtils.d("visible: "+visible+" status:"+status);
        if (visible) {
            pageNum = Config.PAGE_NUM;
            queryGoodsList(pageNum,Config.PAGE_SIZE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isSkip && Config.SHOP_RELEASE == status) {
            isSkip = false;
            pageNum = Config.PAGE_NUM;
            queryGoodsList(pageNum,Config.PAGE_SIZE);
        }
    }

    private void queryGoodsList(int pageNum, int pageSize) {
        GoodsReq goodsReq = new GoodsReq();
        goodsReq.setPageNum(pageNum);
        goodsReq.setPageSize(pageSize);
        goodsReq.setSellSts(status);
        presenter.queryGoodsList(goodsReq);
    }
    private void init() {
        list = new ArrayList<>();
        adapter = new GoodsAdapter(R.layout.recycler_item_home_car_goods,list);
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            isSkip = true;
            //点击列表进入详情
            GoodsBean goodsBean = list.get(position);
            ProductParam productParam = new ProductParam();
            productParam.setUuid(goodsBean.getUuid());
            String json = new Gson().toJson(productParam);
            Router.getInstance().build(HomeRouterPath.CAR_ROUTER_WEB_VIEW)
                    .withString(H5Url.URL_PARAM_KEY,H5Url.PRODUCT_DETAIL)
                    .withString(H5Url.PARAM_PARAM_KEY,json)
                    .start();

        });

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = Config.PAGE_NUM;
                queryGoodsList(pageNum,Config.PAGE_SIZE);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                queryGoodsList(pageNum,Config.PAGE_SIZE);
            }
        });
    }

    @Override
    public void onSuccess(List<GoodsBean> goodsBeans) {
        if (pageNum == Config.PAGE_NUM) {
            list.clear();
            adapter.setNewData(list);
        }
        if (!goodsBeans.isEmpty()) {
            pageNum ++;
            list.addAll(goodsBeans);
            adapter.setNewData(list);
        } else {
            if (pageNum == Config.PAGE_NUM) {
                adapter.setEmptyView(R.layout.common_empty_view,rv);
            }
        }
        smartRefreshLayout.finishLoadMore();
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void onFailed(int code, String message) {
        ToastUtil.showToastS(message);
    }
}
