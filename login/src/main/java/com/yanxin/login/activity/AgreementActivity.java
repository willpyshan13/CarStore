package com.yanxin.login.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.car.baselib.ui.activity.BaseActivity;
import com.car.baselib.utils.StatusBarUtil;
import com.yanxin.login.R;
import com.yanxin.login.R2;
import com.yanxin.common.constants.Config;
import com.yanxin.common.login.LoginRouterPath;

import butterknife.BindView;

/**
 * @author zhouz
 * @date 2021/2/11
 */
@Route(path = LoginRouterPath.CAR_ROUTER_AGREEMENT)
public class AgreementActivity extends BaseActivity {

    @Autowired(name = Config.ROUTE_PRIVACY_POLICY_KEY)
    int agreementType;


    @BindView(R2.id.common_toolbar)
    Toolbar toolbar;
    @BindView(R2.id.common_toolbar_title_tv)
    TextView toolbarTv;
    @BindView(R2.id.login_privacy_policy_content)
    TextView content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        setContentView(R.layout.activity_agreement);
        StatusBarUtil.setStatusBar(this,true,true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(view ->{
            finish();
        });
        toolbarTv.setTextColor(ContextCompat.getColor(this,R.color.login_code_color_def));
        String title;
        String contentStr;
        if (Config.AGREEMENT_COPYRIGHT == agreementType) {
            title = getString(R.string.login_privacy_policy_title);
            contentStr = getString(R.string.login_privacy_policy_content);
        } else if (Config.AGREEMENT_PRIVACY == agreementType) {
            title = getString(R.string.login_intimacy_title);
            contentStr = getString(R.string.login_intimacy_content);
        } else {
            title = getString(R.string.login_agreement_title);
            contentStr = getString(R.string.login_agreement_content);
        }

        toolbarTv.setText(title);
        content.setText(contentStr);
    }
}
