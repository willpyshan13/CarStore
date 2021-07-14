package com.yanxin.home.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.car.baselib.BaseLibCore;
import com.car.baselib.cache.SpCache;
import com.car.baselib.router.Router;
import com.car.baselib.ui.fragment.BaseMVPFragment;
import com.car.baselib.utils.GlideUtils;
import com.car.baselib.utils.RxClickUtils;
import com.car.baselib.utils.ToastUtil;
import com.car.baselib.utils.WxApiUtils;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;
import com.yanxin.common.beans.res.UserLoginRes;
import com.yanxin.common.constants.Config;
import com.yanxin.common.constants.H5Url;
import com.yanxin.common.constants.H5UrlConstants;
import com.yanxin.common.home.HomeRouterPath;
import com.yanxin.common.login.LoginRouterPath;
import com.yanxin.home.R;
import com.yanxin.home.R2;
import com.yanxin.home.beans.res.AccountInfoRes;
import com.yanxin.home.beans.res.DictBean;
import com.yanxin.home.constants.Constants;
import com.yanxin.home.mvp.contract.MyContract;
import com.yanxin.home.mvp.presenter.MyPresenter;
import com.yanxin.home.popup.HintRegisterWindow;
import com.yanxin.home.utils.GlideImageLoader;

import java.util.List;

import butterknife.BindView;

/**
 * @author zhouz
 * @date 2021/1/16
 */
public class MyFragment extends BaseMVPFragment<MyPresenter> implements MyContract.View, RxClickUtils.OnViewClickListener {

    private final String[] permissions = {Permission.CAMERA, Permission.MANAGE_EXTERNAL_STORAGE};

    @BindView(R2.id.home_car_my_wallet_rl)
    RelativeLayout walletRl;
    @BindView(R2.id.home_car_my_order_rl)
    RelativeLayout orderRl;
    @BindView(R2.id.home_car_my_account_rl)
    RelativeLayout accountRl;
    @BindView(R2.id.home_car_my_rel_rl)
    RelativeLayout relRl;
    @BindView(R2.id.home_car_my_quiz_rl)
    RelativeLayout quizRl;
    @BindView(R2.id.home_car_my_dtc_rl)
    RelativeLayout dtcRl;
    @BindView(R2.id.home_car_my_case_rl)
    RelativeLayout caseRl;
    @BindView(R2.id.home_car_my_course_rl)
    RelativeLayout courseRl;
    @BindView(R2.id.parent_view)
    NestedScrollView mParentView;
    @BindView(R2.id.user_photo)
    ImageView userPhoto;
    @BindView(R2.id.home_car_my_rel_img)
    ImageView imgRel;
    @BindView(R2.id.home_car_my_setting)
    ImageView imgSet;
    @BindView(R2.id.user_mobile)
    TextView userMobile;
    @BindView(R2.id.home_car_my_switch)
    TextView carSwitch;
    @BindView(R2.id.home_car_my_rel)
    TextView relTv;
    @BindView(R2.id.home_car_my_withdraw)
    TextView myWithdraw;
    @BindView(R2.id.home_car_my_order_num)
    TextView myOrder;
    @BindView(R2.id.home_car_my_exit)
    TextView exit;
    @BindView(R2.id.home_car_my_shape_rel)
    RelativeLayout shapeRel;
    @BindView(R2.id.home_car_my_service)
    TextView myService;


    private PopupWindow mSelectPop;
    private View mSelectPopLayout;
    private IHandlerCallBack iHandlerCallBack;


    private HintRegisterWindow hintRegisterWindow;

    @Override
    protected MyPresenter setPresenter() {
        return new MyPresenter(this);
    }

    @Override
    protected View getContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_my, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    protected void onVisibilityChanged(boolean visible) {
        super.onVisibilityChanged(visible);
        if (visible) {
            presenter.getAccountInfo();
        }
    }

    private void init() {
        RxClickUtils.setRxClickListener(this, walletRl, orderRl, accountRl, relRl, carSwitch
                , imgSet, exit, shapeRel, myService, userPhoto, quizRl, dtcRl, caseRl, courseRl);
        userMobile.setText(SpCache.get().getString(Config.MOBILE_SP_KEY));

        setInfo(Constants.getUserType());
    }

