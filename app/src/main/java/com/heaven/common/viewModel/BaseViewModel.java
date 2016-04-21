package com.heaven.common.viewModel;

import android.content.Context;

import com.android.volley.VolleyError;
import com.heaven.common.datamodel.BaseResDataModel;
import com.heaven.common.http.INetListener;

/**
 *作用描述 网络回应统一处理入口
 *author liuhongtao
 *created at 2016/4/21 10:52
 */
public abstract class BaseViewModel implements INetListener {
    protected Context mContext = null;

    public BaseViewModel(Context context){
        this.mContext = context;
    }
    @Override
    public void onResponseByID(String requestID, Object response) {
        if (response != null) {
            BaseResDataModel resData = (BaseResDataModel)response;
            if ("0000".equals(resData.code)) {
                //成功
                onResponse(requestID,resData);
            } else if ("1015".equals(resData.code) || "1016".equals(resData.code)) {
                //session失效

            } else if ("0005".equals(resData.code)) {
                //验证码过期

            }
        }
    }

    @Override
    public void onErrorResponse(String s, VolleyError volleyError) {

    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }

    protected abstract void onResponse(String requestID, BaseResDataModel response);
}
