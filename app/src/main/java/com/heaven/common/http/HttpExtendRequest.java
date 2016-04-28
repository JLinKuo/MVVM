
package com.heaven.common.http;

import android.content.Intent;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache.Entry;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSyntaxException;
import com.heaven.common.MainApp;
import com.heaven.common.datamodel.BaseReqDataModel;
import com.heaven.common.datamodel.BaseResDataModel;
import com.heaven.common.ui.activity.MainActivity;
import com.heaven.common.util.LogUtil;
import com.heaven.common.util.Util;

public class HttpExtendRequest<T> extends Request<T> implements Response.ErrorListener{
    //数据交互格式(json或xml)
    private int requestDataType = 0;
    //请求体
    private BaseReqDataModel requestBody;
    private String mRequestBody = null;
    //请求action
    private String reqAction = "";
    //监听后台返回的数据
    private INetCallBack mCallBack;
    //是否需要特殊反序列化
    private boolean needDeserializer = false;
    //特殊反序列化对象
    public JsonDeserializer<T> deserializer = null;
    //范型模板
    private Class<T> clazz = null;
    //数据编码格式
    private static final String PROTOCOL_CHARSET = "UTF-8";
    //请求头
    private static Map<String, String> mHeader = new HashMap<String, String>();
    //自定义请求头
    private HashMap<String, String> headerExtra = null;

    private HttpExtendRequest(int requestType, String url) {
        super(requestType, url);
    }

    public HttpExtendRequest(HttpTask<T> requestItem) {
        this(requestItem.requestType, requestItem.requestUrl);
        this.reqAction = requestItem.reqAction;
        this.requestBody = requestItem.requestBody;
        this.mCallBack = requestItem.listener;
        this.setmErrorListener(this);
        this.needDeserializer = requestItem.needDeserializer;
        this.deserializer = requestItem.deserializer;
        this.clazz = requestItem.clazz;
        this.headerExtra = requestItem.header;
        this.setTag(requestItem.reqAction);
        this.setShouldCache(false);
        this.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    /**
     * 请求内容的数据类型
     */
    @Override
    public String getBodyContentType() {
        return "application/json; charset=UTF-8";
    }

    /**
     * http请求体的生成和返回
     */
    @Override
    public byte[] getBody() throws AuthFailureError {
        mRequestBody = invertToGsonStr(this.requestBody);
        LogUtil.v("Heaven", "convertAfter---" + mRequestBody);
        try {
            return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody,
                    PROTOCOL_CHARSET);
            return null;
        }
    }

    /**
     * 处理后台返回的数据,根据相应的数据格式进行相应的反序列化,生成相应的封装对象
     */
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        /**
         * 得到返回的数据
         */
        T result;
        InputStream input = new ByteArrayInputStream(response.data);
        Reader reader = new InputStreamReader(input);
        Util.saveData(0, response.data);
        // json格式的数据转换
        if (needDeserializer) {
            result = getBeanObjByDeserializer(reader, clazz, deserializer);
        } else {
            result = getBeanObj(reader, clazz);
        }
        Entry entry = HttpHeaderParser.parseCacheHeaders(response);

