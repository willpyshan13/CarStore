package com.yanxin.common.constants;

/**
 * @author zhouz
 * @date 2021/1/31
 */
public interface H5Url {

    String URL_PARAM_KEY = "url";
    String PARAM_PARAM_KEY = "param";
    /**
     * 登录时 账号审核状态不等于审核通过 进入个人中心
     */
    String LOGIN_PARAM_KEY = "login_to_center";

    String URL_HOST = H5UrlConstants.getH5Url();

    /**
     * 添加车辆检测
     */
    String ADD_CAR_DETECTION = URL_HOST + "/addDetection";

    /**
     * 发布商品
     */
    String PUT_AWAY_PRODUCT = URL_HOST + "/putawayProductType";


    /**
     * 旁听
     */
    String AUDIT = URL_HOST + "/audit";

    /**
     * 技师注册
     */
    String TECHNICIAN_REGISTER = URL_HOST + "/jishiRgister";

    /**
     * 店铺注册
     */
    String STORE_REGISTER = URL_HOST + "/shopRgister";
    /**
     * 店铺账户信息
     */
    String ACCOUNT_INFO = URL_HOST + "/accountInfor";
    /**
     * 我的订单
     */
    String MY_ORDER = URL_HOST + "/myOrder";
    /**
     * 店铺管理技师
     */
    String TECHNICIAN_ADMIN = URL_HOST + "/jishiAdmin";
    /**
     * 技师关联店铺
     */
    String RELEVANCY_STORE = URL_HOST + "/relevancyShop";

    /**
     * 问题
     */
    String ASK = URL_HOST + "/ask";

    /**
     * 技师案例
     */
    String CASE = URL_HOST + "/case";

    /**
     * 案例详情
     */
    String CASE_NEWS_DETAIL = URL_HOST + "/caseDetail";

    /**
     * 我的问题
     */
    String MINE_ASK = URL_HOST + "/myConsult";

    /**
     * 我的案例
     */
    String MINE_CASE = URL_HOST + "/myCase";

    /**
     * 发布案例
     */
    String RELEASE_CASE = URL_HOST + "/fabuAnli";
    /**
     * 我的钱包
     */
    String WALLET = URL_HOST + "/wallet";
    /**
     * 技师个人中心
     */
    String TECHNICIAN_INFO = URL_HOST + "/gerenxinxi ";
    /**
     * 技师回答问题
     */
    String TECHNICIAN_ANSWERER = URL_HOST + "/questionAns ";
    /**
     * 案例详情
     */
    String CASE_DETAIL = URL_HOST + "/anliDetail";
    /**
     * 技师账户信息
     */
    String TECHNICIAN_ACCOUNT = URL_HOST + "/accountInforJishi";
    /**
     * 店铺个人中心
     */
    String STORE_INFO = URL_HOST + "/shopCenter";
    /**
     * 店铺 商品详情
     */
    String PRODUCT_DETAIL = URL_HOST + "/putawayProductDetail";
    /**
     * 店铺/技师 新增编辑
     */
    String DTC_ADD_OR_EDIT = URL_HOST + "/dtcAddOrEdit";
    /**
     * 店铺/技师  详情
     */
    String DTC_DETAIL = URL_HOST + "/dtcDetail";
    /**
     * DTC订单列表
     */
    String DTC_LIST = URL_HOST + "/dtcList";
    /**
     * 课程订单列表
     */
    String MY_COURSE = URL_HOST + "/myCourse";
    /**
     * 课程列表
     */
    String MY_MYJIAOCHENG = URL_HOST + "/myjiaocheng";
    /**
     * 新增现场订单
     */
    String SCENCE_ORDER = URL_HOST + "/scenceOrder";
    /**
     * 新增现场订单详情
     */
    String SCENCE_ORDER_DETAIL = URL_HOST + "/scenceOrderDetail";

}
