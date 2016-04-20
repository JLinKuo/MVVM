package com.heaven.common.manager;

import android.content.Context;

/**
 * Created by neusoft on 2016/4/20.
 */
public class Engine {

    public static DataManager getDataManager() {
        return DataManager.getInstance();
    }

    public static HttpManager getHttpManager(Context context) {
        return HttpManager.getInstance(context);
    }
}
