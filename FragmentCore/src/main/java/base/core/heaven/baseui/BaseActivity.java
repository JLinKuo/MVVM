/**
 * Project Name:CommonStruct
 * File Name:BaseActivity.java
 * Package Name:com.heaven.commonstruct.core.base
 * Date:2016年3月22日下午12:53:47
 * Copyright (c) 2016
 */

package base.core.heaven.baseui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import base.core.heaven.Interface.ISwitcher;
import base.core.heaven.main.R;
import base.core.heaven.manager.SwitchManager;
import base.core.heaven.param.PageParam;

/**
 * ClassName:BaseActivity <br/>
 * Function: 所有activity的基类. <br/>
 * Reason:   扩展基类的方法. <br/>
 * Date:     2016年3月22日 下午12:53:47 <br/>
 * @author neusoft liu.hongtao
 * @since JDK 1.6
 */
public abstract class BaseActivity extends Activity implements ISwitcher {
    protected boolean isMainActivity = false;

    /**
     *
     * initView:(初始化view,在onCreatView中最后调用). <br/>
     *
     * @since JDK 1.6
     */
    protected abstract void initView();

    @Override
    public void popPage() {
        SwitchManager.getInstance().OnBackPress(this);
    }

    @Override
    public boolean isTop(String fgName) {
        return false;
    }

    @Override
    public boolean findPage(String pageName) {
        return false;
    }

    @Override
    public void showPage(PageParam param) {
        SwitchManager.getInstance().showPage(this, param);
    }

    @Override
    public void startPageForResult(PageParam param, BaseFragment result) {
        SwitchManager.getInstance().startPageForResult(this, param, result);
    }

    @Override
    public void onBackPressed() {
        SwitchManager.getInstance().OnBackPress(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        SwitchManager.getInstance().onActivityResult(this, requestCode, resultCode, data);
    }

    /**
     * 生产目标页面参数
     * @param clazz 目标页面的clazz
     * @return 页面参数封装
     */
    protected PageParam producePageParam(final Class<?> clazz) {
        PageParam targetPage = null;
        if (clazz != null) {
            targetPage = new PageParam();
            targetPage.pageName = clazz.getSimpleName();
            targetPage.pageClazz = clazz.getName();
        }
        return targetPage;
    }

}
 
