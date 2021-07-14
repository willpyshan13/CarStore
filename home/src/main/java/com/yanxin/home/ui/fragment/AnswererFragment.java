package com.yanxin.home.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

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
import com.yanxin.home.adapter.AnswererAdapter;
import com.yanxin.home.beans.h5.CaseParam;
import com.yanxin.home.beans.h5.ConsultParam;
import com.yanxin.home.beans.req.PageReq;
import com.yanxin.home.beans.res.AnswererRes;
import com.yanxin.home.mvp.contract.AnswererContract;
import com.yanxin.home.mvp.presenter.AnswererPresenter;
import com.yanxin.home.ui.activity.AskActivity;
import com.yanxin.home.ui.activity.CaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author zhouz
 * @date 2021/1/20
 */
public class AnswererFragment extends BaseMVPFragment<AnswererPresenter> implements AnswererContract.View {


    @BindView(R2.id.home_car_answerer_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R2.id.home_car_answerer_recycler_view)
    RecyclerView rv;

    @BindView(R2.id.car_answerer_all)
    TextView answererAll;

    private AnswererAdapter adapter;
    private List<AnswererRes> list;

    /**是否有跳转activity  处理viewpager 嵌套 fragment不回掉 onHiddenChanged 问题*/
    private boolean isSkip;
    private int pageNum;

    @Override
    protected AnswererPresenter setPresenter() {
        return new AnswererPresenter(this);
    }

    @Override
    protected View getContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_answerer,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pageNum = Config.PAGE_NUM;
        init();
    }

    private void init() {

        list = new ArrayList<>();
        adapter = new AnswererAdapter(R.layout.recycler_item_home_car_answerer,list);
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                isSkip = true;
                AnswererRes answererRes = list.get(position);
                if (TextUtils.isEmpty(answererRes.getTechnicianUuid())) {
                    presenter.consultOrderSnapUp(answererRes.getUuid(),answererRes.getOrderUuid());
                } else {
                    goTechnicianAnswerer(answererRes.getOrderUuid());
                }
            }
        });

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = Config.PAGE_NUM;
                queryPreAnswerList(pageNum,Config.PAGE_SIZE);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                queryPreAnswerList(pageNum,Config.PAGE_SIZE);
            }
        });
        answererAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSkip = true;
                startActivity(new Intent(getContext(), CaseActivity.class));
            }
        });
    }

    private void goTechnicianAnswerer(String orderUuid) {
        ConsultParam consultParam = new ConsultParam();
        consultParam.setUuid(orderUuid);
        String json = new Gson().toJson(consultParam);
        Router.getInstance().build(HomeRouterPath.CAR_ROUTER_WEB_VIEW)
                .withString(H5Url.URL_PARAM_KEY,H5Url.TECHNICIAN_ANSWERER)
                .withString(H5Url.PARAM_PARAM_KEY,json)
                .start();
    }

    @Override
    protected void onVisibilityChanged(boolean visible) {
        super.onVisibilityChanged(visible);
        if (visible) {
            pageNum = Config.PAGE_NUM;
            queryPreAnswerList(pageNum,Config.PAGE_SIZE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isSkip) {
            isSkip = false;
            pageNum = Config.PAGE_NUM;
            queryPreAnswerList(pageNum,Config.PAGE_SIZE);
        }
    }

    private void queryPreAnswerList(int pageNum, int pageSize) {
        PageReq pageReq = new PageReq();
        pageReq.setPageNum(pageNum);
        pageReq.setPageSize(pageSize);
        presenter.queryPreAnswerList(pageReq);
    }

    @Override
    public void onSuccessAnswererList(List<AnswererRes> answererRes) {

        if (pageNum == Config.PAGE_NUM) {
            list.clear();
            adapter.setNewData(list);
        }
        if (!answererRes.isEmpty()) {
            pageNum ++;
            list.addAll(answererRes);
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
    public void onConsultOrderSuccess(String uuid) {
        goTechnicianAnswerer(uuid);
    }

    @Override
    public void onFailed(int code, String message) {
        ToastUtil.showToastS(message);
    }
}
