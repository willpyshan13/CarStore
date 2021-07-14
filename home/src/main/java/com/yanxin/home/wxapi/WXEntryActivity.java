package com.yanxin.home.wxapi;

import android.app.Activity;

import com.car.baselib.utils.ToastUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import static com.tencent.mm.opensdk.constants.ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX;
import static com.tencent.mm.opensdk.modelbase.BaseResp.ErrCode.ERR_OK;
import static com.tencent.mm.opensdk.modelbase.BaseResp.ErrCode.ERR_USER_CANCEL;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case ERR_OK:
                if (baseResp.getType() == COMMAND_SENDMESSAGE_TO_WX) {
                    ToastUtil.showToastL("分享成功");
                }
                break;

            case ERR_USER_CANCEL:
                if (baseResp.getType() == COMMAND_SENDMESSAGE_TO_WX) {
                    ToastUtil.showToastL("取消分享");
                }
                break;
        }
        finish();
    }
}
