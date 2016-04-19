/**
 * Project Name:JxAir_heaven
 * File Name:MobileConstant.java
 * Package Name:com.neusoft.jxair.constants
 * Date:2015年10月19日下午6:03:08
 * Copyright (c) 2015
 * <p>
 * <p>
 * Project Name:JxAir_heaven
 * File Name:MobileConstant.java
 * Package Name:com.neusoft.jxair.constants
 * Date:2015年10月19日下午6:03:08
 * Copyright (c) 2015
 */
/**
 * Project Name:JxAir_heaven 
 * File Name:MobileConstant.java 
 * Package Name:com.neusoft.jxair.constants 
 * Date:2015年10月19日下午6:03:08 
 * Copyright (c) 2015 
 *
 */

package base.core.heaven.param;

/**
 * ClassName:AppConfig <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年10月19日 下午6:03:08 <br/>
 *
 * @author neusoft liu.hongtao
 * @version
 * @since JDK 1.6
 */
public class AppInfo {

    public static int isCheckUp = 0;
    public static int isReset = 0;
    public static int isCheng = 0;
    public static int isCreat = 0;

    public static int isForgot = -1;

    public static final String APP_ID = "5";
    public String APP_IP = "127.0.0.1";
    public static final String DEVICE_TYPE = "android";
    // 移动设备相关属性
    public String model = android.os.Build.MODEL;// 手机型号
    public int sdk = android.os.Build.VERSION.SDK_INT;// SDK版本
    public String release = android.os.Build.VERSION.RELEASE;// 系统版本

    // 应用相关的属性
    public String packageName = "";
    public int verCode = 0;
    public String verName = "";
    public String name = "";

    public static int b = 0;
    public static int e = 10;

    public static int iscreat = 0;
}
