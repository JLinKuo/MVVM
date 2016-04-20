/** 
 * Project Name:SxPreAccept 
 * File Name:TimeCount.java 
 * Package Name:com.neusoft.preaccept.utils 
 * Date:2015年12月18日下午5:47:06 
 * Copyright (c) 2015 
 * 
*/

package com.heaven.common.util;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.heaven.common.R;

/**
 * ClassName:TimeCount <br/>
 * Function: 验证码获取倒计时. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年12月18日 下午5:47:06 <br/>
 * 
 * @author neusoft liu.hongtao
 * @version
 * @since JDK 1.6
 */
public class TimeCount extends CountDownTimer {
    private static long totalTime = 60000;

    private static long countDownInterval = 1000;

    private TextView mCheckCode = null;

    private Context mContext = null;

    public TimeCount(Context context, TextView checkCodeView) {
        super(totalTime, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        mCheckCode = checkCodeView;
        mContext = context;
    }

    @Override
    public void onFinish() {// 计时完毕时触发
        cancel();
        mCheckCode.setTextColor(mContext.getResources().getColor(R.color.white));
        mCheckCode.setText("发送验证码");
        mCheckCode.setEnabled(true);

    }

    @Override
    public void onTick(long millisUntilFinished) {// 计时过程显示
        mCheckCode.setTextColor(mContext.getResources().getColor(R.color.red));
        mCheckCode.setText("重新发送" + "(" + (millisUntilFinished / 1000 + ")"));
    }
    
    //请求成功
    public void success(String code) {
        mCheckCode.setTextColor(mContext.getResources().getColor(R.color.white));
        mCheckCode.setText(code);
        mCheckCode.setEnabled(true);
    }
    
    
}
