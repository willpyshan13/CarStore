package com.yanxin.home.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.car.baselib.adapter.BaseQuickAdapter;
import com.car.baselib.router.Router;
import com.car.baselib.ui.fragment.BaseMVPFragment;
import com.car.baselib.utils.RxClickUtils;
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
import com.yanxin.home.adapter.FieldServiceAdapter;
import com.yanxin.home.beans.h5.ProductParam;
import com.yanxin.home.beans.h5.ServiceParam;
import com.yanxin.home.beans.req.SceneOrderReq;
import com.yanxin.home.beans.res.GoodsBean;
import com.yanxin.home.beans.res.SceneOrderListRes;
import com.yanxin.home.mvp.contract.ServiceContract;
import com.yanxin.home.mvp.presenter.ServicePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author zhouz
 * @date 2021/2/10
 */
public class ServiceOrderFragment extends BaseMVPFragment<ServicePresenter> implements ServiceContract.View , RxClickUtils.OnViewClickListener {

    @BindView(R2.id.home_car_service_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R2.id.home_car_service_recycler_view)
    RecyclerView serviceRv;
    @BindView(R2.id.home_car_service_add_order)
    TextView addOrder;

    private FieldServiceAdapter adapter;
    private List<SceneOrderListRes> list;
    private int orderStatus;

    private int pageNum;
    /**是否有跳转activity  处理viewpager 嵌套 fragment不回掉 onHiddenChanged 问题*/
    private boolean isSkip;

    public static ServiceOrderFragment newInstance(int orderStatus){
        Bundle bundle = new Bundle();
        ServiceOrderFragment serviceOrderFragment = new ServiceOrderFragment();
        bundle.putInt(Config.SERVICE_ORDER_TYPE_KEY,orderStatus);
        serviceOrderFragment.setArguments(bundle);
        return serviceOrderFragment;
    }

    @Override
    protected ServicePresenter setPresenter() {
        return new ServicePresenter(this);
    }

    @Override
    protected View getContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_service_order,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            orderStatus = arguments.getInt(Config.SERVICE_ORDER_TYPE_KEY);
            if (orderStatus == Config.SERVICE_ORDER_RELEASE) {
                addOrder.setVisibility(View.VISIBLE);
                RxClickUtils.setRxClickListener(this,addOrder);
            }
        }
        pageNum = Config.PAGE_NUM;
        initRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isSkip && orderStatus != Config.SERVICE_ORDER_RELEASE) {
            isSkip = false;
            pageNum = Config.PAGE_NUM;
            queryServiceOrder(pageNum,Config.PAGE_SIZE);
        }
    }

    @Override
    protected void onVisibilityChanged(boolean visible) {
        super.onVisibilityChanged(visible);
        if (visible) {
            pageNum = Config.PAGE_NUM;
            queryServiceOrder(pageNum,Config.PAGE_SIZE);
        }
    }

    private void queryServiceOrder(int pageNum, int pageSize) {
        SceneOrderReq sceneOrderReq = new SceneOrderReq();
        sceneOrderReq.setPageNum(pageNum);
        sceneOrderReq.setPageSize(pageSize);
        sceneOrderReq.setQueryType(orderStatus);
        presenter.querySceneOrderList(sceneOrderReq);
    }


    private void initRecyclerView() {
        list = new ArrayList<>();


        adapter = new FieldServiceAdapter(R.layout.recycler_item_home_car_service,list);
        serviceRv.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            //点击列表进入详情
            ToastUtil.showToastS("当前订单无更多详情...");
        });

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = Config.PAGE_NUM;
                queryServiceOrder(pageNum,Config.PAGE_SIZE);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                queryServiceOrder(pageNum,Config.PAGE_SIZE);
            }
        });

        adapter.setOnItemClickListener((adapter, view, position) -> {
            isSkip = true;
            //点击列表进入详情
            SceneOrderListRes sceneOrderListRes = list.get(position);
            ServiceParam serviceParam = new ServiceParam();
            serviceParam.setUuid(sceneOrderListRes.getUuid());
            String json = new Gson().toJson(serviceParam);
            Router.getInstance().build(HomeRouterPath.CAR_ROUTER_WEB_VIEW)
                    .withString(H5Url.URL_PARAM_KEY,H5Url.SCENCE_ORDER_DETAIL)
                    .withString(H5Url.PARAM_PARAM_KEY,json)
                    .start();
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (list.size() > position) {
                    SceneOrderListRes sceneOrderListRes = list.get(position);
                    presenter.grabbingOrders(sceneOrderListRes.getUuid());
                }

            }
        });
    }

    @Override
    public void onSuccessSceneOrderList(List<SceneOrderListRes> sceneOrderListRes) {
        if (pageNum == Config.PAGE_NUM) {
            list.clear();
            adapter.setNewData(list);
        }
        if (!sceneOrderListRes.isEmpty()) {
            pageNum ++;
            list.addAll(sceneOrderListRes);
            adapter.setNewData(list);
        } else {
            if (pageNum == Config.PAGE_NUM) {
                adapter.setEmptyView(R.layout.common_empty_view,serviceRv);
            }
        }
        smartRefreshLayout.finishLoadMore();
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void onSuccessGrabbingOrders(String message) {
        pageNum = Config.PAGE_NUM;
        queryServiceOrder(pageNum,Config.PAGE_SIZE);
    }

    @Override
    public void onFailed(int code, String message) {
        ToastUtil.showToastS(message);
    }

    @Override
    public void onRxViewClick(View view) {
        isSkip = true;
        Router.getInstance().build(HomeRouterPath.CAR_ROUTER_WEB_VIEW)
                .withString(H5Url.URL_PARAM_KEY,H5Url.SCENCE_ORDER)
                .start();
    }
}
