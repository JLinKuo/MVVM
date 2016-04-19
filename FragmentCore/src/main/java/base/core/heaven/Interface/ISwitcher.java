/**
 * Project Name:CommonStruct
 * File Name:ISwitcher.java
 * Package Name:com.heaven.commonstruct.core
 * Date:2016年3月23日下午1:27:19
 * Copyright (c) 2016
 */

package base.core.heaven.Interface;

import base.core.heaven.baseui.BaseFragment;
import base.core.heaven.param.PageParam;

/**
 * ClassName:ISwitcher <br/>
 * Function: 页面跳转接口,回调. <br/>
 * Reason:   页面转换接口. <br/>
 * Date:     2016年3月23日 下午1:27:19 <br/>
 *
 * @author neusoft liu.hongtao
 * @since JDK 1.6
 */
public interface ISwitcher {
    //回退到上一个页面

    /**
     * popPage:(回退到上一个页面). <br/>
     *
     * @author neusoft
     * @since JDK 1.6
     */
    void popPage();

    /**
     * isTop:(是否在栈顶). <br/>
     *
     * @param fgName
     * @return
     * @author neusoft
     * @since JDK 1.6
     */
    boolean isTop(String fgName);

    /**
     * findPage:(查询页面). <br/>
     *
     * @param pageName
     * @return
     * @author neusoft
     * @since JDK 1.6
     */
    boolean findPage(final String pageName);

    /**
     * goPage:(跳转界面). <br/>
     *
     * @param parm
     * @author neusoft
     * @since JDK 1.6
     */
    void showPage(PageParam parm);

    /**
     * startPageForResult:(开启一个页面并返回数据). <br/>
     *
     * @param parm
     * @param result
     * @author neusoft
     * @since JDK 1.6
     */
    void startPageForResult(final PageParam parm, BaseFragment result);
}
 
