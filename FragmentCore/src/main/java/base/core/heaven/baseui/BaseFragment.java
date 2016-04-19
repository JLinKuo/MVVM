/**
 * Project Name:CommonStruct
 * File Name:BaseFragment.java
 * Package Name:com.heaven.commonstruct.core.base
 * Date:2016年3月22日下午12:58:06
 * Copyright (c) 2016
 */

package base.core.heaven.baseui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import base.core.heaven.Interface.IOnPageBackResult;
import base.core.heaven.Interface.ISwitcher;
import base.core.heaven.param.PageParam;

/**
 * ClassName:BaseFragment <br/> 
 * Function: fragment基类. <br/> 
 * Reason:   方法扩展. <br/> 
 * Date:     2016年3月22日 下午12:58:06 <br/> 
 * @author neusoft liu.hongtao
 * @version
 * @since JDK 1.6
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    public String mFragmentName = null;
    /**
     * 启动fragment参数
     */
    protected PageParam mPageParam = null;
    /**
     * 结果参数回调监听
     */
    protected IOnPageBackResult mOnBackResultListener = null;
    /**
     * fragment切换监听
     */
    protected ISwitcher mISwitcher = null;
    /**
     * activity上下文
     */
    protected Context mContext = null;
    /**
     * 本ui中的header级别
     */
    protected int titleLevel = -1;
    /**
     * header中title内容
     */
    protected String title = "";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
        this.mISwitcher = (ISwitcher) activity;
        initTitle();
        initData();
    }

    /**
     *
     * initTitle:(页面title初始化). <br/> 
     *
     * @since JDK 1.6
     */
    protected abstract void initTitle();

    /**
     *
     * initData:(页面静态数据初始化). <br/> 
     *
     * @since JDK 1.6
     */
    protected abstract void initData();

    /**
     *
     * createView:(创建页面视图). <br/> 
     *
     * @param inflater ui膨胀器
     * @param container  ui所属容器
     * @since JDK 1.6
     */
    protected abstract View createView(LayoutInflater inflater, ViewGroup container);

    /**
     *作用描述 页面初始化
     *created at 2016/3/31 15:34
     */
    protected abstract void initView(View rootView);

    /**
     * 确认按钮操作
     */
    protected abstract void conFirm();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = createView(inflater, container);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        closeKeyBoard();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            closeKeyBoard();
        }
    }

    /**
     *
     * setPageParm:(页面相关参数). <br/> 
     *
     * @param mFromPageParam page参数
     * @since JDK 1.6
     */
    public void setPageParm(PageParam mFromPageParam) {
        this.mPageParam = mFromPageParam;
    }

    /**
     *
     * getOnBackResultListener:(结果回调接口). <br/> 
     *
     * @since JDK 1.6
     */
    public void setOnBackResultListener(IOnPageBackResult mOnBackResultListener) {
        this.mOnBackResultListener = mOnBackResultListener;
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

    /**
     *
     * getStr:(取得字符串的公用方法). <br/> 
     *
     * @param stringId 字符串id
     * @return 结果
     * @since JDK 1.6
     */
    public String getStr(int stringId) {
        return mContext.getResources().getString(stringId);
    }

    /**
     *
     * closeKeyBoard:(隐藏时关闭软键盘). <br/> 
     *
     * @since JDK 1.6
     */
    private void closeKeyBoard() {
        View view = getView();
        if (view != null) {
            InputMethodManager inputManger = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
 
