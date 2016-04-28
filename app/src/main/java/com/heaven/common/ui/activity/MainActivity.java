package com.heaven.common.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.heaven.common.R;
import com.heaven.common.http.HttpErrorConst;
import com.heaven.common.ui.fragment.Login;
import com.heaven.common.ui.note.NotePopWindow;

import base.core.heaven.baseui.BaseActivity;
import base.core.heaven.manager.SwitchManager;
import base.core.heaven.param.PageParam;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 注册自定义动态广播消息 session失效时用
        IntentFilter filter_dynamic = new IntentFilter();
        filter_dynamic.addAction(HttpErrorConst.SESSION_FAIL_FILTER);
        filter_dynamic.addAction(HttpErrorConst.CHECK_CODE_FAIL_FILTER);
        registerReceiver(SessionFailReceiver, filter_dynamic);

        PageParam param = producePageParam(Login.class);
        SwitchManager.getInstance().showPage(this, param);
    }

    @Override
    protected void initView() {

    }

    private BroadcastReceiver SessionFailReceiver  = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(HttpErrorConst.SESSION_FAIL_FILTER)){
                NotePopWindow.getInstance(MainActivity.this).showPopupWindow(HttpErrorConst.SESSION_FAIL);
            } else if (intent.getAction().equals(HttpErrorConst.CHECK_CODE_FAIL_FILTER)){
                NotePopWindow.getInstance(MainActivity.this).showPopupWindow(HttpErrorConst.CHECK_CODE_FAIL);
            }
        }

    };
}
