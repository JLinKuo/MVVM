package com.heaven.common.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heaven.common.R;

import base.core.heaven.baseui.BaseFragment;
import base.core.heaven.manager.SwitchManager;
import base.core.heaven.note.NotePopWindow;
import base.core.heaven.param.PageParam;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseSubFragment extends BaseFragment {
    private static final int SESSION_FAIL = 1;
    private static final int CHECK_CODE_FAIL = 2;
    private static final int PROTOCOL_ANALYZE_FAIL = 3;
    private static final int OTHER_FAIL = 4;

    //处理网络请求相关异常
    private int mErrorType = -1;

    protected void onNetErrorResponse(int errorType, String errorMessage) {
        mErrorType = errorType;
        NotePopWindow note = NotePopWindow.getInstance(mContext);
        switch (errorType) {
            case SESSION_FAIL:
                note.setOnClickListener(errorListener);
                errorMessage = mContext.getResources().getString(base.core.heaven.main.R.string.session_fail);
                break;
            case CHECK_CODE_FAIL:
                note.setOnClickListener(errorListener);
                errorMessage = mContext.getResources().getString(base.core.heaven.main.R.string.check_code_fail);
                break;
            case PROTOCOL_ANALYZE_FAIL:
                note.setOnClickListener(null);
                errorMessage =  mContext.getResources().getString(base.core.heaven.main.R.string.protocol_analyze_fail);
                break;
            case OTHER_FAIL:
                note.setOnClickListener(null);
                break;
        }
        note.showPopupWindow(errorMessage);
    }

    private View.OnClickListener errorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PageParam param = producePageParam(Login.class);
            if (mErrorType == SESSION_FAIL) {
                SwitchManager.getInstance().showPage(mActivity, param);
            } else if (mErrorType == CHECK_CODE_FAIL) {
                SwitchManager.getInstance().showPage(mActivity, param);
            }
        }
    };
}
