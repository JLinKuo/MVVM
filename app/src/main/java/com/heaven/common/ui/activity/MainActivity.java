package com.heaven.common.ui.activity;

import android.os.Bundle;

import com.heaven.common.R;
import com.heaven.common.ui.fragment.Login;

import base.core.heaven.baseui.BaseActivity;
import base.core.heaven.manager.SwitchManager;
import base.core.heaven.param.PageParam;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PageParam param = producePageParam(Login.class);
        SwitchManager.getInstance().showPage(this, param);
    }

    @Override
    protected void initView() {

    }
}
