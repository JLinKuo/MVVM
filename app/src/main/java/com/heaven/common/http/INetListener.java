
package com.heaven.common.http;

import com.android.volley.Response.ErrorListener;

public interface INetListener extends ErrorListener{
    //根据请求ID返回请求结果
    void onResponseByID(String requestID, Object response);
}
