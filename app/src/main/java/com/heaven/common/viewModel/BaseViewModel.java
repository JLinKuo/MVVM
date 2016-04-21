package com.heaven.common.viewModel;

import android.content.Context;

import com.android.volley.VolleyError;
import com.heaven.common.R;
import com.heaven.common.datamodel.BaseResDataModel;
import com.heaven.common.http.INetListener;

/**
 *作用描述 网络回应统一处理入口
 *author liuhongtao
 *created at 2016/4/21 10:52
 */
public abstract class BaseViewModel implements INetListener {
    protected Context mContext = null;
    private static final int SESSION_FAIL = 1;
    private static final int CHECK_CODE_FAIL = 2;
    private static final int PROTOCOL_ANALYZE_FAIL = 3;
    private static final int OTHER_FAIL = 4;

    public BaseViewModel(Context context){
        this.mContext = context;
    }
    @Override
    public void onResponseByID(String requestID, Object response) {
        if (response != null) {
            BaseResDataModel resData = (BaseResDataModel)response;
            if ("0000".equals(resData.code)) {
                //成功
                onSuccessResponse(requestID, resData);
            } else {
                String errorMessage = "";
                int errorType = -1;
                if ("1015".equals(resData.code) || "1016".equals(resData.code)) {
                    //session失效
                    if (mContext != null) {
                        errorMessage = mContext.getResources().getString(R.string.session_fail);
                    }
                    errorType = SESSION_FAIL;
                } else if ("0005".equals(resData.code)) {
                    //验证码过期
                    if (mContext != null) {
                        errorMessage = mContext.getResources().getString(R.string.check_code_fail);
                    }
                    errorType = CHECK_CODE_FAIL;
                } else {
                    //其它错误
                    errorMessage = resData.detail;
                    errorType = OTHER_FAIL;
                }
                onErrorResponse(errorType,errorMessage);
            }
        }
    }

    @Override
    public void onErrorResponse(String s, VolleyError volleyError) {
        onErrorResponse(PROTOCOL_ANALYZE_FAIL,volleyError.getMessage());
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        if (volleyError != null) {
            onErrorResponse(PROTOCOL_ANALYZE_FAIL,volleyError.getMessage());
        }
    }

    protected abstract void onSuccessResponse(String requestID, BaseResDataModel response);

    protected abstract void onErrorResponse(int type, String errorMessage);


}
