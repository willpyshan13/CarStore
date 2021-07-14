package com.yanxin.home.api;

/**
 * @author zhouz
 * @date 2021/1/16
 */
public interface UrlConstant {



    /**
     * 查询父节点下车辆子节点，根节点传-1
     */
    String QUERY_VEHICLE_CONFIG_LIST = "/account/vehicleConfig/queryList/{parentUuid}";

    /**
     * 优秀技师案例
     */
    String QUERY_CASE_FOR_VEHICLE_LIST = "/order/case/queryCaseForVehicleList";

    /**
     * 查询商品列表
     */
    String QUERY_GOODS_LIST = "/account/goods/queryStoreGoodsList";

    /**
     * 查询案例列表
     */
    String QUERY_CASE_LIST = "/order/case/queryCaseForTechnicianList";

    /**
     * 查询可旁听列表
     */
    String ANSWER_LIST = "/order/answer/answerList";

    /**
     * 查询问答技师列表
     */
    String QUERY_TECHNICIAN_LIST = "/account/technician/queryTechnicianAnswerList";

    /**
     * 技师待问答列表
     */
    String QUERY_PRE_ANSWER_LIST = "/order/answer/preAnswerList";

    /**
     * 技师店铺角色切换
     */
    String ACCOUNT_SWITCH_ROLE = "/account/login/switchRole";

    /**
     * 账户信息
     */
    String ACCOUNT_INFO = "/account/account/account";

    /**
     * 图片上传
     */
    String UPLOAD_IMAGE = "/utility/file/uploadFile";

    String UPDATE_USER_PHOTO_IMG = "/account/user/updateUserPhotoImg";

    String QUERY_USER_PHOTO_IMG_URL = "/account/user/queryUserPhotoImgUrl";

    /**
     * 查询dtc故障列表
     */
    String QUERY_DTC_LIST = "/order/dtc/list";

    /**
     * 查询教程分页列表
     */
    String QUERY_COURSE_PARENT_LIST = "/order/courseParent/list";

    /**
     * 查询教程分页列表
     */
    String QUERY_COURSE_PARENT_GENERAL = "order/courseParent/listGeneral";

    /**
     * 查询最新教程分页列表
     */
    String QUERY_COURSE_PARENT_GENERAL_NEWS = "order/courseParent/listNewest";

    /**
     * 查询现场订单列表
     */
    String QUERY_SCENE_ORDER_LIST = "order/sceneOrder/querySceneOrderList";

    /**
     * 现场订单抢单
     */
    String GRABBING_ORDERS = "/order/sceneOrder/grabbingOrders/{sceneOrderUuid}";

    /**
     * 全国咨询订单枪单（技师)
     */
    String CONSULT_ORDER_SNAP_UP = "/order/orderConsult/consultOrderSnapUp/{uuid}";

    /**
     * 上报用户位置信息（技师/店铺）
     */
    String POSITION_REPORT = "/account/position/report";

    /**
     * 根据字典类型查询字典集合
     */
    String QUERY_DICT_LIST_BY_TYPE = "/manager/dict/queryListByType/{type}";

    /**
     * 根据字典类型查询字典集合
     */
    String QUERY_HIDE_MODULE = "/manager/dict/queryByUuid/{uuid}";

}
