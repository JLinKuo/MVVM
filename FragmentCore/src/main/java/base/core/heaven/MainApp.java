/**
 * Project Name:SxPreAccept
 * File Name:MainApplication.java
 * Package Name:com.neusoft.preaccept
 * Date:2015骞?11鏈?23鏃ヤ笅鍗?1:16:41
 * Copyright (c) 2015
 */

package base.core.heaven;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.Toast;

import base.core.heaven.param.AppInfo;


/**
 * ClassName:MainApplication <br/>
 * Function: 应用的主入口. <br/>
 * Date: 2015年11月23日 下午1:16:41 <br/>
 *
 * @author neusoft liu.hongtao
 * @version
 * @since JDK 1.6
 */
public class MainApp extends Application {

    private static MainApp mInstance;

    private static Context context = null;
    // 用于处理一些耗时的操作,统一使用
    private HandlerThread handlerThread = null;
    private static Handler handler = null;
    // 应用相关的属性
    private static AppInfo appConfig;

    public static MainApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {

        mInstance = this;
        context = getApplicationContext();
        handlerThread = new HandlerThread("");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
        appConfig = new AppInfo();
        initAppInfo(appConfig);
    }


    /* 初始化软件信息 */
    private void initAppInfo(AppInfo app) {
        PackageManager pkManager = getPackageManager();
        app.packageName = getPackageName();
        PackageInfo info = null;
        if (pkManager != null) {
            try {
                info = pkManager.getPackageInfo(app.packageName, PackageManager.GET_SIGNATURES);
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }

        }

        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            app.name = appInfo.loadLabel(pkManager).toString();// 软件名称在这里赋值
            app.verCode = info.versionCode;
            app.verName = info.versionName;
        }
        // app.APP_IP = Util.getLocalHostIp();
    }

    // 耗时操作的代码用Runnable包装一下，post进来单独执行
    public static void postRunnable(Runnable r) {
        handler.post(r);
    }

    public static Context getContext() {
        return context;
    }

    public static AppInfo getAppConfig() {
        return appConfig;
    }

    public static void ShowToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
