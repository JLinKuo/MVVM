package com.heaven.common.manager;

import com.heaven.common.config.UserInfo;

/**
 * Created by neusoft on 2016/4/20.
 */
public class DataManager {
    private UserInfo mUserInfo = null;

    private static DataManager instance = null;

    private DataManager() {

    }

    protected static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public UserInfo getmUserInfo() {
        return mUserInfo;
    }

    public void setmUserInfo(UserInfo mUserInfo) {
        this.mUserInfo = mUserInfo;
    }
}
