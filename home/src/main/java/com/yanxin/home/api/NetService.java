package com.yanxin.home.api;

import com.car.baselib.bean.CommonBean;
import com.yanxin.common.beans.res.UserLoginRes;
import com.yanxin.home.beans.QueryHideBean;
import com.yanxin.home.beans.req.AuditListReq;
import com.yanxin.home.beans.req.CaseForVehicleListRep;
import com.yanxin.home.beans.req.CaseListReq;
import com.yanxin.home.beans.req.DtcListReq;
import com.yanxin.home.beans.req.GoodsReq;
import com.yanxin.home.beans.req.PageReq;
import com.yanxin.home.beans.req.PositionReq;
import com.yanxin.home.beans.req.SceneOrderReq;
import com.yanxin.home.beans.req.TechnicianListReq;
import com.yanxin.home.beans.req.UpdateUserImgReq;
import com.yanxin.home.beans.res.AccountInfoRes;
import com.yanxin.home.beans.res.AnswererRes;
import com.yanxin.home.beans.res.CaseForVehicleListRes;
import com.yanxin.home.beans.res.CaseListItemRes;
import com.yanxin.home.beans.res.CourseParentInfoRes;
import com.yanxin.home.beans.res.DictBean;
import com.yanxin.home.beans.res.DtcBeanRes;
import com.yanxin.home.beans.res.GoodsBean;
import com.yanxin.home.beans.res.IssueBeanRes;
import com.yanxin.home.beans.res.SceneOrderListRes;
import com.yanxin.home.beans.res.TechnicianListRes;
import com.yanxin.home.beans.res.VehicleConfigBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author zhouz
 * @date 2021/1/15
 */
public interface NetService {


    @GET(UrlConstant.QUERY_VEHICLE_CONFIG_LIST)
    Observable<CommonBean<List<VehicleConfigBean>>> queryVehicleConfigAllList(@Header ("token") String token, @Path("parentUuid") String parentUuid);

    @POST(UrlConstant.QUERY_CASE_FOR_VEHICLE_LIST)
    Observable<CommonBean<List<CaseForVehicleListRes>>> queryVehicleConfigAllList(@Header ("token") String token, @Body CaseForVehicleListRep caseForVehicleListRep);

    @POST(UrlConstant.QUERY_GOODS_LIST)
    Observable<CommonBean<List<GoodsBean>>> queryGoodsList(@Header("token") String token, @Body GoodsReq goodsReq);

    @POST(UrlConstant.QUERY_CASE_LIST)
    Observable<CommonBean<List<CaseListItemRes>>> queryCaseList(@Header("token") String token, @Body CaseListReq caseListReq);

    @POST(UrlConstant.ANSWER_LIST)
    Observable<CommonBean<List<IssueBeanRes>>> queryAnswerList(@Header ("token") String token, @Body AuditListReq auditListReq);

    @POST(UrlConstant.QUERY_TECHNICIAN_LIST)
    Observable<CommonBean<List<TechnicianListRes>>> queryTechnicianList(@Header ("token") String token, @Body TechnicianListReq technicianListReq);

    @POST(UrlConstant.QUERY_PRE_ANSWER_LIST)
    Observable<CommonBean<List<AnswererRes>>> queryPreAnswerList(@Header("token") String token, @Body PageReq pageReq);

    @POST(UrlConstant.ACCOUNT_SWITCH_ROLE)
    Observable<CommonBean<UserLoginRes>> accountSwitchRole(@Header("token") String token);

    @GET(UrlConstant.ACCOUNT_INFO)
    Observable<CommonBean<AccountInfoRes>> getAccountInfo(@Header("token") String token);

    @Multipart
    @POST(UrlConstant.UPLOAD_IMAGE)
    Observable<CommonBean<String>> uploadOneFile(@Part MultipartBody.Part body, @Query("type") String type);

    @POST(UrlConstant.UPDATE_USER_PHOTO_IMG)
    Observable<CommonBean<String>> updateUserPhotoImg(@Header("token")  String token, @Body UpdateUserImgReq updateUserImgReq);

    @POST(UrlConstant.QUERY_USER_PHOTO_IMG_URL)
    Observable<CommonBean<String>> queryUserPhotoImgUrl(@Header("token")  String token);

    @POST(UrlConstant.QUERY_DTC_LIST)
    Observable<CommonBean<List<DtcBeanRes>>> queryDtcList(@Header("token")  String token, @Body DtcListReq dtcListReq);

    @POST(UrlConstant.QUERY_COURSE_PARENT_LIST)
    Observable<CommonBean<List<CourseParentInfoRes>>> queryCourseParentList(@Header("token")  String token, @Body PageReq pageReq);

    @POST(UrlConstant.QUERY_COURSE_PARENT_GENERAL_NEWS)
    Observable<CommonBean<List<CourseParentInfoRes>>> queryCourseParentNewsList(@Header("token")  String token, @Body PageReq pageReq);

    @POST(UrlConstant.QUERY_SCENE_ORDER_LIST)
    Observable<CommonBean<List<SceneOrderListRes>>> querySceneOrderList(@Header("token")  String token, @Body SceneOrderReq sceneOrderReq);

    @GET(UrlConstant.GRABBING_ORDERS)
    Observable<CommonBean<String>> grabbingOrders(@Header("token")  String token, @Path("sceneOrderUuid") String sceneOrderUuid);

    @POST(UrlConstant.POSITION_REPORT)
    Observable<CommonBean<String>> positionReport(@Header("token")  String token, @Body PositionReq positionReq);

    @GET(UrlConstant.QUERY_DICT_LIST_BY_TYPE)
    Observable<CommonBean<List<DictBean>>> queryDictListByType(@Path("type") String type);

    @GET(UrlConstant.QUERY_HIDE_MODULE)
    Observable<QueryHideBean> queryHideModule(@Path("uuid") int type);

    @GET(UrlConstant.CONSULT_ORDER_SNAP_UP)
    Observable<CommonBean<String>> consultOrderSnapUp(@Header("token")  String token, @Path("uuid") String uuid);
}
