package com.heaven.common.http;


import com.data.model.BaseResDataModel;

/**
 * Created by neusoft on 2016/4/28.
 */
public class ResponseData {
    /**
     * if the request got correct response
     */
    public boolean isSuccess = false;
    /**
     * request action
     */
    public String action = null;
    /**
     * response data package
     */
    public BaseResDataModel resData = null;
    /**
     * request error type
     */
    public int errorType = -1;
    /**
     * error detail
     */
    public String detail = null;
}
