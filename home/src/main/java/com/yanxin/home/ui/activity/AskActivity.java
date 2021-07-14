package com.yanxin.home.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.car.baselib.adapter.BaseQuickAdapter;
import com.car.baselib.router.Router;
import com.car.baselib.ui.activity.BaseMVPActivity;
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
import com.yanxin.home.adapter.AskAdapter;
import com.yanxin.home.adapter.CaseAdapter;
import com.yanxin.home.beans.FiltrateBean;
import com.yanxin.home.beans.h5.CaseParam;
import com.yanxin.home.beans.h5.CaseUuidParam;
import com.yanxin.home.beans.req.CaseForVehicleListRep;
import com.yanxin.home.beans.res.CaseForVehicleListRes;
import com.yanxin.home.mvp.contract.CaseContract;
import com.yanxin.home.mvp.presenter.CasePresenter;
import com.yanxin.home.popup.FiltrateWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AskActivity extends BaseMVPActivity<CasePresenter> implements CaseContract.View {


    private RecyclerView rv;
    private SmartRefreshLayout smartRefreshLayout;
    private TextView brand;
    private TextView vehicleModel;
    private TextView system;


    private int pageNum = Config.PAGE_NUM;
    private AskAdapter caseAdapter;
    private FiltrateWindow filtrateWindow;
    private String brandUuid;
    private String vehicleModelUuid;
    private String systemUuid;
    private List<FiltrateBean> brandList;
    private List<FiltrateBean> modelList;
    private List<FiltrateBean> sysList;
    private List<CaseForVehicleListRes> caseForVehicleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);
        init();
        initRecyclerView();
    }

    private void initRecyclerView() {
        caseForVehicleList = new ArrayList<>();
        caseAdapter = new AskAdapter(R.layout.recycler_item_home_car_ask, caseForVehicleList);
        rv.setAdapter(caseAdapter);

        caseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CaseForVehicleListRes caseForVehicleListRes = caseForVehicleList.get(position);
                CaseUuidParam caseParam = new CaseUuidParam();
                caseParam.setCaseUuid(caseForVehicleListRes.getUuid());
                String json = new Gson().toJson(caseParam);
                Router.getInstance().build(HomeRouterPath.CAR_ROUTER_WEB_VIEW)
                        .withString(H5Url.URL_PARAM_KEY, H5Url.CASE_NEWS_DETAIL)
                        .withString(H5Url.PARAM_PARAM_KEY, json)
                        .start();
            }
        });

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = Config.PAGE_NUM;
                queryCaseForVehicleList();
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                queryCaseForVehicleList();
            }
        });
        pageNum = Config.PAGE_NUM;
        queryCaseForVehicleList();
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 查询案例列表
     */
    private void queryCaseForVehicleList() {
        CaseForVehicleListRep caseForVehicleListRep = new CaseForVehicleListRep();
        caseForVehicleListRep.setAttachSys(systemUuid);
        caseForVehicleListRep.setBrandUuid(brandUuid);
        caseForVehicleListRep.setModel(vehicleModelUuid);
        caseForVehicleListRep.setPageNum(pageNum);
        caseForVehicleListRep.setPageSize(Config.PAGE_SIZE);
        presenter.queryCaseForVehicleList(caseForVehicleListRep);
    }

    private void init() {
        rv = findViewById(R.id.home_car_case_rv);
        smartRefreshLayout = findViewById(R.id.home_car_case_refresh_layout);
        brand = findViewById(R.id.home_car_case_brand);
        vehicleModel = findViewById(R.id.home_car_case_vehicle_model);
        system = findViewById(R.id.home_car_case_system);
        brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWindow(brand, brandList);
            }
        });
        vehicleModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(brandUuid)) {
                    ToastUtil.showToastS("请选择车辆品牌");
                } else {
                    presenter.queryVehicleConfigAllList(brandUuid);
                }
            }
        });
        system.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWindow(system, sysList);
            }
        });
    }

    @Override
    protected CasePresenter setPresenter() {
        return new CasePresenter(this);
    }

    @Override
    public void onSuccessBrandList(List<FiltrateBean> brands) {
        if (brandList != null) {
            brandList.clear();
            brandList.addAll(brands);
        } else {
            brandList = brands;
        }
    }

    @Override
    public void onSuccessModelList(List<FiltrateBean> models) {
        if (modelList != null) {
            modelList.clear();
            modelList.addAll(models);
        } else {
            modelList = models;
        }
        showWindow(vehicleModel, modelList);
    }

    @Override
    public void onSuccessSysList(List<FiltrateBean> list) {
        if (sysList != null) {
            sysList.clear();
            sysList.addAll(list);
        } else {
            sysList = list;
        }
    }

    @Override
    public void onSuccessCaseList(List<CaseForVehicleListRes> caseForVehicleListRes) {
        if (pageNum == Config.PAGE_NUM && !caseForVehicleList.isEmpty()) {
            caseForVehicleList.clear();
            caseAdapter.setNewData(caseForVehicleList);
        }
        if (caseForVehicleListRes.size() > 0) {
            pageNum++;
            caseForVehicleList.addAll(caseForVehicleListRes);
            caseAdapter.setNewData(caseForVehicleList);
        } else {
            if (pageNum == Config.PAGE_NUM) {
                caseAdapter.setEmptyView(R.layout.common_empty_view, rv);
            }
        }
        smartRefreshLayout.finishLoadMore();
        smartRefreshLayout.finishRefresh();
    }

    @Override
    public void onFailed(int code, String message) {
        ToastUtil.showToastS(message);
    }

    private void showWindow(View view, List<FiltrateBean> list) {
        filtrateWindow = FiltrateWindow.newBuilder()
                .setWidth((int) getResources().getDimension(R.dimen.popup_window_filtrate_width))
                .setHeight((int) getResources().getDimension(R.dimen.popup_window_filtrate_height))
                .setOnArithmeticList(list)
                .setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        filtrateWindow = null;
                    }
                })
                .setOnSelectFiltrateListener(new FiltrateWindow.OnSelectFiltrateListener() {
                    @Override
                    public void onSelectFiltrate(FiltrateBean filtrateBean) {
                        int type = filtrateBean.getType();
                        if (Config.FILTRATE_TYPE_BRAND == type) {
                            brandUuid = filtrateBean.getUuid();
                            brand.setText(filtrateBean.getName());
                            if (TextUtils.isEmpty(brandUuid)) {
                                vehicleModelUuid = filtrateBean.getUuid();
                                vehicleModel.setText("全部系统");
                            }

                        } else if (Config.FILTRATE_TYPE_SYS == type) {
                            systemUuid = filtrateBean.getUuid();
                            system.setText(filtrateBean.getName());
                        } else if (Config.FILTRATE_TYPE_MODEL == type) {
                            vehicleModelUuid = filtrateBean.getUuid();
                            vehicleModel.setText(filtrateBean.getName());
                        }
                        pageNum = Config.PAGE_NUM;
                        queryCaseForVehicleList();
                    }
                })
                .build(this);
        filtrateWindow.showAtLocation(view.getRootView(), Gravity.CENTER, 0, 0);
    }

}
