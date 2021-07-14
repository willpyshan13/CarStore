package com.yanxin.home.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.car.baselib.router.Router;
import com.car.baselib.ui.fragment.BaseMVPFragment;
import com.car.baselib.utils.ToastUtil;
import com.google.gson.Gson;
import com.yanxin.common.constants.Config;
import com.yanxin.common.constants.H5Url;
import com.yanxin.common.home.HomeRouterPath;
import com.yanxin.home.R;
import com.yanxin.home.R2;
import com.yanxin.home.adapter.SortTrainAdapter;
import com.yanxin.home.adapter.SortTrainNewsAdapter;
import com.yanxin.home.beans.h5.CourseParentParam;
import com.yanxin.home.beans.req.PageReq;
import com.yanxin.home.beans.res.CourseParentInfoRes;
import com.yanxin.home.mvp.contract.CourseParentContract;
import com.yanxin.home.mvp.presenter.CourseParentPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author zhouz
 * @date 2021/2/9
 */
public class TrainFragment extends BaseMVPFragment<CourseParentPresenter> implements CourseParentContract.View {


    @BindView(R2.id.home_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R2.id.home_car_train_recycler_view)
    RecyclerView trainRv;
    @BindView(R2.id.home_car_train_recycler_news_view)
    RecyclerView trainNewsRv;
    @BindView(R2.id.common_empty_view)
    ConstraintLayout emptyView;

    private List<CourseParentInfoRes> list;
    private List<CourseParentInfoRes> newList;
    private SortTrainAdapter sortTrainAdapter;
    private SortTrainNewsAdapter sortTrainNewsAdapter;

    private int pageNum;


    /**
     * 是否有跳转activity  处理viewpager 嵌套 fragment不回掉 onHiddenChanged 问题
     */
    private boolean isSkip;

    @Override
    protected CourseParentPresenter setPresenter() {
        return new CourseParentPresenter(this);
    }

    @Override
    protected View getContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_train, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pageNum = Config.PAGE_NUM;
        initRecyclerView();
    }

    @Override
    protected void onVisibilityChanged(boolean visible) {
        super.onVisibilityChanged(visible);
        if (visible) {
            pageNum = Config.PAGE_NUM;
            queryCourseParentList(pageNum, Config.PAGE_SIZE);
            queryCourseParentNewsList(1, Config.PAGE_SIZE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isSkip) {
            isSkip = false;
            pageNum = Config.PAGE_NUM;
            queryCourseParentList(pageNum, Config.PAGE_SIZE);
            queryCourseParentNewsList(1, Config.PAGE_SIZE);
        }
    }

    private void initRecyclerView() {
        list = new ArrayList<>();
        newList = new ArrayList<>();

        sortTrainAdapter = new SortTrainAdapter(R.layout.recycler_item_home_car_train, list);
        sortTrainNewsAdapter = new SortTrainNewsAdapter(R.layout.recycler_item_home_car_train, newList);
        trainRv.setAdapter(sortTrainAdapter);
        trainNewsRv.setAdapter(sortTrainNewsAdapter);
        sortTrainAdapter.setOnItemClickListener((adapter, view, position) -> {
            //点击列表进入详情
            skipWebView(list.get(position), 0);
        });
        sortTrainNewsAdapter.setOnItemClickListener((adapter, view, position) -> {
            //点击列表进入详情
            skipWebView(newList.get(position), 1);
        });
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = Config.PAGE_NUM;
                queryCourseParentList(1, Config.PAGE_SIZE);
                queryCourseParentNewsList(1, Config.PAGE_SIZE);
            }
        });
        trainRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    if (lastVisibleItemPosition + 1 == sortTrainAdapter.getItemCount() && sortTrainAdapter.getItemCount() >= 20) {
                        queryCourseParentList(pageNum, Config.PAGE_SIZE);
                    }
                }
            }
        });
    }

    private void queryCourseParentList(int pageNum, int pageSize) {
        PageReq pageReq = new PageReq();
        pageReq.setPageNum(pageNum);
        pageReq.setPageSize(pageSize);
        presenter.queryCourseParentList(pageReq);
    }

    private void queryCourseParentNewsList(int pageNum, int pageSize) {
        PageReq pageReq = new PageReq();
        pageReq.setPageNum(pageNum);
        pageReq.setPageSize(pageSize);
        presenter.queryCourseParentNewsList(pageReq);
    }

    @Override
    public void onSuccessCourseParentList(List<CourseParentInfoRes> courseParentInfoRes) {
        if (pageNum == Config.PAGE_NUM) {
            list.clear();
//            showNewCourse(courseParentInfoRes);
            sortTrainAdapter.setNewData(list);
        }
        if (!courseParentInfoRes.isEmpty()) {
            pageNum++;
            list.addAll(courseParentInfoRes);
            sortTrainAdapter.setNewData(list);
        } else {
            if (pageNum == Config.PAGE_NUM) {
                sortTrainAdapter.setEmptyView(R.layout.common_empty_view, trainRv);
            }
        }
        swipeRefresh.setRefreshing(false);
    }

//    private void showNewCourse(List<CourseParentInfoRes> courseParentInfoRes) {
//        newList.clear();
//        for (int i = 0; i < courseParentInfoRes.size(); i++) {
//            if(courseParentInfoRes.get(i).isNewest()){
//                newList.add(courseParentInfoRes.remove(i));
//                i--;
//            }
//        }
//        if (newList.size() == 0) {
//            emptyView.setVisibility(View.VISIBLE);
//        } else {
//            emptyView.setVisibility(View.GONE);
//        }
//        sortTrainNewsAdapter.notifyDataSetChanged();
//        sortTrainAdapter.notifyDataSetChanged();
//    }

    @Override
    public void onSuccessCourseParentNewsList(List<CourseParentInfoRes> courseParentInfoRes) {
        newList.clear();
        newList.addAll(courseParentInfoRes);
        if (newList.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
        sortTrainNewsAdapter.notifyDataSetChanged();
    }

    private void skipWebView(CourseParentInfoRes courseParentInfoRes, int latestCourse) {
        isSkip = true;
        CourseParentParam courseParentParam = new CourseParentParam();
        courseParentParam.setUuid(courseParentInfoRes.getUuid());
        courseParentParam.setCourseName(courseParentInfoRes.getCourseTitle());
        courseParentParam.setLatestCourse(latestCourse);
        String json = new Gson().toJson(courseParentParam);
        Router.getInstance().build(HomeRouterPath.CAR_ROUTER_WEB_VIEW)
                .withString(H5Url.URL_PARAM_KEY, H5Url.MY_COURSE)
                .withString(H5Url.PARAM_PARAM_KEY, json)
                .start();
    }

    @Override
    public void onFailed(int code, String message) {
        ToastUtil.showToastS(message);
    }
}
