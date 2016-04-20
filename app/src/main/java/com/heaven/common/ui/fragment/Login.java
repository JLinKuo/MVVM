package com.heaven.common.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.heaven.common.R;
import com.heaven.common.databinding.MainBinding;
import com.heaven.common.manager.HttpManager;
import com.heaven.common.util.LogUtil;
import com.heaven.common.viewModel.Login.ILoginVm;
import com.heaven.common.viewModel.Login.LoginVm;

import base.core.heaven.baseui.BaseFragment;
import base.core.heaven.param.PageParam;

public class Login extends BaseFragment implements ILoginVm {
    LoginVm loginVm = null;
    @Override
    protected void initTitle() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.main, null);
        loginVm = new LoginVm(this);
        initView(rootView);
        return rootView;
    }

    @Override
    protected void initView(View rootView) {
        Button btn = (Button) rootView.findViewById(R.id.plus_one_button);
        Button confirm = (Button) rootView.findViewById(R.id.confirm);
        btn.setOnClickListener(this);
        confirm.setOnClickListener(this);
        request();
    }

    private void request() {
        StringRequest request = new StringRequest(
                Request.Method.GET,
                "http://kyfw.12306.cn/otn/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogUtil.w("heaven", response);
//                        user.setFirstName(response);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogUtil.w("heaven", error.toString());
                    }

                    public void onErrorResponse(String action, VolleyError error){

                    }
                });
        HttpManager.getsInstance(mContext).addRequest(request, this);
    }
    @Override
    protected void conFirm() {

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
//        if (id == R.id.plus_one_button) {
//            PageParam param = producePageParam(BlankFragment2.class);
//            param.needHide = true;
//            Bundle data = new Bundle();
//            data.putString("heaven", "heaventest");
//            param.data = data;
//            mISwitcher.startPageForResult(param, this);
////            mISwitcher.showPage(param);
//        } else if (id == R.id.confirm) {
//            conFirm();
//        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("Heaven", "BlankFragment1");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Heaven", "BlankFragment1--onDestroy");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            PageParam param = data.getParcelableExtra("param");
            Bundle paramData = param.data;
            String str = paramData.getString("heaven");
            Log.i("Heaven", "initIntentExtraData" + str);
            Log.i("Heaven", "" + requestCode + ":" + resultCode);
        }
    }

    @Override
    public void resCheckCode(String checkCode) {

    }

    @Override
    public void resLoginSuccess() {

    }
}
