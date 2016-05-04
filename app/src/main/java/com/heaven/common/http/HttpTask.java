
package com.heaven.common.http;

import java.util.HashMap;

import com.android.volley.Request.Method;
import com.data.model.BaseReqDataModel;
import com.google.gson.JsonDeserializer;
import com.heaven.common.MainApp;
import com.heaven.common.config.UserInfo;
import com.heaven.common.manager.Engine;

import base.core.heaven.param.AppInfo;

public class HttpTask<T> {
    //数据编码格式
    private static final String PROTOCOL_CHARSET = "UTF-8";
    //请求url
    public String requestUrl;
    //http请求头(根据需求添加)
    public HashMap<String, String> header = null;
    //请求类型(Method.POST,Method.GET,Method.PUT,Method.DELETE)
    public int requestType = Method.POST;
    //请求action
    public String reqAction = null;
    //请求体(xml格式的必须带simple-xml的注解)
    public BaseReqDataModel requestBody = null;
    //是否需要特殊反序列化
    public boolean needDeserializer = false;
    //特殊反序列化对象
    public JsonDeserializer<T> deserializer = null;
    //后台返回的数据需要封装的对象
    public Class<T> clazz = null;
    //网络请求监听
    public INetCallBack listener = null;
    //请求体
    public BaseReqDataModel reqBody = null;
    public HttpTask(BaseReqDataModel reqBody) {
        fillIdentifyData(reqBody);
        this.reqAction = reqBody.action;
        this.requestBody = reqBody;
        header = new HashMap<String, String>();
        initHttpHeader(header);
    }
    
    //填充请求验证信息
    private void fillIdentifyData(BaseReqDataModel reqBody) {
        AppInfo appConfig = MainApp.getAppConfig();
        UserInfo userConfig = Engine.getDataManager().getmUserInfo();
        reqBody.type = AppInfo.DEVICE_TYPE;
        reqBody.ip = appConfig.APP_IP;
        if (userConfig != null) {
            reqBody.sessionID = userConfig.sessionID;
        }
    }
    
    private void initHttpHeader(HashMap<String, String> header) {
        header.put("User-Agent", getUserAgent());
        header.put("APP-Key", "");//应用的key值
        header.put("APP-Secret", "");//应用的密钥
        header.put("Charset", PROTOCOL_CHARSET);//字符编码格式
        header.put("Accept", "application/json");//能够接受的数据格式
        header.put("Accept-Language", "zh-cn");//接受的语言
        header.put("Content-Type", "application/json");//内容数据格式
        header.put("Accept-Encoding", "gzip,deflate");//gzip格式压缩
    }
    
    private String getUserAgent(){
        return "android" + MainApp.getAppConfig().verName;
    }
    
    private String getMIMEType() {
        return "text/xml";
    }
}
