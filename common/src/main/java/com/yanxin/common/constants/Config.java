package com.yanxin.common.constants;

/**
 * @author zhouz
 * @date 2021/1/10
 */
public interface Config {


    int PAGE_NUM = 1;
    int PAGE_SIZE = 30;

    /**
     * 人像录入时照片本地存储位置
     */
    String PATH = "/Store/picture";

    String ROUTE_PRIVACY_POLICY_KEY = "privacy_policy";
    /**
     * 版权声明
     */
    int AGREEMENT_COPYRIGHT = 1;
    /**
     * 隐私政策
     */
    int AGREEMENT_PRIVACY = 2;
    /**
     * 服务协议
     */
    int AGREEMENT_SERVE = 3;

    /**
     * 字典类型查询字典 查询客服电话
     */
    String CUSTOMER_SERVICE_PHONE = "customer_service_phone";

    String SP_CHECK_BOX_VALUE_KEY = "check_box_value";
    /**
     * 回答状态 0:为回答  1:已回答
     */
    int HOME_CAR_QA_STATUS = 0;

    /**
     * 咨询类型 1：技师提问 2：全国技师提问
     */
    int HOME_CAR_CONSULT_TECHNICIAN_TYPE = 1;
    int HOME_CAR_CONSULT_NATIONWIDE_TYPE = 2;

    /**
     * 字典类型查询字典集合 attach_sys:所属系统
     */
    String ATTACH_SYS = "attach_sys";
    /**
     * 手机号码验证
     */
    String TEL_REG = "^[1][3,4,5,6,7,8,9][0-9]{9}$";

    String TOKEN_SP_KEY = "token_sp_key";

    String USER_TYPE_SP_KEY = "user_type_sp_key";

    /**
     * 查询车辆品牌
     */
    String REPAIR_BRAND = "0001";
    //审核通过
    int CHECK_APPROVE = 1;

    String USER_CHECK_STS_SP_KEY = "user_check_sts_sp_key";

    String MOBILE_SP_KEY = "mobile_sp_key";

    /**
     * 是否为webView 401 状态返回
     */
    String WEB_VIEW_BACK = "web_view_back";

    /**
     * 用户类型 1车主 2 技师 3 店铺
     */
    String USER_TYPE_CAR_OWNER = "1";
    String USER_TYPE_TECHNICIAN = "2";
    String USER_TYPE_STORE = "3";

    /**
     * 登陆终端 vehicle：车主 merchant:（技师/店铺）
     */
    String USER_MERCHANT_TERMINAL = "merchant";

    /**
     * 商品状态 key
     */
    String GOODS_STATUS_KEY = "goods_status_key";

    /**
     * 现场支持下单 key
     */
    String SERVICE_ORDER_TYPE_KEY = "service_order_type_key";

    /**
     * 查询现场订单类型，0：未抢订单，1：已抢订单，2：发布订单
     */
    int SERVICE_ORDER_ACCESS = 0;
    int SERVICE_ORDER_ALREADY = 1;
    int SERVICE_ORDER_RELEASE = 2;

    /**
     * 商品状态 0 库存(下架) 1 在售(上架)
     */
    int SHOP_UN_RELEASE = 0;
    int SHOP_RELEASE = 1;

    /**
     * 未注册店铺
     */
    int NOT_REGISTER_STORE = 30006;
    /**
     * 未注册技师
     */
    int NOT_REGISTER_TERMINAL = 11000;



    /**
     * 筛选条件类型 品牌
     */
    int FILTRATE_TYPE_BRAND = 1;

    /**
     * 筛选条件类型 车型
     */
    int FILTRATE_TYPE_MODEL = 2;

    /**
     * 筛选条件类型 系统
     */
    int FILTRATE_TYPE_SYS = 3;

}
