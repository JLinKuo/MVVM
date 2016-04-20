/**
 * Project Name:SxPreAccept
 * File Name:NetConstant.java
 * Package Name:com.neusoft.preaccept.http
 * Date:2015年11月24日下午5:46:47
 * Copyright (c) 2015
 */

package com.heaven.common.http;

/**
 * ClassName:NetConstant <br/>
 * Function: 网络请求常量. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年11月24日 下午5:46:47 <br/>
 *
 * @author neusoft liu.hongtao
 * @since JDK 1.6
 */
public class NetConstant {
    //1:陕西 2:四川
    public static int TAG = 1;
//     本机服务地址
//     public final static String WEB_HOST = "http://10.4.124.84:8080/crm4/rest/";
//     东软-开发-金玉石
//     public final static String WEB_HOST = "http://10.4.123.65:8080/crm4/rest/";
//     胡恩德
//     public final static String WEB_HOST = "http://10.4.123.90:8090/crm4/rest/";
//     徐文正
//     public final static String WEB_HOST = "http://10.4.123.161:8090/crm4/rest/";
    
    
//     陕西现场正式展示地址
//    public final static String WEB_HOST = "http://123.139.156.95:8082/crm4/rest/";
//    public final static String WEB_HOST = "http://123.139.156.95:8080/crm4/rest/";
//    陕西测试地址
//    public final static String WEB_HOST = "http://130.84.1.100:8090/crm4/rest/";

    
    
//    四川现场正式地址
//    public final static String WEB_HOST = "http://130.81.10.21:8889/crm4/rest/";
//    四川现场测试地址
//    public final static String WEB_HOST = "http://130.81.9.19:8889/crm4/rest/";

	/* 请求action */

    public static String WEB_HOST = "";
    static {
        if (TAG == 2) {
//          赵武林
//          WEB_HOST = "http://10.4.127.233:8989/crm4/rest/";
//              胡恩德
//                WEB_HOST = "http://10.4.123.90:8090/crm4/rest/";
//             四川现场正式地址
//              WEB_HOST = "http://130.81.10.21:8889/crm4/rest/";
//             四川现场测试地址
            WEB_HOST = "http://130.81.9.19:8889/crm4/rest/";
        } else {
            // 陕西现场测式地址
            WEB_HOST = "http://130.84.1.100:8090/crm4/rest/";
            // 陕西现场正式地址
//            WEB_HOST = "http://123.139.156.95:8082/crm4/rest/";
        }
    }
    
    // 融合查询产品
    public static String PRODUCT_INFORMATION_COMB = "productinfo/comb";

