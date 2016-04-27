package com.heaven.common.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heaven.common.R;
import com.heaven.common.http.ErrorMessage;
import com.heaven.common.http.VelloyHttpError;

import base.core.heaven.baseui.BaseFragment;
import base.core.heaven.manager.SwitchManager;
import base.core.heaven.note.NotePopWindow;
import base.core.heaven.param.PageParam;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseSubFragment extends BaseFragment {

    //处理网络请求相关异常
    private int mErrorType = -1;

    /**
     * show the error message, if the error type is SESSION_ERROR or CHECK_CODE_ERROR
     * will trigger the login view
     * @param error error message
     */
    protected void onNetErrorResponse(ErrorMessage error) {
        if (error != null) {
            NotePopWindow note = NotePopWindow.getInstance(mContext);
            mErrorType = error.mErrorType;
            switch (error.mErrorType) {
                case VelloyHttpError.SESSION_ERROR:
                case VelloyHttpError.CHECK_CODE_ERROR:
                    mErrorType = VelloyHttpError.SESSION_ERROR;
                    note.setOnClickListener(errorListener);
                    break;
                default:
                    mErrorType = -1;
                    note.setOnClickListener(null);
            }
            note.showPopupWindow(error.mErrorDetail);
        }
    }

    private View.OnClickListener errorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PageParam param = producePageParam(Login.class);
            if (mErrorType == VelloyHttpError.SESSION_ERROR) {
                SwitchManager.getInstance().showPage(mActivity, param);
            }
        }
    };
}
