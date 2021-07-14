package com.yanxin.home.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.car.baselib.BaseLibCore;
import com.car.baselib.cache.SpCache;
import com.car.baselib.net.http.HttpParams;
import com.car.baselib.router.Router;
import com.car.baselib.ui.activity.BaseActivity;
import com.car.baselib.utils.LogUtils;
import com.car.baselib.utils.StatusBarUtil;
import com.car.baselib.utils.ToastUtil;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.yanxin.common.constants.Config;
import com.yanxin.common.constants.H5Url;
import com.yanxin.common.home.HomeRouterPath;
import com.yanxin.common.login.LoginRouterPath;
import com.yanxin.common.utils.AndroidBug5497Workaround;
import com.yanxin.home.R;
import com.yanxin.home.R2;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;

/**
 * @author zhouz
 * @date 2021/1/10
 */
@Route(path = HomeRouterPath.CAR_ROUTER_WEB_VIEW)
public class WebViewActivity extends BaseActivity {

    @BindView(R2.id.car_web_view)
    WebView carWebView;

    private final String JS_ANDROID = "js_android";
    protected WebSettings webSettings;

    @Autowired(name = H5Url.URL_PARAM_KEY)
    String url;
    @Autowired(name = H5Url.PARAM_PARAM_KEY)
    String param;
    @Autowired(name = H5Url.LOGIN_PARAM_KEY)
    boolean isLoginToCenter;

