/**
 * Project Name:CommonStruct
 * File Name:IOnPageBackResult.java
 * Package Name:com.heaven.commonstruct.core
 * Date:2016年3月29日下午3:37:25
 * Copyright (c) 2016
 */

package base.core.heaven.Interface;

import android.content.Intent;

/**
 * ClassName:IOnPageBackResult <br/> 
 * Function: 返回数据监听,当需要返回数据而启动的一个界面时用到. <br/> 
 * Reason:   回调返回数据. <br/> 
 * Date:     2016年3月29日 下午3:37:25 <br/> 
 * @author neusoft liu.hongtao
 * @version
 * @since JDK 1.6
 */
public interface IOnPageBackResult {
    void onPageBackResult(int requestCode, int resultCode, Intent data);

}
