package com.car.baselib.router;

import android.app.Activity;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 第三方Router的封装
 */
public class Router {

    private static Router instance;
    private ARouter aRouter;
    
    public Router(){
        aRouter = ARouter.getInstance();
    }

    public static Router getInstance() {
        if(instance == null) {
            instance = new Router();
        }
        return instance;
    }

    public RouterData build(String pageId){
        return new RouterData(aRouter.build(pageId));
    }
    
    public RouterData build(Context context, String pageId){
        return new RouterData(aRouter.build(pageId),context);
    }

    public RouterData build(Activity activity, String pageId, int requestCode){
        return new RouterData(aRouter.build(pageId),activity,requestCode);
    }

}