    @Override
    public void onRxViewClick(View view) {
        int id = view.getId();
        if (id == R.id.user_photo) {
            checkPermission();
        } else if (id == R.id.home_car_my_switch) {
            //技师店铺身份切换
            presenter.accountSwitchRole();
        } else if (id == R.id.home_car_my_service) {
            //我的客服
            presenter.queryDictByType(Config.CUSTOMER_SERVICE_PHONE);
        } else if (id == R.id.home_car_my_shape_rel) {
            //分享
//            ToastUtil.showToastS("当前功能暂未开放,敬请期待...");
            mSelectPopLayout =
                    LayoutInflater.from(mContext).inflate(R.layout.layout_select_share, null);
            mSelectPop = new PopupWindow(mSelectPopLayout,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mSelectPop.setFocusable(true);
            mSelectPop.setOutsideTouchable(false);
            //给PopupWindow的window窗口设置软键盘展示属性
            mSelectPop.setBackgroundDrawable(new ColorDrawable());
            mSelectPop.setAnimationStyle(R.style.PopWindowAnimStyle);
            mSelectPop.showAtLocation(mParentView, Gravity.BOTTOM, 0, mSelectPopLayout.getHeight());
            TextView shareCancel = mSelectPopLayout.findViewById(R.id.share_cancel);
            LinearLayout shareWx = mSelectPopLayout.findViewById(R.id.share_wechat_wx);
            LinearLayout shareWxFriend = mSelectPopLayout.findViewById(R.id.share_wechat);
            shareCancel.setOnClickListener(view1 -> mSelectPop.dismiss());
            shareWx.setOnClickListener(view13 -> {
                //分享到朋友圈
                mSelectPop.dismiss();
                // send oauth request
                //初始化一个 WXTextObject 对象，填写分享的文本内容
                WXWebpageObject webObj = new WXWebpageObject();

                webObj.webpageUrl = H5UrlConstants.getH5Url() + "/sharePage";
                //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = webObj;
                msg.title = "嘟一家服务";
//                    msg.thumbData = Utils.bmpToByteArray(
//                            Bitmap.createScaledBitmap(
//                                    Utils.GetLocalOrNetBitmap(imgUrl) ?: Utils.DrawableToBitmap(ContextCompat.getDrawable(baseContext, R.mipmap.logo_icon)!!)
//                    , 150, 150, true
//                ), true
//            )
                msg.description = "分享各地实现上亿车主  信任与成就的技师/店铺之家";
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = "webpage";
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                //调用api接口，发送数据到微信
                WxApiUtils.getmApi().sendReq(req);
            });
            shareWxFriend.setOnClickListener(view12 -> {
                //分享给朋友
                mSelectPop.dismiss();
                // send oauth request
                //初始化一个 WXTextObject 对象，填写分享的文本内容
                WXWebpageObject webObj = new WXWebpageObject();
                webObj.webpageUrl = H5UrlConstants.getH5Url() + "/sharePage";
                //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = webObj;
                msg.title = "嘟一家服务";
//                    msg.thumbData = Utils.bmpToByteArray(
//                            Bitmap.createScaledBitmap(
//                                    Utils.GetLocalOrNetBitmap(imgUrl) ?: Utils.DrawableToBitmap(ContextCompat.getDrawable(baseContext, R.mipmap.logo_icon)!!)
//                    , 150, 150, true
//                ), true
//            )
                msg.description = "分享各地实现上亿车主  信任与成就的技师/店铺之家";
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = "webpage";
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                //调用api接口，发送数据到微信
                WxApiUtils.getmApi().sendReq(req);
            });

        } else if (id == R.id.home_car_my_exit) {
            //退出登录
            SpCache.get().remove(Config.TOKEN_SP_KEY);
            BaseLibCore.getInstance().getActivityStack().finishAll();
            Router.getInstance().build(LoginRouterPath.CAR_ROUTER_LOGIN)
                    .start();
        } else if (id == R.id.home_car_my_setting) {
            String url;
            if (Config.USER_TYPE_STORE.equals(Constants.getUserType())) {
                //店铺详情
                url = H5Url.STORE_INFO;
            } else {
                //技师详情
                url = H5Url.TECHNICIAN_INFO;
            }
            Router.getInstance().build(HomeRouterPath.CAR_ROUTER_WEB_VIEW)
                    .withString(H5Url.URL_PARAM_KEY, url)
                    .start();
        } else {
            String url = "";
            if (id == R.id.home_car_my_wallet_rl) {
                //钱包
                url = H5Url.WALLET;
            } else if (id == R.id.home_car_my_order_rl) {
                //订单
                url = H5Url.MY_ORDER;
            } else if (id == R.id.home_car_my_account_rl) {
                //账户
                if (Config.USER_TYPE_STORE.equals(Constants.getUserType())) {
                    //店铺详情
                    url = H5Url.ACCOUNT_INFO;
                } else {
                    //技师详情
                    url = H5Url.TECHNICIAN_ACCOUNT;
                }

            } else if (id == R.id.home_car_my_rel_rl) {
                if (Config.USER_TYPE_STORE.equals(Constants.getUserType())) {
                    //技师管理 店铺端
                    url = H5Url.TECHNICIAN_ADMIN;
                } else {
                    //关联店铺 技师端
                    url = H5Url.RELEVANCY_STORE;
                }
            } else if (id == R.id.home_car_my_quiz_rl) {
                //我的问题 技师端
                url = H5Url.MINE_ASK;
            } else if (id == R.id.home_car_my_dtc_rl) {
                url = H5Url.DTC_LIST;
            } else if (id == R.id.home_car_my_case_rl) {
                //我的案例 技师端
                url = H5Url.MINE_CASE;
            } else if (id == R.id.home_car_my_course_rl) {
                url = H5Url.MY_MYJIAOCHENG;
            }
            if (TextUtils.isEmpty(url)) {
                ToastUtil.showToastS("当前功能暂未上线,敬请期待");
            } else {
                Router.getInstance().build(HomeRouterPath.CAR_ROUTER_WEB_VIEW)
                        .withString(H5Url.URL_PARAM_KEY, url)
                        .start();
            }
        }
    }

    @Override
    public void onSuccessDict(List<DictBean> dictBeanList) {
        if (dictBeanList != null && !dictBeanList.isEmpty()) {
            DictBean dictBean = dictBeanList.get(0);
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + dictBean.getLableValue()));
            startActivity(intent);
        } else {
            ToastUtil.showToastS("当前功能暂未开放,敬请期待...");
        }
    }

    private void checkPermission() {
        boolean isGranted = XXPermissions.isGrantedPermission(mContext, permissions);

        if (isGranted) {
            openCamera();
        } else {
            XXPermissions.with(this)
                    .permission(permissions)
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            if (all) {
                                openCamera();
                            } else {
                                ToastUtil.showToastS("获取部分权限成功，但部分权限未正常授予");
                            }
                        }

                        @Override
                        public void onDenied(List<String> permissions, boolean never) {
                            if (never) {
                                ToastUtil.showToastS("被永久拒绝授权，请手动授予相机、存储权限");
                                // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            } else {
                                ToastUtil.showToastS("获取相机、存储权限失败");
                            }
                        }
                    });
        }
    }

    private void openCamera() {
        if (iHandlerCallBack == null) {
            initGallery();
        }
        GalleryConfig galleryConfig = initConfig();
        GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(getActivity());
    }

    private void initGallery() {
        iHandlerCallBack = new IHandlerCallBack() {
            @Override
            public void onStart() {
                Log.i(TAG, "onStart: 开启");
            }

            @Override
            public void onSuccess(List<String> photoList) {
                presenter.uploadImage(photoList.get(0));
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "onCancel: 取消");
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "onFinish: 结束");
            }

            @Override
            public void onError() {
                Log.i(TAG, "onError: 出错");
            }
        };

    }

    private GalleryConfig initConfig() {

        return new GalleryConfig.Builder()
                // ImageLoader 加载框架（必填）
                .imageLoader(new GlideImageLoader())
                // 监听接口（必填）
                .iHandlerCallBack(iHandlerCallBack)
                // provider(必填)
                .provider("com.yanxin.store.fileprovider")
                // 记录已选的图片
                //.pathList(path)
                // 是否多选   默认：false
                .multiSelect(false)
                // 配置是否多选的同时 配置多选数量   默认：false ， 9
//                .multiSelect(false, 9)
                // 配置多选时 的多选数量。    默认：9
//                .maxSize(9)
                // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                //.crop(true)
                // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .crop(true, 1, 1, 500, 500)
                // 是否现实相机按钮  默认：false
                .isShowCamera(true)
                // 图片存放路径
                .filePath(Config.PATH)
                .build();
    }

    private void setInfo(String userType) {

        if (Config.USER_TYPE_STORE.equals(userType)) {
            carSwitch.setText(R.string.home_car_my_store_to_technician);
            imgRel.setImageResource(R.drawable.home_car_my_admin);
            relTv.setText(R.string.home_car_my_technician);
        } else {
            carSwitch.setText(R.string.home_car_my_technician_to_store);
            imgRel.setImageResource(R.drawable.home_car_my_rel_store);
            relTv.setText(R.string.home_car_my_store);
        }
    }


    @Override
    public void onSuccessSwitch(UserLoginRes userLoginRes) {
        setInfo(userLoginRes.getUserType());

        if (Config.CHECK_APPROVE == userLoginRes.getCheckSts()) {
            BaseLibCore.getInstance().getActivityStack().finishAll();
            Router.getInstance().build(HomeRouterPath.CAR_ROUTER_HOME).start();
        } else {
            String url;
            if (Config.USER_TYPE_TECHNICIAN.equals(userLoginRes.getUserType())) {
                url = H5Url.TECHNICIAN_INFO;
            } else {
                url = H5Url.STORE_INFO;
            }
            BaseLibCore.getInstance().getActivityStack().finishAll();
            Router.getInstance().build(HomeRouterPath.CAR_ROUTER_WEB_VIEW)
                    .withString(H5Url.URL_PARAM_KEY, url)
                    .withBoolean(H5Url.LOGIN_PARAM_KEY, true)
                    .start();
        }

    }

    @Override
    public void updateUserPhotoImg(String userPhotoImgUrl) {
        GlideUtils.loadUrlCircleImage(userPhotoImgUrl
                , R.drawable.car_default, userPhoto);
    }

    @Override
    public void onSuccessAccountInfo(AccountInfoRes accountInfoRes) {
        myWithdraw.setText(mContext.getString(R.string.home_car_my_withdraw, accountInfoRes.getAviWithdrawAmt()));
        myOrder.setText(mContext.getString(R.string.home_car_my_order_total, accountInfoRes.getOrderNum()));
    }

    @Override
    public void onFailed(int code, String message) {
        if (Config.NOT_REGISTER_STORE == code || Config.NOT_REGISTER_TERMINAL == code) {
            showHintRegisterWindow(code);
        } else {
            ToastUtil.showToastS(message);
        }
    }

    private void showHintRegisterWindow(int code) {
        String msg = Config.NOT_REGISTER_STORE == code ? getString(R.string.home_car_my_window_hint_store)
                : getString(R.string.home_car_my_window_hint_technician);
        hintRegisterWindow = HintRegisterWindow.newBuilder()
                .setMessage(msg)
                .setHeight((int) mContext.getResources().getDimension(R.dimen.popup_window_hint_register_height))
                .setWidth((int) mContext.getResources().getDimension(R.dimen.popup_window_hint_register_width))
                .setOnDismissListener(() -> {
                    if (hintRegisterWindow != null) {
                        hintRegisterWindow.dismiss();
                        hintRegisterWindow = null;
                    }
                })
                .build(mContext);
        if (getActivity() != null) {
            Window window = getActivity().getWindow();
            hintRegisterWindow.showAtLocation(window.getDecorView(), Gravity.CENTER, 0, 0);
        }

    }
}
