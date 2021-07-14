package com.yanxin.home.ui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.car.baselib.router.Router;
import com.car.baselib.ui.fragment.BaseFragment;
import com.car.baselib.ui.fragment.BaseMVPFragment;
import com.car.baselib.utils.LogUtils;
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
import com.yanxin.home.adapter.DtcAdapter;
import com.yanxin.home.beans.h5.CaseParam;
import com.yanxin.home.beans.h5.DtcParam;
import com.yanxin.home.beans.req.DtcListReq;
import com.yanxin.home.beans.req.PageReq;
import com.yanxin.home.beans.res.DtcBeanRes;
import com.yanxin.home.mvp.contract.DtcContract;
import com.yanxin.home.mvp.presenter.DtcPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author zhouz
 * @date 2021/2/9
 */
public class DtcFragment extends BaseMVPFragment<DtcPresenter> implements DtcContract.View , RxClickUtils.OnViewClickListener {

    @BindView(R2.id.home_car_dtc_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R2.id.home_car_dtc_recycler_view)
    RecyclerView dtcRv;
    @BindView(R2.id.car_home_dtc_ed)
    EditText dtcEd;
    @BindView(R2.id.home_car_dtc_release)
    TextView dtcRelease;

    private DtcAdapter dtcAdapter;
    private List<DtcBeanRes> list;
    /**是否有跳转activity  处理viewpager 嵌套 fragment不回掉 onHiddenChanged 问题*/
    private boolean isSkip;

    private int pageNum;

    @Override
    protected View getContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_dtc,null);
    }

    @Override
    protected DtcPresenter setPresenter() {
        return new DtcPresenter(this);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pageNum = Config.PAGE_NUM;

        RxClickUtils.setRxClickListener(this,dtcRelease);
        initRecyclerView();
        dtcEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pageNum = Config.PAGE_NUM;
                queryDtcList(pageNum,Config.PAGE_SIZE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onRxViewClick(View view) {
        int id = view.getId();
        if (id == R.id.home_car_dtc_release) {
            isSkip = true;
            Router.getInstance().build(HomeRouterPath.CAR_ROUTER_WEB_VIEW)
                    .withString(H5Url.URL_PARAM_KEY,H5Url.DTC_ADD_OR_EDIT)
                    .start();
        }
    }

    private void initRecyclerView() {
        list = new ArrayList<>();

        dtcAdapter = new DtcAdapter(R.layout.recycler_item_home_car_dtc,list);
        dtcRv.setAdapter(dtcAdapter);
        dtcRv.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));

        dtcAdapter.setOnItemClickListener((adapter, view, position) -> {
            isSkip = true;
            //点击列表进入详情
            String dtcUuid = list.get(position).getUuid();
            DtcParam dtcParam = new DtcParam();
            dtcParam.setUuid(dtcUuid);
            String json = new Gson().toJson(dtcParam);
            Router.getInstance().build(HomeRouterPath.CAR_ROUTER_WEB_VIEW)
                    .withString(H5Url.URL_PARAM_KEY,H5Url.DTC_DETAIL)
                    .withString(H5Url.PARAM_PARAM_KEY,json)
                    .start();
        });

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = Config.PAGE_NUM;
                queryDtcList(pageNum,Config.PAGE_SIZE);
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                queryDtcList(pageNum,Config.PAGE_SIZE);
            }
        });
    }

    @Override
    protected void onVisibilityChanged(boolean visible) {
        super.onVisibilityChanged(visible);
        if (visible) {
            pageNum = Config.PAGE_NUM;
           queryDtcList(pageNum,Config.PAGE_SIZE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isSkip) {
            isSkip = false;
            pageNum = Config.PAGE_NUM;
            queryDtcList(pageNum,Config.PAGE_SIZE);
        }
    }

    private void queryDtcList(int pageNum, int pageSize) {
        DtcListReq dtcListReq = new DtcListReq();
        dtcListReq.setPageNum(pageNum);
        dtcListReq.setPageSize(pageSize);
        dtcListReq.setDtcCode(dtcEd.getText().toString());
        presenter.queryDtcList(dtcListReq);
    }

    @Override
    public void onSuccessDtcList(List<DtcBeanRes> dtcBeanResList) {
        if (pageNum == Config.PAGE_NUM) {
            list.clear();
            dtcAdapter.setNewData(list);
        }
        if (!dtcBeanResList.isEmpty()) {
            pageNum ++;
            list.addAll(dtcBeanResList);
            dtcAdapter.setNewData(list);
        } else {
            if (pageNum == Config.PAGE_NUM) {
                dtcAdapter.setEmptyView(R.layout.common_empty_view,dtcRv);
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
