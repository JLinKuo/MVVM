package com.heaven.common.presenter.login;

import com.heaven.common.config.UserInfo;
import com.heaven.common.datamodel.BaseResDataModel;
import com.heaven.common.datamodel.login.ReqIdentifyID;
import com.heaven.common.datamodel.login.ResIdentifyID;
import com.heaven.common.http.NetConstant;
import com.heaven.common.manager.Engine;
import com.heaven.common.presenter.BasePresenter;

/**
 *作用描述 登陆和验证码请求处理
 *author liuhongtao
 *created at 2016/4/21 10:58
 */
public class LoginPt extends BasePresenter implements ILoginPt.Presenter{
    private ILoginPt.View mViewCallBack = null;

    public LoginPt(ILoginPt.View viewCallBack) {
        this.mViewCallBack = viewCallBack;
        viewCallBack.setPresenter(this);
    }

    @Override
    protected void onSuccessResponse(String requestID, BaseResDataModel response) {
            if (response != null) {
                if (NetConstant.IDENTIFY_ACTION.equals(requestID)) {
                    mViewCallBack.resCheckCode(((ResIdentifyID)response).checkCode);
                } else if(NetConstant.LOGIN_ACTION.equals(requestID)) {

                }
            }
    }

    @Override
    protected void onErrorResponse(Object error) {
        mViewCallBack.resError(error);
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
        Engine.getDataManager().setmUserInfo(userInfo);
        Engine.getHttpManager().addRequestQueue(req, ResIdentifyID.class,this);
    }

    @Override
    public void reqLogin(String userName, String password, String checkCode) {
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
}
