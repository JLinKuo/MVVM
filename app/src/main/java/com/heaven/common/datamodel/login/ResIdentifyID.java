/** 
 * Project Name:SxPreAccept 
 * File Name:ResponseIdentifyID.java 
 * Package Name:com.neusoft.preaccept.data.model.baseRequest 
 * Date:2015年11月24日下午5:08:59 
 * Copyright (c) 2015 
 * 
*/

package com.heaven.common.datamodel.login;


import com.heaven.common.datamodel.BaseReqDataModel;
import com.heaven.common.datamodel.BaseResDataModel;

/**
 * ClassName:ResponseIdentifyID <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年11月24日 下午5:08:59 <br/>
 * 
 * @author neusoft liu.hongtao
 * @version
 * @since JDK 1.6
 */
public class ResIdentifyID extends BaseResDataModel {
    // 验证码
    public String checkCode;

    // 生成时间
    public String date;
}
