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
import com.yanxin.home.adapter.CaseIssueAdapter;
import com.yanxin.home.beans.h5.AuditParam;
import com.yanxin.home.beans.req.AuditListReq;
import com.yanxin.home.beans.res.IssueBeanRes;
import com.yanxin.home.mvp.contract.CaseIssueContract;
import com.yanxin.home.mvp.presenter.CaseIssuePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author zhouz
 * @date 2021/1/11
 */
public class CaseIssueFragment extends BaseMVPFragment<CaseIssuePresenter> implements CaseIssueContract.View, RxClickUtils.OnViewClickListener {

    @BindView(R2.id.home_car_issue_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R2.id.home_car_issue_rv)
    RecyclerView rv;
    @BindView(R2.id.home_car_ask)
    TextView tvAsk;

    private CaseIssueAdapter issueAdapter;
    private List<IssueBeanRes> list;

    private int pageNum = Config.PAGE_NUM;

    @Override
    protected CaseIssuePresenter setPresenter() {
        return new CaseIssuePresenter(this);
    }


    @Override
    protected View getContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_issue,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RxClickUtils.setRxClickListener(this,tvAsk);
        init();
    }

    private void init() {
        list = new ArrayList<>();
        issueAdapter = new CaseIssueAdapter(R.layout.recycler_item_home_car_issue,list);
        rv.setAdapter(issueAdapter);
        issueAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IssueBeanRes issueBeanRes = list.get(position);
                AuditParam auditParam = new AuditParam();
                auditParam.setUuid(issueBeanRes.getUuid());
                auditParam.setOrderUuid(issueBeanRes.getOrderUuid());
                String json = new Gson().toJson(auditParam);
                Router.getInstance().build(HomeRouterPath.CAR_ROUTER_WEB_VIEW)
                        .withString(H5Url.URL_PARAM_KEY, H5Url.AUDIT)
                        .withString(H5Url.PARAM_PARAM_KEY,json)
                        .start();
            }
        });


        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = Config.PAGE_NUM;
                queryAuditList(pageNum, Config.PAGE_SIZE);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                queryAuditList(pageNum, Config.PAGE_SIZE);
            }
        });
        pageNum = Config.PAGE_NUM;
        queryAuditList(pageNum, Config.PAGE_SIZE);
    }

    private void queryAuditList(int pageNum, int pageSize) {
        AuditListReq auditListReq = new AuditListReq();
        auditListReq.setPageNum(pageNum);
        auditListReq.setPageSize(Config.PAGE_SIZE);
        presenter.queryAnswerList(auditListReq);
    }

    @Override
    public void onSuccessIssueBeanList(List<IssueBeanRes> issueBeanResList) {

        if (pageNum == Config.PAGE_NUM && !issueBeanResList.isEmpty()) {
            list.clear();
            issueAdapter.setNewData(list);
        }
        if (issueBeanResList.size() > 0) {
            pageNum ++;
            list.addAll(issueBeanResList);
            issueAdapter.setNewData(list);
        } else {
            if (pageNum == Config.PAGE_NUM) {
                issueAdapter.setEmptyView(R.layout.common_empty_view,rv);
            }
        }
        smartRefreshLayout.finishLoadMore();
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void onFailed(int code, String message) {
        ToastUtil.showToastS(message);
    }

    @Override
    public void onRxViewClick(View view) {
        Router.getInstance().build(HomeRouterPath.CAR_ROUTER_WEB_VIEW)
                .withString(H5Url.URL_PARAM_KEY, H5Url.ASK)
                .start();
    }
}
