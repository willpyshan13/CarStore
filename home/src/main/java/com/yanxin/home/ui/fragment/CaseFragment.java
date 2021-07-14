package com.yanxin.home.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

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
import com.yanxin.home.adapter.CaseAdapter;
import com.yanxin.home.beans.h5.CaseParam;
import com.yanxin.home.beans.req.CaseListReq;
import com.yanxin.home.beans.res.CaseListItemRes;
import com.yanxin.home.mvp.contract.OnlineContract;
import com.yanxin.home.mvp.presenter.OnlinePresenter;
import com.yanxin.home.ui.activity.AskActivity;
import com.yanxin.home.ui.activity.CaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author zhouz
 * @date 2021/1/20
 */
public class CaseFragment extends BaseMVPFragment<OnlinePresenter> implements OnlineContract.View, RxClickUtils.OnViewClickListener {

    @BindView(R2.id.home_car_online_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R2.id.home_car_online_recycler_view)
    RecyclerView rv;

    @BindView(R2.id.car_home_release_case)
    TextView releaseCase;

    @BindView(R2.id.car_home_all_case)
    TextView allCase;

    private CaseAdapter adapter;
    private List<CaseListItemRes> list;

    /**
     * 是否有跳转activity  处理viewpager 嵌套 fragment不回掉 onHiddenChanged 问题
     */
    private boolean isSkip;
    private int pageNum;

    @Override
    protected OnlinePresenter setPresenter() {
        return new OnlinePresenter(this);
    }

    @Override
    protected View getContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_case, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pageNum = Config.PAGE_NUM;
        init();
    }

    @Override
    public void onRxViewClick(View view) {
        int id = view.getId();
        if (id == R.id.car_home_release_case) {
            isSkip = true;
            Router.getInstance().build(HomeRouterPath.CAR_ROUTER_WEB_VIEW)
                    .withString(H5Url.URL_PARAM_KEY, H5Url.RELEASE_CASE)
                    .start();
        } else if (id == R.id.car_home_all_case) {
            isSkip = true;
            startActivity(new Intent(mContext, AskActivity.class));
        }
    }

    private void init() {
        RxClickUtils.setRxClickListener(this, releaseCase, allCase);

        list = new ArrayList<>();
        adapter = new CaseAdapter(R.layout.recycler_item_home_car_case, list);
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            isSkip = true;
            //点击列表进入详情
            String caseUuid = list.get(position).getCaseUuid();
            CaseParam caseParam = new CaseParam();
            caseParam.setUuid(caseUuid);
            String json = new Gson().toJson(caseParam);

            Router.getInstance().build(HomeRouterPath.CAR_ROUTER_WEB_VIEW)
                    .withString(H5Url.URL_PARAM_KEY, H5Url.CASE_DETAIL)
                    .withString(H5Url.PARAM_PARAM_KEY, json)
                    .start();
        });

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = Config.PAGE_NUM;
                queryCaseList(pageNum, Config.PAGE_SIZE);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                queryCaseList(pageNum, Config.PAGE_SIZE);
            }
        });
    }

    private void queryCaseList(int pageNum, int pageSize) {
        CaseListReq caseListReq = new CaseListReq();
        caseListReq.setPageNum(pageNum);
        caseListReq.setPageSize(pageSize);
        presenter.queryCaseList(caseListReq);
    }

    @Override
    protected void onVisibilityChanged(boolean visible) {
        super.onVisibilityChanged(visible);
        if (visible) {
            pageNum = Config.PAGE_NUM;
            queryCaseList(pageNum, Config.PAGE_SIZE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isSkip) {
            isSkip = false;
            pageNum = Config.PAGE_NUM;
            queryCaseList(pageNum, Config.PAGE_SIZE);
        }
    }

    @Override
    public void onSuccess(List<CaseListItemRes> caseListItemRes) {

        if (pageNum == Config.PAGE_NUM) {
            list.clear();
            adapter.setNewData(list);
        }
        if (!caseListItemRes.isEmpty()) {
            pageNum++;
            list.addAll(caseListItemRes);
            adapter.setNewData(list);
        } else {
            if (pageNum == Config.PAGE_NUM) {
                adapter.setEmptyView(R.layout.common_empty_view, rv);
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
