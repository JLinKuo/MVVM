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
import com.heaven.common.ui.note.NotePopWindow;
import com.heaven.common.viewModel.BaseViewModel;

/**
 *作用描述 登陆和验证码请求处理
 *author liuhongtao
 *created at 2016/4/21 10:58
 */
public class LoginVm extends BaseViewModel {
    private ILoginVm mCallBack = null;

    public LoginVm(ILoginVm callBack,Context context) {
        super(context);
        this.mCallBack = callBack;
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
    protected void onSuccessResponse(String requestID, BaseResDataModel response) {
            if (response != null) {
                if (NetConstant.IDENTIFY_ACTION.equals(requestID)) {
                    mCallBack.resCheckCode(((ResIdentifyID)response).checkCode);
                } else if(NetConstant.LOGIN_ACTION.equals(requestID)) {

                }
            }
    }

    @Override
    protected void onErrorResponse(int errorType, String errorMessage) {
        NotePopWindow.getInstance(mContext).showPopupWindow(errorMessage);
    }
}
