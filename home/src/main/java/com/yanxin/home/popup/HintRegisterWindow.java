package com.yanxin.home.popup;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.car.baselib.BaseLibCore;
import com.car.baselib.cache.SpCache;
import com.car.baselib.popup.BasePopupWindow;
import com.car.baselib.router.Router;
import com.car.baselib.ui.activity.BaseActivity;
import com.yanxin.common.login.LoginRouterPath;
import com.yanxin.home.R;


/**
 * @author zhouz
 * @date 2021/2/2
 */
public class HintRegisterWindow extends BasePopupWindow {

    private String msg;
    private BaseActivity activity;
    private PopupWindow.OnDismissListener onDismissListener;

    private TextView windowHint;
    private TextView windowCancel;
    private TextView windowRegister;

    public HintRegisterWindow(Context context,Builder builder) {
        super(context);
        msg = builder.msg;
        activity = (BaseActivity) context;
        onDismissListener = builder.onDismissListener;

        createWindow(R.layout.window_hint_register,builder.width,builder.height);
    }

    @Override
    protected void onViewCreated(View container) {
        windowHint = container.findViewById(R.id.window_hint);
        windowCancel = container.findViewById(R.id.window_hint_cancel);
        windowRegister = container.findViewById(R.id.window_hint_register);

        windowHint.setText(msg);

        windowCancel.setOnClickListener(view -> dismiss());

        windowRegister.setOnClickListener(view ->{
            dismiss();
            SpCache.get().clear();
            BaseLibCore.getInstance().getActivityStack().finishAll();
            Router.getInstance().build(LoginRouterPath.CAR_ROUTER_LOGIN).start();
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
        private int width;
        private int height;
        private String msg;


        public Builder setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
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

        public Builder setMessage(String msg) {
            this.msg = msg;
            return this;
        }


        public HintRegisterWindow build(Context context) {
            return new HintRegisterWindow(context, this);
        }
    }

}
