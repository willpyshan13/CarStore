package com.yanxin.home.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.car.baselib.adapter.BaseQuickAdapter;
import com.car.baselib.router.Router;
import com.car.baselib.ui.fragment.BaseMVPFragment;
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
import com.yanxin.home.adapter.CaseAnswererAdapter;
import com.yanxin.home.beans.CaseAnswererBean;
import com.yanxin.home.beans.req.TechnicianListReq;
import com.yanxin.home.beans.res.TechnicianListRes;
import com.yanxin.home.mvp.contract.CaseAnswererContract;
import com.yanxin.home.mvp.presenter.CaseAnswererPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author zhouz
 * @date 2021/1/11
 */
public class CaseAnswererFragment extends BaseMVPFragment<CaseAnswererPresenter> implements CaseAnswererContract.View {

    @BindView(R2.id.home_car_answerer_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R2.id.home_car_answerer_recycler_view)
    RecyclerView answererRv;

    private List<TechnicianListRes> list;
    private CaseAnswererAdapter adapter;

    private int pageNum = Config.PAGE_NUM;


    @Override
    protected CaseAnswererPresenter setPresenter() {
        return new CaseAnswererPresenter(this);
    }


    @Override
    protected View getContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_case_answerer, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pageNum = Config.PAGE_NUM;
        init();
    }

    @Override
    protected void onVisibilityChanged(boolean visible) {
        super.onVisibilityChanged(visible);
        if (visible) {
            pageNum = Config.PAGE_NUM;
            TechnicianListReq technicianListReq = new TechnicianListReq();
            technicianListReq.setPageNum(pageNum);
            technicianListReq.setPageSize(Config.PAGE_SIZE);
            presenter.queryTechnicianList(technicianListReq);
        }
    }

    private void init() {

        list = new ArrayList<>();
        adapter = new CaseAnswererAdapter(R.layout.recycler_item_home_car_case_answerer, list);
        answererRv.setAdapter(adapter);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = Config.PAGE_NUM;
                queryTechnicianList(pageNum, Config.PAGE_SIZE);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                queryTechnicianList(pageNum, Config.PAGE_SIZE);
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TechnicianListRes technicianListRes = list.get(position);
                CaseAnswererBean answererBean = new CaseAnswererBean();
                answererBean.setTechnicianUuid(technicianListRes.getUuid());
                String json = new Gson().toJson(answererBean);
                Router.getInstance().build(HomeRouterPath.CAR_ROUTER_WEB_VIEW)
                        .withString(H5Url.URL_PARAM_KEY, H5Url.ASK)
                        .withString(H5Url.PARAM_PARAM_KEY, json)
                        .start();
            }
        });

    }

    @Override
    public void onFragmentVisibilityChanged(boolean visible) {
        super.onFragmentVisibilityChanged(visible);
        if (visible) {
            queryTechnicianList(pageNum, Config.PAGE_SIZE);
        }
    }

    private void queryTechnicianList(int pageNum, int pageSize) {
        TechnicianListReq technicianListReq = new TechnicianListReq();
        technicianListReq.setPageNum(pageNum);
        technicianListReq.setPageSize(pageSize);
        presenter.queryTechnicianList(technicianListReq);
    }

    @Override
    public void onSuccessTechnicianList(List<TechnicianListRes> technicianListRes) {

        if (pageNum == Config.PAGE_NUM) {
            list.clear();
            adapter.setNewData(list);
        }
        if (!technicianListRes.isEmpty()) {
            pageNum++;
            list.addAll(technicianListRes);
            adapter.setNewData(list);
        } else {
            if (pageNum == Config.PAGE_NUM) {
                adapter.setEmptyView(R.layout.common_empty_view, answererRv);
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
