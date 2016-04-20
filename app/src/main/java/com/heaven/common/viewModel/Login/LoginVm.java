package com.heaven.common.viewModel.Login;

import android.content.Context;

import com.android.volley.VolleyError;
import com.heaven.common.config.UserInfo;
import com.heaven.common.datamodel.BaseResDataModel;
import com.heaven.common.datamodel.login.ReqIdentifyID;
import com.heaven.common.datamodel.login.ResIdentifyID;
import com.heaven.common.http.INetListener;
import com.heaven.common.http.NetConstant;
import com.heaven.common.manager.Engine;


/**
 * Created by heaven on 2016/4/17.
 */
public class LoginVm implements INetListener {
    private ILoginVm mCallBack = null;
    private Context mContext = null;

    public LoginVm(ILoginVm callBack,Context context) {
        this.mCallBack = callBack;
        this.mContext = context;
    }

    // 请求验证码
    public void getCheckNum(String userName, String password) {
        ReqIdentifyID req = new ReqIdentifyID();
        req.action = NetConstant.IDENTIFY_ACTION;
        req.username = userName;
        req.password = password;
        UserInfo userInfo = new UserInfo();
        userInfo.userName = userName;
        userInfo.password = password;
        Engine.getDataManager().setmUserInfo(userInfo);
        Engine.getHttpManager(mContext).addRequestQueue(req, ResIdentifyID.class,this);
    }

    // 登陆请求
    public void login(String userName, String pwd, String checkCode) {
//        ReqLogin login = new ReqLogin();
//        login.action = NetConstant.LOGIN_ACTION;
//        String name = user_name.getText().toString();
//        String password = user_password.getText().toString();
//        login.username = name;
//        login.password = password;
//        login.checkCode = check_num.getText().toString();
//        if ("".equals(login.checkCode)) {
//            MainApp.ShowToast(this.getStr(R.string.please_fill_check_code));
//            changeLoginState(false);
//            return;
//        }
    }

    @Override
    public void onResponseByID(String requestID, Object response) {
        if (response != null) {
            mCallBack.resCheckCode(((ResIdentifyID)response).checkCode);
        }
    }

    @Override
    public void onErrorResponse(String s, VolleyError volleyError) {

    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {

    }
}