    // 获取登陆验证码
    public static String IDENTIFY_ACTION = "login/code";
    // 应用更新
    public static String APP_UPDATA = "app_updata";
    // 登陆
    public static String LOGIN_ACTION = "login";
    // 查询产品
    public static String FIND_PRODUCT_ACTION = "productinfo";
    // 23转4类型套餐
    public static String FIND_23TO4_PRODUCT_ACTION = "productinfo/query23to4";
    // 查询活动
    public static String FIND_FAVOUR_ACTION = "favourinfo";
    // 客户资料校验
    public static String CHECK_USERINFO_ACTION = "mcheck";
    // 可选号码查询-简单版
    public static String FIND_OPTIONNUM_ACTION = "serviceidqry";
    // 号码状态变更-简单版
    public static String NUM_STATE_CHANGE_ACTION = "serviceidchg";
    // 终端状态查询变更
    public static String TERMINAL_STATE_FIND_ACTION = "termchg";
    // 开户处理申请
    public static String OPEN_ACCOUNT_ACTION = "orderpresub";
    // 开户提交申请
    public static String OPEN_ACCOUNT_SUB_ACTION = "ordersub";
    // 卡数据同步提交
    public static String CARD_DATA_SYN_SUB_ACTION = "carddatesyn";
    // 写卡数据查询
    public static String FIND_WRITE_CARD_ACTION = "carddateqry";
    // 单独写卡数据查询
    public static String ONLY_FIND_WRITE_CARD_ACTION = "carddateqry/info";
    // 写卡结果通知
    public static String WRITE_CARD_RESULT_ACTION = "cardnotify";
    // 老用户业务校验
    public static String CHECK_OLD_CUSTOMER_ACTION = "olducheck";
    // 老用户优惠购机处理申请
    public static String OLD_CUST_PRI_APPLY_ACTION = "actipresub";
    // 老用户优惠购机处理提交
    public static String OLD_CUST_PRI_SUB_ACTION = "actisub";
    // 流量包订购
    public static String BOOK_FLOW_PACKAGE_ACTION = "trafficord";
    // 根据流量类型请求流量包
    public static String BOOK_FLOW_TYPE_ACTION = "trafficord/req";
    // 套餐变更
    public static String COMBO_CHANGE_ACTION = "productch";
    // 套餐变更撤单
    public static String COMBO_CHANGE_CANCEL_ACTION = "prochgcannel";
    // 修改密码
    public static String CHANGE_PWD_ACTION = "login/uppwd";
    // 路由查询
    public static String ROUTE_FIND_ACTION = "routerqry";
    // 免填单打印
    public static String FREE_PRINT_ORDER_ACTION = "printorder";
    // 订单查看
    public static String ORDER_QUERY_ACTION = "orderquery";
    // 公告新闻
    public static String PUBLISH_NOTE_ACTION = "pubnote";
    // 问题反馈
    public static String FEEDBACK_PROBLEM_ACTION = "probbak";
    // 客户资料修改
    public static String CUSTOM_INFO_CHANGE_ACTION = "custmod";
    // 用户资料校验三户返回
    public static String USER_INFO_CHECK_THREE_ACTION = "threepart";
    // 白卡失败数据统计
    public static String PURE_CARD_FAIL_COUNT_ACTION = "cardstat";
    // 日收报表
    public static String DAY_REPORT_ACTION = "orderquery";
    // 日受理报表
    public static String DAY_DEAL_REPORT_ACTION = "orderquery";
    // 代理商余额查询
    public static String AGENT_FEE_ACTION = "agentfee";
    // 分页查询套餐
    public static String FIND_COMBO_BY_PAGE_ACTION = "productinfo/query";
    // 渠道同步
    public static String CHANNEL_SYN_ACTION = "bsdm";
    // 快捷菜单查询
    public static String QUICK_MENU_ACTION = "workmenu/query";
    // 查询发展渠道，发展人
    public static String CHANNEL_DATA_ACTION = "dealerque";
    // 国政通认证接口
    public static String GUOZHENGTONG_CERT_ACTION = "certcheck";
    // 用户实名补录
    public static String REAL_NAME_MEND = "custmod";
    //当前发展人发展渠道
    public static String DEVELOP_PERSON_ACTION = "dealerque/user";
    //单独白卡写卡的卡费用和流水单号请求
    public static String READ_WRITE_CARD_DATA_ACTION = "carddateqry/info";
    //缴费时用户通过号码查询用户状态
    public static String ROUTER_QRY = "routerqry";
    //用户信息查询
    public static String USER_FEE_QRY = "feequery";
    //缴费用户帐户费用信息查询
    public static String QRY_ACCOUNT = "qryaccount";
    //缴费
    public static String PAY_FEE = "payfee";
    //4G登网维护
    public static String IMSI_ADD_NEW = "searchImsi/addNew";
    //代理商折扣请求
    public static String DISCOUNTFEE = "discountFee";
    //预登陆返单用户验证请求
    public static String RESERVEREORDER = "reservereorder";
    //预登陆返单请求
    public static String RESERVEREORDER_SUB = "reservereorder_sub";
    //预登陆返单请求
    public static String RESERVEREORDER_OPEN = "reservereorder_open";
    /*主副请求*/
//    主副卡套餐
    public static String PRODUCTINFO_SECQRY = "productinfo/secqry";
    //主副卡开户请求
    public static String MAIN_AND_ASSISTANT = "orderpresub2nd";
    //主副卡开户提交
    public static String MAIN_AND_ASSISTANT_SUB = "ordersub2nd";
    /*主副卡请求*/
    /*2,3转4*/
    //开户申请
    public static String OPEN_ACCOUNT_23_TO_4 = "tfpresub";
    //无活动产品请求
    public static String NON_ACTIVITY_PRODUCT = "productinfo/sepe";
    //23转4认证
    public static String TFCHECK = "tfcheck";
    //附加产品请求
    public static String EXTRA_PRODUCT = "productinfo/annexprod";
    /*2,3转4*/
    //集团客户信息请求
    public static String GROUPQRY = "groupqry";
    
    
    /* 融合和固网业务请求 */
    //宽带、固话入网查询地址
    public static final String CREATE_USER_ADDR = "createuseraddress";
    //宽带、固话入网选择地址
    public static final String ANTICIPATION_4G = "resanticipation4g";
    //宽带编码
    public static final String INTERACCTQUERY = "interacctquery";
    public static final String INTERACCTCHECK = "interacctcheck";
    public static final String QRYGX = "qrygx";
    public static final String ADDNUMQRY = "addnumqry";
    //固话新装
    public static final String GNUMBERQUERY = "gnumberquery";
    public static final String RELENUMBER4G = "relenumber4g";
    //宽带预占
    public static final String WLANPERSUB = "wlanpersub";
    //宽带缴费
    public static final String WLANSUB = "wlansub";
    //宽带固网活动请求
    public static final String ASDL_LAN_FAV = "favourinfo/query2nd";
    //宽带固网产品请求
    public static final String ASDL_LAN_PRODUCT = "favourinfo/query2ndpage";
    //融合活动
    public static final String CPMAINFAVOURINFO = "favourinfo/cpmainfavourinfo";
    /* 融合和固网业务请求 */
    /* 请求ID */
    // 验证码请求ID
    public static int REQ_IDENTIFY_CODE = 1;
    // 登陆请求ID
    public static int REQ_LOGIN_CODE = 2;

    /* 请求返回码类别 */
    public static String SUCCESS = "0000";

    public static String NET_ERROR = "net_error";

    public static String NET_ERROR_NOTE = "网络连接错误";

    public static String SERVER_ERROR_NOTE = "服务器错误";

    public static String PROTOCOL_ERROR_NOTE = "协议错误错误";

    public static String URL_ERROR_NOTE = "地址错误";
}
