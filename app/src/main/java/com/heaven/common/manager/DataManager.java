package com.heaven.common.manager;

import com.data.model.login.ResLogin;
import com.heaven.common.config.UserInfo;

/**
 * Created by neusoft on 2016/4/20.
 */
public class DataManager {
    private UserInfo mUserInfo = null;

    private static DataManager instance = null;

    private DataManager() {
        mUserInfo = new UserInfo();
    }

    protected static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public void memoryLoginData(String userName, String password) {
        mUserInfo.userName = userName;
        mUserInfo.password = password;
    }

    public void memoryLoginSession(String session) {
        mUserInfo.sessionID = session;
    }

    public void memoryLoginRes(ResLogin loginData) {
        mUserInfo.loginData = loginData;
    }

    public UserInfo getmUserInfo() {
        return mUserInfo;
    }
}
