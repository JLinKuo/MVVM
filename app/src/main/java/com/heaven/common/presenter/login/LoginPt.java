package com.heaven.common.presenter.login;

import com.data.model.login.ReqIdentifyID;
import com.data.model.login.ReqLogin;
import com.data.model.login.ResIdentifyID;
import com.data.model.login.ResLogin;
import com.heaven.common.MainApp;
import com.heaven.common.config.UserInfo;
import com.heaven.common.http.HttpErrorConst;
import com.heaven.common.http.INetCallBack;
import com.heaven.common.http.NetConstant;
import com.heaven.common.http.ResponseData;
import com.heaven.common.manager.Engine;

/**
 *作用描述 登陆和验证码请求处理
 *author liuhongtao
 *created at 2016/4/21 10:58
 */
public class LoginPt implements ILoginPt.Presenter,INetCallBack{
    private ILoginPt.View mViewCallBack = null;

    public LoginPt(ILoginPt.View viewCallBack) {
        this.mViewCallBack = viewCallBack;
        viewCallBack.setPresenter(this);
    }

    @Override
    public void reqCheckCode(String userName, String password) {
        ReqIdentifyID req = new ReqIdentifyID();
        req.action = NetConstant.IDENTIFY_ACTION;
        req.username = userName;
        req.password = password;
        UserInfo userInfo = new UserInfo();
        userInfo.userName = userName;
        userInfo.password = password;
        Engine.getHttpManager().addRequestQueue(req, ResIdentifyID.class,this);
    }

    @Override
    public void reqLogin(String userName, String password, String checkCode) {
        ReqLogin login = new ReqLogin();
        login.action = NetConstant.LOGIN_ACTION;
        login.username = userName;
        login.password = password;
        login.checkCode = checkCode;
        Engine.getDataManager().memoryLoginData(userName, password);
        Engine.getHttpManager().addRequestQueue(login, ResLogin.class,this);
    }

    @Override
    public void onHttpResponse(ResponseData data) {
        if (data != null) {
            if (data.isSuccess) {
                if (NetConstant.IDENTIFY_ACTION.equals(data.action)) {
                    Engine.getDataManager().memoryLoginSession(data.resData.sessionID);
                    mViewCallBack.resCheckCode(((ResIdentifyID)data.resData).checkCode);
                } else if(NetConstant.LOGIN_ACTION.equals(data.action)) {
                    Engine.getDataManager().memoryLoginRes((ResLogin)data.resData);
                    mViewCallBack.resLoginSuccess(true);
                }
            } else {
                if (data.errorType == HttpErrorConst.SERVER_OTHER_FAIL) {
                    MainApp.ShowToast(data.detail);
                }
                mViewCallBack.resLoginSuccess(false);

            }
        }
    }
}