        return Response.success(result, entry);
    }

    /**
     * 取得http请求头,根据情况可以自己修改
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (this.headerExtra != null) {
            mHeader.putAll(headerExtra);
        }
        return mHeader;
    }

    /**
     * 接受到服务器返回的结果后通过监听向前台分发
     */
    @Override
    protected void deliverResponse(T response) {
        onHttpResponse(reqAction, response);
    }


    public T getBeanObj(Reader reader, Class<T> clazz) throws JsonSyntaxException {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        return gson.fromJson(reader, clazz);
    }

    public T getBeanObjByDeserializer(Reader reader, Class<T> clazz, JsonDeserializer<T> deserializer) throws JsonSyntaxException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(clazz, deserializer);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        return gson.fromJson(reader, clazz);
    }

    /**
     * convert object to gson
     * @param obj http request body
     * @return string
     */
    private String invertToGsonStr(Object obj) {
        String reqStr = "";
        try {
            Gson gson = new Gson();
            reqStr = gson.toJson(obj);
            LogUtil.v("Heaven", "ReqJson---" + reqStr);
            reqStr = URLEncoder.encode(reqStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return reqStr;
    }

    /**
     * package response data
     * @param requestAction request action
     * @param response http response
     */
    public void onHttpResponse(String requestAction, Object response) {
        ResponseData resData = new ResponseData();
        resData.isSuccess = false;
        resData.action = requestAction;
        resData.errorType = HttpErrorConst.SERVER_OTHER_FAIL;
        resData.detail = HttpErrorConst.UNKNOWN_ERROR;
        if (response != null) {
            if (response instanceof BaseResDataModel) {
                BaseResDataModel resDataMode = (BaseResDataModel) response;
                if (HttpErrorConst.SUCCESS.equals(resDataMode.code)) {
                    //success
                    resData.isSuccess = true;
                    resData.action = requestAction;
                    resData.resData = resDataMode;
                } else {
                    int errorType;
                    if (HttpErrorConst.SESSION_FAIL1.equals(resDataMode.code) ||
                        HttpErrorConst.SESSION_FAIL2.equals(resDataMode.code) ) {
                        Intent intent = new Intent();
                        intent.setAction(HttpErrorConst.SESSION_FAIL_FILTER);
                        MainApp.sendGlobalBroadCast(intent);
                        //session expire
                        errorType = HttpErrorConst.SERVER_SESSION_CODE_FAIL;
                    } else if(HttpErrorConst.CHECK_CODE_FAIL_CODE.equals(resDataMode.code)) {
                        Intent intent = new Intent();
                        intent.setAction(HttpErrorConst.CHECK_CODE_FAIL_FILTER);
                        MainApp.sendGlobalBroadCast(intent);
                        errorType = HttpErrorConst.SERVER_SESSION_CODE_FAIL;
                    } else {
                        //other error
                        errorType = HttpErrorConst.SERVER_OTHER_FAIL;
                    }

                    resData.isSuccess = false;
                    resData.action = requestAction;
                    resData.errorType = errorType;
                    resData.detail = resDataMode.detail;
                }
            }
        }
        if (mCallBack != null) {
            mCallBack.onHttpResponse(resData);
        }
    }

    /**
     * classify the error type
     *
     * @param error error message
     */
    public void handleServerError(String reqAction,Object error) {
        ResponseData resData = new ResponseData();
        resData.isSuccess = false;
        resData.action = reqAction;
        resData.errorType = HttpErrorConst.SERVER_OTHER_FAIL;
        resData.detail = HttpErrorConst.UNKNOWN_ERROR;
        if (error != null) {
            String errorMessage = "";
            if (error instanceof ServerError) {
                errorMessage = handleServerInnerError((VolleyError) error);
            } else if (error instanceof TimeoutError) {
                errorMessage = HttpErrorConst.TIMEOUTE_ERROR;
            } else if (error instanceof NetworkError) {
                errorMessage = HttpErrorConst.NETWORKE_ERROR;
            } else if (error instanceof NoConnectionError) {
                errorMessage = HttpErrorConst.NO_CONNECTION_ERROR;
            } else if (error instanceof AuthFailureError) {
                errorMessage = HttpErrorConst.AUTHFAILURE_ERROR;
            } else if (error instanceof VolleyError) {
                Throwable exception = ((VolleyError) error).getCause();
                if (exception != null && exception instanceof JsonSyntaxException) {
                    errorMessage = HttpErrorConst.PROTOCOL_ANALYZE_ERROR;
                }
            }
            resData.detail = errorMessage;
        }
        if (mCallBack != null) {
            mCallBack.onHttpResponse(resData);
        }
    }

    /**
     * Handles the server error, tries to determine whether to show a stock message or to
     * show a message retrieved from the server.
     *
     * @param error error Object
     * @return error message
     */
    private static String handleServerInnerError(VolleyError error) {
        NetworkResponse response = error.networkResponse;
        String serverErrorMessage = HttpErrorConst.SERVER_ERROR;
        if (response != null) {
            switch (response.statusCode) {
                case 404:
                    serverErrorMessage = HttpErrorConst.RESOURCE_NOT_FIND_ERROR;
                    break;
                case 422:
                    serverErrorMessage = HttpErrorConst.SERVICE_CONN_NUM_OUT_ERROR;
                    break;
                case 401:
                    serverErrorMessage = HttpErrorConst.AUTHORIZATION_ERROR;
                    break;
                default:
                    serverErrorMessage = HttpErrorConst.SERVER_ERROR;
            }
        }
        return serverErrorMessage;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        handleServerError(reqAction,error);
    }
}
