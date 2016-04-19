/**
 * Project Name:CommonStruct
 * File Name:PageParam.java
 * Package Name:com.heaven.commonstruct.core.param
 * Date:2016年3月23日上午10:56:35
 * Copyright (c) 2016
 */

package base.core.heaven.param;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * ClassName:PageParam <br/> 
 * Function: 页面属性的封装. <br/> 
 * Reason:   页面跳转必传的参数(相应的属性根据需求自己调整). <br/> 
 * Date:     2016年3月23日 上午10:56:35 <br/> 
 * @author neusoft liu.hongtao
 * @version 1.0.0
 * @since JDK 1.6
 */
public class PageParam implements Parcelable {
    //页面类名
    public String pageName = null;
    //对应类的全名
    public String pageClazz = null;
    //数据传递
    public Bundle data = null;
    //附带的参数
    public String[] param = null;
    //动画类型
    public int[] anim = null;
    //是否开启新的activity
    public boolean isNewActivity = false;
    //是否加入到回退栈中
    public boolean needAddToBackStack = true;
    //是否需要隐藏上一个页面
    public boolean needHide = false;
    //是否创建新实例
    public boolean isNewInstance = false;
    //请求码
    public int requestCode = -1;
    //结果码
    public int resultCode = -1;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pageName);
        dest.writeString(this.pageClazz);
        dest.writeBundle(data);
        dest.writeStringArray(this.param);
        dest.writeIntArray(this.anim);
        dest.writeByte(isNewActivity ? (byte) 1 : (byte) 0);
        dest.writeByte(needAddToBackStack ? (byte) 1 : (byte) 0);
        dest.writeByte(needHide ? (byte) 1 : (byte) 0);
        dest.writeByte(isNewInstance ? (byte) 1 : (byte) 0);
        dest.writeInt(this.requestCode);
        dest.writeInt(this.resultCode);
    }

    public PageParam() {
    }

    protected PageParam(Parcel in) {
        this.pageName = in.readString();
        this.pageClazz = in.readString();
        data = in.readBundle();
        this.param = in.createStringArray();
        this.anim = in.createIntArray();
        this.isNewActivity = in.readByte() != 0;
        this.needAddToBackStack = in.readByte() != 0;
        this.needHide = in.readByte() != 0;
        this.isNewInstance = in.readByte() != 0;
        this.requestCode = in.readInt();
        this.resultCode = in.readInt();
    }

    public static final Creator<PageParam> CREATOR = new Creator<PageParam>() {
        @Override
        public PageParam createFromParcel(Parcel source) {
            return new PageParam(source);
        }

        @Override
        public PageParam[] newArray(int size) {
            return new PageParam[size];
        }
    };
}
