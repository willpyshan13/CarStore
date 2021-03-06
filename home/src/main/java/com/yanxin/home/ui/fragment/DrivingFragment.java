package com.yanxin.home.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.car.baselib.adapter.BaseQuickAdapter;
import com.car.baselib.ui.fragment.BaseFragment;
import com.car.baselib.utils.StatusBarUtil;
import com.car.baselib.utils.ToastUtil;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yanxin.home.R;
import com.yanxin.home.R2;
import com.yanxin.home.adapter.DrivingAdapter;
import com.yanxin.home.beans.res.DrivingBean;
import com.yanxin.home.utils.LocationUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author zhouz
 * @date 2021/1/16
 */
public class DrivingFragment extends BaseFragment {

    @BindView(R2.id.common_toolbar)
    Toolbar toolbar;
    @BindView(R2.id.common_toolbar_title_tv)
    TextView toolbarTv;

    @BindView(R2.id.home_car_driving_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R2.id.home_car_driving_recycler_view)
    RecyclerView rv;

    private DrivingAdapter drivingAdapter;
    private List<DrivingBean> list;
    private final String[] permissions = {Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION};

    @Override
    protected View getContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_driving, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.home_toolbar_color));
        StatusBarUtil.setStatusBar(this, true, R.color.home_toolbar_color);
        toolbarTv.setTextColor(ContextCompat.getColor(mContext, R.color.home_toolbar_title_color));
        toolbarTv.setText(R.string.car_home_driving_detail);

        init();
        checkPermission();
    }

    String[] start = {"?????????????????????", "????????????", "????????????", "??????"};
    String[] end = {"??????", "????????????", "????????????", "????????????"};

    private void init() {
        list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            DrivingBean drivingBean = new DrivingBean();
            drivingBean.setDistance(1 * (i + 4));
            drivingBean.setJourney(17 * (i + 4));
            drivingBean.setStartTime("??????");
            drivingBean.setPrice(BigDecimal.valueOf(3.5 * drivingBean.getJourney()));
            drivingBean.setEndPoint(start[i]);
            drivingBean.setStartPoint(end[i]);
            list.add(drivingBean);
        }
        drivingAdapter = new DrivingAdapter(R.layout.recycler_item_home_car_driving, list);
        rv.setAdapter(drivingAdapter);
        drivingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showToastS("????????????????????????,????????????...");
            }
        });
//        FloatingMagnetView mFloatingView = (FloatingMagnetView) LayoutInflater.from(mContext).inflate(R.layout.layout_floating, null);
//        FloatingView.get().customView(mFloatingView);
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.gravity = Gravity.RIGHT | Gravity.CENTER;
//        FloatingView.get().layoutParams(layoutParams);
//        FloatingView.get().add();
//        FloatingView.get().listener(new MagnetViewListener() {
//            @Override
//            public void onRemove(FloatingMagnetView magnetView) {
//
//            }
//
//            @Override
//            public void onClick(FloatingMagnetView magnetView) {
//                Router.getInstance().build(HomeRouterPath.CAR_ROUTER_WEB_VIEW)
//                        .withString(H5Url.URL_PARAM_KEY,H5Url.ADD_CAR_DETECTION)
//                        .start();
//            }
//        });
    }

    private void checkPermission() {
        boolean isGranted = XXPermissions.isGrantedPermission(mContext, permissions);
        if (isGranted) {
            LocationUtils.getInstance().upLoadLocation(mContext);
        } else {
            XXPermissions.with(this)
                    .permission(permissions)
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            if (all) {
                                LocationUtils.getInstance().upLoadLocation(mContext);
                            } else {
                                ToastUtil.showToastS("?????????????????????????????????????????????????????????");
                            }
                        }

                        @Override
                        public void onDenied(List<String> permissions, boolean never) {
                            if (never) {
                                ToastUtil.showToastS("????????????????????????????????????????????????");
                                // ??????????????????????????????????????????????????????????????????
                            } else {
                                ToastUtil.showToastS("????????????????????????");
                            }

                        }
                    });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        FloatingView.get().attach(mActivity);
    }

    @Override
    public void onStop() {
        super.onStop();
//        FloatingView.get().detach(mActivity);
    }

    @Override
    protected void onVisibilityChanged(boolean visible) {
        super.onVisibilityChanged(visible);
//        if (visible) {
//            FloatingView.get().getView().setVisibility(View.VISIBLE);
////            FloatingMagnetView mFloatingView = (FloatingMagnetView) LayoutInflater.from(mContext).inflate(R.layout.layout_floating, null);
////            FloatingView.get().customView(mFloatingView);
////            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
////            layoutParams.gravity = Gravity.RIGHT | Gravity.CENTER;
////            FloatingView.get().layoutParams(layoutParams);
//        } else {
//            FloatingView.get().getView().setVisibility(View.GONE);
////            FloatingView.get().remove();
//        }
    }
}
