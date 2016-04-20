
package com.heaven.common.http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache.Entry;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.JsonDeserializer;
import com.heaven.common.datamodel.BaseReqDataModel;
import com.heaven.common.util.LogUtil;
import com.heaven.common.util.Util;

public class HttpExtendRequest<T> extends Request<T>{
    //数据交互格式(json或xml)
    private int requestDataType = 0;
    //请求体
    private BaseReqDataModel requestBody;
    private String mRequestBody = null;
    //请求action
    private String reqAction = "";
    //监听后台返回的数据
    private INetListener mCallBack;
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
    /**
     * 设置访问自己服务器时必须传递的参数，密钥,头信息等
     */
    static {
        mHeader.put("APP-Key", "");//应用的key值 
        mHeader.put("APP-Secret", "");//应用的密钥
        mHeader.put("Charset", PROTOCOL_CHARSET);//字符编码格式
        mHeader.put("Accept", "application/json");//能够接受的数据格式
        mHeader.put("Accept-Language", "zh-cn");//接受的语言
        mHeader.put("Content-Type","application/json");//内容数据格式
        mHeader.put("Accept-Encoding", "gzip,deflate");//gzip格式压缩
    }
    
    private HttpExtendRequest(int requestType, String reqAction, String url, INetListener listener) {
        super(requestType,reqAction, url, listener);
    }

    public HttpExtendRequest(HttpTask<T> requestItem) {
        this(requestItem.requestType, requestItem.reqAction, requestItem.requestUrl,requestItem.listener);
        this.reqAction = requestItem.reqAction;
        this.requestBody = requestItem.requestBody;
        this.mCallBack = requestItem.listener;
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
        mRequestBody = DataConvert.invertToGsonStr(this.requestBody);
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
        T result = null;
        InputStream input = new ByteArrayInputStream(response.data);
        Reader reader = new InputStreamReader(input);
        Util.saveData(0, response.data);
        // json格式的数据转换
        if (needDeserializer) {
            result = DataConvert.getBeanObjByDeserializer(reader, clazz, deserializer);
        } else {
            result = DataConvert.getBeanObj(reader, clazz);
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
        if (mCallBack != null) {
            mCallBack.onResponseByID(reqAction, response);
        }
    }
}
