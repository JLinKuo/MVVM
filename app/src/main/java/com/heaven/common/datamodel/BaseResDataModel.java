/** 
 * Project Name:SxPreAccept 
 * File Name:xxx.java
 * Package Name:com.neusoft.preaccept.data.model 
 * Date:2015年11月24日下午4:28:22 
 * Copyright (c) 2015 
 * 
*/

package com.heaven.common.datamodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * ClassName:BaseResDataModel <br/>
 * Function: 响应model的基类. <br/>
 * Date: 2015年11月24日 下午4:28:22 <br/>
 * 
 * @author neusoft liu.hongtao
 * @version
 * @since JDK 1.6
 */
public class BaseResDataModel implements Serializable{
    // 身份id
    public String sessionID;
    //请求状态码
    public String code;
    //请求结果描述
    public String detail;
}
