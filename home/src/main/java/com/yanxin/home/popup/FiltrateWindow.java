package com.yanxin.home.popup;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.RecyclerView;

import com.car.baselib.adapter.BaseQuickAdapter;
import com.car.baselib.popup.BasePopupWindow;
import com.car.baselib.ui.activity.BaseActivity;
import com.yanxin.home.R;
import com.yanxin.home.adapter.FiltrateAdapter;
import com.yanxin.home.beans.FiltrateBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouz
 * @date 2021/1/18
 */
public class FiltrateWindow extends BasePopupWindow {

    private PopupWindow.OnDismissListener onDismissListener;
    private List<FiltrateBean> list;
    private OnSelectFiltrateListener onSelectFiltrateListener;

    private RecyclerView rv;
    private FiltrateAdapter adapter;
    private BaseActivity activity;

    public FiltrateWindow(Context context, Builder builder) {
        super(context);
        activity = (BaseActivity) context;
        onDismissListener = builder.onDismissListener;
        list = builder.list;
        onSelectFiltrateListener = builder.onSelectFiltrateListener;

        createWindow(R.layout.popup_filtrate,builder.width,
                builder.height);
    }

    @Override
    protected void onViewCreated(View container) {
        rv = container.findViewById(R.id.popup_filtrate_rv);
        if (list == null) {
            list = new ArrayList<>();
        }
        adapter = new FiltrateAdapter(R.layout.recycler_item_popup_filtrate,list);
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!list.isEmpty() && list.size() > position ) {
                    FiltrateBean bean = list.get(position);
                    if (onSelectFiltrateListener != null) {
                        onSelectFiltrateListener.onSelectFiltrate(bean);
                    }
                }
                dismiss();
            }
        });
    }

    @Override
    protected void onPopupWindowCreated(PopupWindow popupWindow) {
        popupWindow.setOnDismissListener(() -> {
            if (onDismissListener != null) {
                onDismissListener.onDismiss();
            }
            darkenBackground(activity,1f);
        });
        darkenBackground(activity,0.4f);
    }

    /**
     * 改变背景颜色
     */
    private void darkenBackground(Activity activity, Float bgColor) {
        if (activity != null) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = bgColor;
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            activity.getWindow().setAttributes(lp);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder{

        private PopupWindow.OnDismissListener onDismissListener;
        private List<FiltrateBean> list;
        private int width;
        private int height;
        private OnSelectFiltrateListener onSelectFiltrateListener;


        public Builder setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnArithmeticList(List<FiltrateBean> list) {
            this.list = list;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setOnSelectFiltrateListener(OnSelectFiltrateListener onSelectFiltrateListener) {
            this.onSelectFiltrateListener = onSelectFiltrateListener;
            return this;
        }

        public FiltrateWindow build(Context context) {
            return new FiltrateWindow(context, this);
        }
    }

    public interface OnSelectFiltrateListener {
        /**
         * 选中item
         * @param filtrateBean
         */
        void onSelectFiltrate(FiltrateBean filtrateBean);
    }
}
