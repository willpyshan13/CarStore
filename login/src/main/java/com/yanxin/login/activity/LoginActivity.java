package com.yanxin.login.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.car.baselib.BaseLibCore;
import com.car.baselib.cache.SpCache;
import com.car.baselib.router.Router;
import com.car.baselib.ui.activity.BaseMVPActivity;
import com.car.baselib.utils.RxClickUtils;
import com.car.baselib.utils.StatusBarUtil;
import com.car.baselib.utils.ToastUtil;
import com.yanxin.login.R;
import com.yanxin.login.R2;
import com.yanxin.login.mvp.contract.LoginContract;
import com.yanxin.login.mvp.presenter.LoginPresenter;
import com.yanxin.common.beans.res.UserLoginRes;
import com.yanxin.common.constants.Config;
import com.yanxin.common.constants.H5Url;
import com.yanxin.common.home.HomeRouterPath;
import com.yanxin.common.login.LoginRouterPath;

import butterknife.BindView;


/**
 * @author zhouz
 * @date 2021/1/10
 */
@Route(path = LoginRouterPath.CAR_ROUTER_LOGIN)
public class LoginActivity extends BaseMVPActivity<LoginPresenter> implements RxClickUtils.OnViewClickListener, LoginContract.View {

    @BindView(R2.id.login_mobile)
    EditText etMobile;
    @BindView(R2.id.login_verification_code_et)
    EditText etCode;
    @BindView(R2.id.login_get_verification_code)
    TextView tvGetCode;
    @BindView(R2.id.login_login)
    TextView tvLogin;
    @BindView(R2.id.login_check_box)
    CheckBox checkBox;
    @BindView(R2.id.login_serve)
    TextView tvServe;
    @BindView(R2.id.login_intimacy)
    TextView tvIntimacy;
    @BindView(R2.id.login_copyright_notice)
    TextView tvCopyright;
    @BindView(R2.id.home_car_technician_register)
    TextView technicianRegister;
    @BindView(R2.id.home_car_store_register)
    TextView storeRegister;

    private String mobile;
    private boolean isCode;

    @Autowired(name = Config.WEB_VIEW_BACK)
    boolean isWebViewBack;

    @Override
    protected LoginPresenter setPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setStatusBar(this,true);
        init();
    }

    private void init() {
        RxClickUtils.setRxClickListener(this,tvGetCode,tvLogin,tvServe,tvIntimacy
                ,technicianRegister,storeRegister,tvCopyright);
        checkBox.setChecked(SpCache.get().getBoolean(Config.SP_CHECK_BOX_VALUE_KEY,false));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SpCache.get().putBoolean(Config.SP_CHECK_BOX_VALUE_KEY,isChecked);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            BaseLibCore.getInstance().getActivityStack().finishAll();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRxViewClick(View view) {
        int id = view.getId();
        if (id == R.id.login_get_verification_code) {
            mobile = etMobile.getText().toString();
            boolean isValid = checkMobile(mobile);
            if (isValid) {
                presenter.getVerificationCode(mobile);
                etCode.requestFocus();
            } else {
                ToastUtil.showToastL(R.string.login_input_mobile_hint);
            }
        } else if (id == R.id.login_login) {
            if (!checkBox.isChecked()) {
                ToastUtil.showToastL(R.string.login_agree_agreement_hint);
                return;
            }

            if (TextUtils.isEmpty(etMobile.getText().toString())){
                ToastUtil.showToastL(R.string.login_input_mobile_hint);
                return;
            }
            if (!isCode) {
                ToastUtil.showToastL(R.string.login_get_code_hint);
                return;
            }
            String code = etCode.getText().toString();
            if (TextUtils.isEmpty(code)){
                ToastUtil.showToastL(R.string.login_input_verification_code_ed);
                return;
            }
            presenter.login(mobile,code);
        } else if (id == R.id.login_serve) {
            //服务协议
            Router.getInstance().build(LoginRouterPath.CAR_ROUTER_AGREEMENT)
                    .withInt(Config.ROUTE_PRIVACY_POLICY_KEY,Config.AGREEMENT_SERVE)
                    .start();
        } else if (id == R.id.login_intimacy) {
            //隐私政策
            Router.getInstance().build(LoginRouterPath.CAR_ROUTER_AGREEMENT)
                    .withInt(Config.ROUTE_PRIVACY_POLICY_KEY,Config.AGREEMENT_PRIVACY)
                    .start();
        } else if (id == R.id.login_copyright_notice) {
            //版权声明
            Router.getInstance().build(LoginRouterPath.CAR_ROUTER_AGREEMENT)
                    .withInt(Config.ROUTE_PRIVACY_POLICY_KEY,Config.AGREEMENT_COPYRIGHT)
                    .start();
        } else if (id == R.id.home_car_technician_register) {
            if (!checkBox.isChecked()) {
                ToastUtil.showToastL(R.string.login_agree_agreement_hint);
                return;
            }
            //技师注册
            Router.getInstance().build(HomeRouterPath.CAR_ROUTER_WEB_VIEW)
                    .withString(H5Url.URL_PARAM_KEY,H5Url.TECHNICIAN_REGISTER)
                    .start();
        } else if (id == R.id.home_car_store_register) {
            if (!checkBox.isChecked()) {
                ToastUtil.showToastL(R.string.login_agree_agreement_hint);
                return;
            }
            //店铺注册
            Router.getInstance().build(HomeRouterPath.CAR_ROUTER_WEB_VIEW)
                    .withString(H5Url.URL_PARAM_KEY,H5Url.STORE_REGISTER)
                    .start();
        }
    }

    private boolean checkMobile(String mobile) {

        return mobile.matches(Config.TEL_REG);
    }

    @Override
    public void onCodeSuccess(String code) {
        isCode = true;
    }

    @Override
    public void onLoginSuccess(UserLoginRes userLoginRes) {
        if (!isWebViewBack) {
            // 不是webview 401 返回并且审核状态为1(审核通过)就进入到主页面
            if (userLoginRes.getCheckSts() == Config.CHECK_APPROVE) {
                Router.getInstance().build(HomeRouterPath.CAR_ROUTER_HOME).start();
                finish();
            } else {
                String url;
                if (Config.USER_TYPE_TECHNICIAN.equals(userLoginRes.getUserType())) {
                    url = H5Url.TECHNICIAN_INFO;
                } else {
                    url = H5Url.STORE_INFO;
                }
                Router.getInstance().build(HomeRouterPath.CAR_ROUTER_WEB_VIEW)
                        .withString(H5Url.URL_PARAM_KEY,url)
                        .withBoolean(H5Url.LOGIN_PARAM_KEY,true)
                        .start();
            }
        } else {
            finish();
        }
    }

    @Override
    public void onCodeTvStr(String str) {
        tvGetCode.setTextColor(ContextCompat.getColor(this,R.color.login_code_color));
        tvGetCode.setText(str);
    }

    @Override
    public void onComplete() {
        tvGetCode.setTextColor(ContextCompat.getColor(this,R.color.login_code_color_def));
        tvGetCode.setText(R.string.login_get_verification_code);
    }

    @Override
    public void onFailed(int code, String message) {
        ToastUtil.showToastS(message);
    }

}
