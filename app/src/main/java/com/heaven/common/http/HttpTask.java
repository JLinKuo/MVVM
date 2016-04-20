
package com.heaven.common.http;

import java.util.HashMap;

import com.android.volley.Request.Method;
import com.google.gson.JsonDeserializer;
import com.heaven.common.config.UserInfo;
import com.heaven.common.manager.DataManager;

import base.core.heaven.MainApp;
import base.core.heaven.param.AppInfo;

public class HttpTask<T> {
    //请求url
    public String requestUrl;
    //http请求头(根据需求添加)
    public HashMap<String, String> header = null;
    //请求类型(Method.POST,Method.GET,Method.PUT,Method.DELETE)
    public int requestType = Method.POST;
    //请求action
    public String reqAction = null;
    //请求体(xml格式的必须带simple-xml的注解)
    public BaseRequest requestBody = null;
    //是否需要特殊反序列化
    public boolean needDeserializer = false;
    //特殊反序列化对象
    public JsonDeserializer<T> deserializer = null;
    //后台返回的数据需要封装的对象
    public Class<T> clazz = null;
    //网络请求监听
    public INetListener listener = null;
    //请求体
    public BaseRequest reqBody = null;
    public HttpTask(BaseRequest reqBody) {
        fillIdentifyData(reqBody);
        this.reqAction = reqBody.action;
        this.requestBody = reqBody;
        header = new HashMap<String, String>();
        initHttpHeader(header);
    }
    
    //填充请求验证信息
    private void fillIdentifyData(BaseRequest reqBody) {
        AppInfo appConfig = MainApp.getAppConfig();
        UserInfo userConfig = DataManager.getInstance().getmUserInfo();
        reqBody.type = AppInfo.DEVICE_TYPE;
        reqBody.ip = appConfig.APP_IP;
        if (userConfig != null) {
            reqBody.sessionID = userConfig.sessionID;
        }
    }
    
    private void initHttpHeader(HashMap<String, String> header) {
        header.put("User-Agent", getUserAgent());
//        header.put("Content-Type", getMIMEType() + "; charset=utf-8");
    }
    
    private String getUserAgent(){
        return "android" + MainApp.getAppConfig().verName;
    }
    
    private String getMIMEType() {
        return "text/xml";
    }
}
