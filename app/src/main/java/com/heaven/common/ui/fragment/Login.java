package com.heaven.common.ui.fragment;


import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heaven.common.R;
import com.heaven.common.http.NetConstant;
import com.heaven.common.util.TimeCount;
import com.heaven.common.viewModel.Login.ILoginVm;
import com.heaven.common.viewModel.Login.LoginVm;

public class Login extends BaseSubFragment implements ILoginVm {

    private LoginVm mLoginVm = null;
    private String mCheckCode = null;

    private EditText user_name;
    private EditText user_password;
    private EditText check_num;
    private TextView get_check_num;
    private RelativeLayout login_btn;
    private TextView login;
    private ImageView login_state;

    private TimeCount timeCount = null;
    private AnimationDrawable login_state_bg = null;

    @Override
    public void initTitle() {
        title = getStr(R.string.login);
    }

    @Override
    protected void initData() {
        mLoginVm = new LoginVm(this,mContext);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.login, null);
        initView(rootView);
        return rootView;
    }

    @Override
    public void initView(View rootView) {

        user_name = (EditText) rootView.findViewById(R.id.user_name);
        user_password = (EditText) rootView.findViewById(R.id.user_password);
        check_num = (EditText) rootView.findViewById(R.id.check_num);
        get_check_num = (TextView) rootView.findViewById(R.id.get_check_num);
        login_btn = (RelativeLayout) rootView.findViewById(R.id.login_btn);
        login = (TextView) rootView.findViewById(R.id.login);
        login_state = (ImageView) rootView.findViewById(R.id.login_state);
        timeCount = new TimeCount(mContext, get_check_num);

//		xa_neumk/1qaz!QAZ

//		xa_suni /.,mnbv1
//		lgh_810/1qaz!QAZ
//		CDJF0010  /1qaz!QAZ
//		xa_neumk   密码 1qaz!QAZ
//		cdjf0010  1qaz!CDJF
//		zx810 1qaz!QAZ
//		21 sf_cd_cs 1qaz!QAZ

        if (NetConstant.TAG == 2) {
            user_name.setText("CDJF0010");
            user_password.setText("1qaz!QAZ");
        } else {
            user_name.setText("xa_suni");
            user_password.setText("/.,mnbv1");
        }

        login_state_bg = (AnimationDrawable) login_state.getDrawable();

        get_check_num.setOnClickListener(this);
        login_btn.setOnClickListener(this);
    }

    @Override
    protected void conFirm() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.login_btn) {
            changeLoginState(true);
            login();
        } else if (id == R.id.get_check_num) {
            timeCount.start();
            get_check_num.setEnabled(false);
            reqCheckNum();
        }
    }

    private void changeLoginState(boolean isLogin) {
        if (isLogin) {
            login_btn.setClickable(false);
            login_state_bg.start();
            login.setVisibility(View.INVISIBLE);
            login_state.setVisibility(View.VISIBLE);
        } else {
            login_btn.setClickable(true);
            login.setVisibility(View.VISIBLE);
            login_state.setVisibility(View.INVISIBLE);
            login_state_bg.stop();
        }
    }

    // 请求验证码
    private void reqCheckNum() {
        String name = user_name.getText().toString();
        String password = user_password.getText().toString();
        mLoginVm.getCheckNum(name, password);
    }


    // 登陆请求
    private void login() {
        String name = user_name.getText().toString();
        String password = user_password.getText().toString();
        mLoginVm.login(name, password, mCheckCode);
    }

    @Override
    public void resCheckCode(String checkCode) {
        this.mCheckCode = checkCode;
        check_num.setText(checkCode);
        changeLoginState(false);
    }

    @Override
    public void resLoginSuccess(boolean isSuccess) {
        changeLoginState(false);
    }
}
