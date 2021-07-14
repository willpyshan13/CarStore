package com.yanxin.login.api;

import com.car.baselib.bean.CommonBean;
import com.yanxin.login.beans.req.UserLoginReq;
import com.yanxin.common.beans.res.UserLoginRes;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author zhouz
 * @date 2021/1/15
 */
public interface LoginNetService {

    @GET(UrlConstant.GET_VERIFICATION_CODE)
    Observable<CommonBean<String>> getVerificationCode(@Path("accountName") String accountName,@Path("terminal") String terminal);

    @POST(UrlConstant.USER_LOGIN)
    Observable<CommonBean<UserLoginRes>> userLogin(@Body UserLoginReq uerLoginReq);

    @GET(UrlConstant.SPLASH_CONFIG)
    Observable<CommonBean<Object>> splashConfig(@Path("code") String code);

}
