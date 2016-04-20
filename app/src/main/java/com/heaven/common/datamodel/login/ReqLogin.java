/** 
 * Project Name:SxPreAccept 
 * File Name:LoginRequest.java 
 * Package Name:com.neusoft.preaccept.data.model.baseRequest 
 * Date:2015年11月24日下午5:05:25 
 * Copyright (c) 2015 
 * 
*/  
  
 package com.heaven.common.datamodel.login;

import com.heaven.common.datamodel.BaseReqDataModel;

/**
 * ClassName:LoginRequest <br/> 
 * Function: 登陆请求模型. <br/>
 * Date:     2015年11月24日 下午5:05:25 <br/>
 * @author   neusoft liu.hongtao
 * @version   
 * @since    JDK 1.6    
 */
public class ReqLogin extends BaseReqDataModel {
    public String username = null;
    public String password = null;
    public String checkCode = null;
    public String req = null;
}
 
