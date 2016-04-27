package com.heaven.common.presenter;

import com.android.volley.VolleyError;
import com.heaven.common.datamodel.BaseResDataModel;
import com.heaven.common.http.ErrorMessage;
import com.heaven.common.http.INetListener;
import com.heaven.common.http.VelloyHttpError;

/**
 * 作用描述 网络回应统一处理入口
 * author liuhongtao
 * created at 2016/4/21 10:52
 */
public abstract class BasePresenter implements INetListener {
    @Override
    public void onResponseByID(String requestID, Object response) {
        if (response != null) {
            if (response != null && response instanceof BaseResDataModel) {
                BaseResDataModel resData = (BaseResDataModel) response;
                if ("0000".equals(resData.code)) {
                    //成功
                    onSuccessResponse(requestID, resData);
                } else {
                    String errorMessage;
                    int errorType;
                    if ("1015".equals(resData.code) || "1016".equals(resData.code)) {
                        //session失效
                        errorType = VelloyHttpError.SESSION_ERROR;
                    } else if ("0005".equals(resData.code)) {
                        //验证码过期
                        errorType = VelloyHttpError.CHECK_CODE_ERROR;
                    } else {
                        //其它错误
                        errorType = VelloyHttpError.OTHER_ERROR;
                    }
                    errorMessage = resData.detail;
                    ErrorMessage error = new ErrorMessage();
                    error.mErrorType = errorType;
                    error.mErrorDetail = errorMessage;
                    onHttpError(error);
                }
            }
        }
    }

    @Override
    public void onErrorResponse(String action, VolleyError volleyError) {
        onErrorResponse(volleyError);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        onHttpError(volleyError);
    }

    protected abstract void onSuccessResponse(String requestID, BaseResDataModel response);

    protected abstract void onHttpError(Object error);


}