    boolean isTransferToken;
    boolean isCheck;
    private final String[] permissions = {Permission.CAMERA, Permission.MANAGE_EXTERNAL_STORAGE};
    private android.webkit.ValueCallback<Uri[]> mUploadCallbackAboveL;
    private int REQUEST_CODE = 1234;
    private Uri imageUri;
    private boolean isPay;
    private boolean isAiPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_web_view);
        StatusBarUtil.setStatusBar(this, true, true);
        initWebView();
        AndroidBug5497Workaround.assistActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isTransferToken) {
            isTransferToken = false;

            carWebView.evaluateJavascript("setToken(" + HttpParams.getToken() + ")", value ->
                    LogUtils.d("value: " + value));
        }
    }

    /**
     * 初始化webview及相关设置
     */
    protected void initWebView() {
        carWebView.loadUrl(url);
        carWebView.addJavascriptInterface(new JsInterface(), JS_ANDROID);

        carWebView.setWebChromeClient(webChromeClient);
        carWebView.setWebViewClient(webViewClient);

        webSettings = carWebView.getSettings();
        //允许使用js
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
        //允许js弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);

        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        // 打开本地缓存提供JS调用,至关重要
        webSettings.setDomStorageEnabled(true);
        // 实现5M缓存
        webSettings.setAppCacheMaxSize(1024 * 1024 * 5);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setUseWideViewPort(true);
        String appCachePath = getApplication().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setDatabaseEnabled(true);


        //是否支持屏幕缩放
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carWebView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        carWebView.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (carWebView.canGoBack() && !isFinish) {
                carWebView.goBack();
                return true;
            } else {
                checkSts();
                finish();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * WebViewClient主要帮助WebView处理各种通知、请求事件
     */
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(final WebView view, String url) {//页面加载完成

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载

        }


        @SuppressLint("NewApi")
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            LogUtils.d("url:" + request.getUrl());

            return super.shouldInterceptRequest(view, request);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtils.e("url: " + url);
            String tag = "tel:";
            if (url != null && url.contains(tag)) {
                String mobile = url.substring(url.lastIndexOf(":") + 1);
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobile));
                startActivity(intent);
                return true;
            }

            if (!isPay) {
                try {
                    if (url.startsWith("weixin://")) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                } catch (Exception e) {
                    return false;
                }
            }

            try {
                if (url.startsWith("alipays://") && !isAiPay) {
                    isAiPay = true;
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
            } catch (Exception e) {
                return false;
            }

            if (url.contains("https://wx.tenpay.com")) {
                Map<String, String> extraHeaders = new HashMap<>();
                String referer = Uri.parse(url).getQueryParameter("redirect_url");
                extraHeaders.put("Referer", referer);
                LogUtils.e("referer:" + referer);
                view.loadUrl(url, extraHeaders);
                return true;
            }
            isPay = true;
            if (url.startsWith("weixin://")) {
                if (carWebView.canGoBack()) {
                    carWebView.goBackOrForward(-2);
                    isPay = false;
                }
            } else if (url.startsWith("alipays://")) {
                if (carWebView.canGoBack()) {
                    carWebView.goBackOrForward(-2);
                }
            } else {
                view.loadUrl(url);
            }
            return true;
        }
    };

    /**
     * WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
     */
    private WebChromeClient webChromeClient = new WebChromeClient() {
        /** 不支持js的alert弹窗，需要自己监听然后通过dialog弹窗 */
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
            AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
            localBuilder.setMessage(message).setPositiveButton("确定", null);
            localBuilder.setCancelable(false);
            localBuilder.create().show();

            /** 注意:
             必须要这一句代码:result.confirm()表示:
             处理结果为确定状态同时唤醒WebCore线程
             否则不能继续点击按钮
             */
            result.confirm();
            return true;
        }

        /** 获取网页标题 */
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        /** 加载进度回调 */
        @Override
        public void onProgressChanged(WebView view, int newProgress) {

        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            mUploadCallbackAboveL = filePathCallback;
            checkPermission();
            return true;
        }
    };

    /**
     * 清楚webview缓存
     */
    public void clearWebViewCache() {

        File file = this.getApplicationContext().getCacheDir().getAbsoluteFile();
        deleteFile(file);
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {

        LogUtils.d("delete file path=" + file.getAbsolutePath());

        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            LogUtils.e("delete file no exists " + file.getAbsolutePath());
        }
    }

    @Override
    protected void onDestroy() {
//        clearWebViewCache();   // 清除webview缓存
        if (carWebView != null) {

            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = carWebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(carWebView);
            }

            carWebView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webSettings.setJavaScriptEnabled(false);
            carWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            carWebView.clearHistory();
            carWebView.clearView();
            carWebView.removeAllViews();
            carWebView.removeJavascriptInterface(JS_ANDROID);
            carWebView.destroy();
            carWebView = null;
        }

        super.onDestroy();
    }

    private void checkPermission() {
        boolean isGranted = XXPermissions.isGrantedPermission(this, permissions);
        if (isGranted) {
            takePhoto();
        } else {
            XXPermissions.with(this)
                    .permission(permissions)
                    .request(new OnPermissionCallback() {

                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            if (all) {
                                takePhoto();
                            } else {
                                if (mUploadCallbackAboveL != null) {
                                    mUploadCallbackAboveL.onReceiveValue(null);
                                    mUploadCallbackAboveL = null;
                                }
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
                            if (mUploadCallbackAboveL != null) {
                                mUploadCallbackAboveL.onReceiveValue(null);
                                mUploadCallbackAboveL = null;
                            }
                        }
                    });
        }

    }

    /**
     * 调用相机
     */
    private void takePhoto() {
        // 指定拍照存储位置的方式调起相机
        String filePath = Environment.getExternalStorageDirectory() + File.separator
                + Environment.DIRECTORY_PICTURES + File.separator;
        String fileName = "IMG_" + DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        imageUri = Uri.fromFile(new File(filePath + fileName));
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        Intent videoIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        videoIntent.addCategory(Intent.CATEGORY_OPENABLE);
        videoIntent.setType("*/*");
        String[] mimetypes = {"image/*", "video/*","application/pdf"};
        videoIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        Intent photo = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent chooserIntent = Intent.createChooser(photo, "Image Chooser");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]{captureIntent, videoIntent});
        startActivityForResult(chooserIntent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            // 经过上边(1)、(2)两个赋值操作，此处即可根据其值是否为空来决定采用哪种处理方法
            if (mUploadCallbackAboveL != null) {
                chooseAbove(resultCode, data);
            } else {
                ToastUtil.showToastS("发生错误");
            }
        }
    }

    /**
     * Android API >= 21(Android 5.0) 版本的回调处理
     *
     * @param resultCode 选取文件或拍照的返回码
     * @param data       选取文件或拍照的返回结果
     */
    private void chooseAbove(int resultCode, Intent data) {
        if (RESULT_OK == resultCode) {
//            updatePhotos();

            if (data != null) {
                // 这里是针对从文件中选图片的处理
                Uri[] results;
                Uri uriData = data.getData();
                if (uriData != null) {
                    results = new Uri[]{uriData};
                    mUploadCallbackAboveL.onReceiveValue(results);
                } else {
                    mUploadCallbackAboveL.onReceiveValue(null);
                }
            } else {
                LogUtils.d("自定义结果：" + imageUri.toString());
                mUploadCallbackAboveL.onReceiveValue(new Uri[]{imageUri});
            }
        } else {
            mUploadCallbackAboveL.onReceiveValue(null);
        }
        mUploadCallbackAboveL = null;
    }

    private void updatePhotos() {
        // 该广播即使多发（即选取照片成功时也发送）也没有关系，只是唤醒系统刷新媒体文件
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(imageUri);
        sendBroadcast(intent);
    }

    private volatile boolean isFinish;

    private class JsInterface {

        @JavascriptInterface
        public String getToken() {
            String token = HttpParams.getToken();
            return token;
        }

        @JavascriptInterface
        public void anewLogin() {
//            isTransferToken = true;
//            SpCache.get().remove(Config.TOKEN_SP_KEY);
//            Router.getInstance().build(LoginRouterPath.CAR_ROUTER_LOGIN)
//                    .withBoolean(Config.WEB_VIEW_BACK, true)
//                    .start();
        }

        @JavascriptInterface
        public void exitLogin() {
//            SpCache.get().clear();
//            BaseLibCore.getInstance().getActivityStack().finishAll();
//            Router.getInstance().build(LoginRouterPath.CAR_ROUTER_LOGIN).start();
        }

        @JavascriptInterface
        public String getCaseParam() {
            return param;
        }

        @JavascriptInterface
        public String getConsultParam() {
            return param;
        }

        @JavascriptInterface
        public String getProductParam() {
            return param;
        }


        @JavascriptInterface
        public String getStoreParam() {
            LogUtils.d(param);
            return param;
        }

        @JavascriptInterface
        public String getParam() {
            LogUtils.e("param: " + param);
            return param;
        }

        @JavascriptInterface
        public String getTechnicianUuid() {
            return param;
        }

        @JavascriptInterface
        public String getAuditParam() {
            LogUtils.d(param);
            return param;
        }

        @JavascriptInterface
        public void setCheckSts() {
            //审核为通过状态时 js回调 isCheck 默认 false
            isCheck = true;
            SpCache.get().putInt(Config.USER_CHECK_STS_SP_KEY, Config.CHECK_APPROVE);
        }

        @JavascriptInterface
        public void finishPage() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (carWebView.canGoBack()) {
                        carWebView.goBack();
                    } else {
                        checkSts();
                        finish();
                    }
                }
            });
        }

        @JavascriptInterface
        public void finishAll() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (isLoginToCenter) {
                        Router.getInstance().build(LoginRouterPath.CAR_ROUTER_LOGIN).start();
                    } else {
                        finish();
                    }
                }
            });
        }

        @JavascriptInterface
        public void goBack() {
            isFinish = true;
        }

    }

    private void checkSts() {
        if (isLoginToCenter) {
            if (isCheck) {
                Router.getInstance().build(HomeRouterPath.CAR_ROUTER_HOME).start();
            } else {
                Router.getInstance().build(LoginRouterPath.CAR_ROUTER_LOGIN).start();
            }
        }
    }
}
