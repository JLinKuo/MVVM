package com.heaven.common.http;

/**
 * Created by neusoft on 2016/4/28.
 */
public class HttpErrorConst {
    public static final int SERVER_SESSION_CODE_FAIL = 1;
    public static final int SERVER_OTHER_FAIL = 2;

    public static final String SESSION_FAIL_FILTER = "session_fail";
    public static final String CHECK_CODE_FAIL_FILTER = "check_code_fail";

    public static final String SUCCESS = "0000";
    public static final String SESSION_FAIL1 = "1015";
    public static final String SESSION_FAIL2 = "1016";
    public static final String CHECK_CODE_FAIL_CODE = "0005";

    public static final String TIMEOUTE_ERROR = "连接超时";
    public static final String NETWORKE_ERROR = "网络错误";
    public static final String NO_CONNECTION_ERROR = "连接错误";
    public static final String AUTHFAILURE_ERROR = "认证错误";
    public static final String PROTOCOL_ANALYZE_ERROR = "协议解析错误";
    public static final String RESOURCE_NOT_FIND_ERROR = "资源未找到";
    public static final String SERVICE_CONN_NUM_OUT_ERROR = "连接数超出最大数";
    public static final String AUTHORIZATION_ERROR = "Authorization 证书问题";
    public static final String SERVER_ERROR = "服务器连接错误";
    public static final String SESSION_FAIL = "session失效";
    public static final String CHECK_CODE_FAIL = "验证码失效";
    public static final String UNKNOWN_ERROR = "未知错误";


}
