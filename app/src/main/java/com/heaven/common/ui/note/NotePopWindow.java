/** 
 * Project Name:SxPreAccept_New 
 * File Name:NotePopWindow.java 
 * Package Name:com.neusoft.preaccept.ui.dialog 
 * Date:2016年1月1日下午12:06:34 
 * Copyright (c) 2016 
 * 
*/  
  
 package com.heaven.common.ui.note;


import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.heaven.common.R;

/** 
 * ClassName:NotePopWindow <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2016年1月1日 下午12:06:34 <br/> 
 * @author   neusoft liu.hongtao
 * @version   
 * @since    JDK 1.6    
 */
public class NotePopWindow extends PopupWindow implements OnClickListener{
    private View contentView = null;
    private TextView pop_content = null;
    private Button confirm_btn = null;
    private OnClickListener listener = null;
    public NotePopWindow(Context context) {
        initAttr(context);
        iniView(context);
    }
    public NotePopWindow(Context context, OnClickListener listener) {
        initAttr(context);
        iniView(context);
        this.listener = listener;
    }
    
    private void initAttr(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getRealSize(point);
        this.setWidth(point.x / 2 + 50);  
        this.setHeight(point.y / 4);
        this.setFocusable(true);  
        this.setOutsideTouchable(true);
        // 刷新状态  
        this.update();  
        // 实例化一个ColorDrawable颜色为半透明  
        ColorDrawable dw = new ColorDrawable(0000000000);  
        this.setBackgroundDrawable(dw);  
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);  
        // 设置SelectPicPopupWindow弹出窗体动画效果  
//        this.setAnimationStyle(R.style.AnimationPreview); 
    }
    
    private void iniView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        contentView = inflater.inflate(R.layout.note_popupwindow, null);
        setContentView(contentView);
        pop_content = (TextView) contentView.findViewById(R.id.pop_content);
        confirm_btn = (Button) contentView.findViewById(R.id.confirm_btn);
        confirm_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
        if (this.listener != null) {
            this.listener.onClick(v);
        }
    }
    
    public void showPopupWindow(String content) {  
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            pop_content.setText(content);
            this.showAtLocation(contentView,Gravity.CENTER, 0, 0);
        } else {  
            this.dismiss();  
        }  
    }  
}
 
