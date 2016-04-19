/** 
 * Project Name:SxPreAccept 
 * File Name:Util.java 
 * Package Name:com.neusoft.preaccept.utils 
 * Date:2015年11月23日下午3:20:31 
 * Copyright (c) 2015 
 * 
*/

package com.heaven.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * ClassName:Util <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年11月23日 下午3:20:31 <br/>
 * 
 * @author neusoft liu.hongtao
 * @version
 * @since JDK 1.6
 */
public class Util {
    private static String preGesture = "gesture_pre";

    // 判断url是否有中文名并进行转化处理
    public static String checkUrl(String url) {
        if (isContainsChinese(url)) {
            String docname = url.substring(url.lastIndexOf("/") + 1);
            String domains = url.substring(0, url.lastIndexOf("/") + 1);

            try {
                url = domains + URLEncoder.encode(docname, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    /**
     * 判断字符串是否包含中文
     * 
     * @param str
     * @return
     */
    public static boolean isContainsChinese(String str) {
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find()) {
            flg = true;
        }
        return flg;
    }

    // 得到本机ip地址
    public static String getLocalHostIp() {
        String ipaddress = "";
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            // 遍历所用的网络接口
            while (en.hasMoreElements()) {
                NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
                Enumeration<InetAddress> inet = nif.getInetAddresses();
                // 遍历每一个接口绑定的所有ip
                while (inet.hasMoreElements()) {
                    InetAddress ip = inet.nextElement();
                    if (!ip.isLoopbackAddress()/*
                                                * &&
                                                * InetAddressUtils.isIPv4Address
                                                * (ip.getHostAddress())
                                                */) {
                        return ipaddress = ip.getHostAddress();
                    }
                }

            }
        } catch (SocketException e) {
            Log.e("feige", "获取本地ip地址失败");
            e.printStackTrace();
        }
        return ipaddress;

    }

    // 得到本机Mac地址
    public String getLocalMac(Context context) {
        String mac = "";
        // 获取wifi管理器
        WifiManager wifiMng = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfor = wifiMng.getConnectionInfo();
        mac = "本机的mac地址是：" + wifiInfor.getMacAddress();
        return mac;
    }

    // 取得屏幕宽度
    public final static int getWindowsWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static void encodeString() {
        Properties initProp = new Properties(System.getProperties());
        System.out.println("file.encoding:" + initProp.getProperty("file.encoding"));
    }

    public static enum FilterType {
        RANDOM("01", "随机"), NUM_BETWEEN("02", "号段"), NUM_KEY("03", "号码关键字"), NUM_NICE("04", "靓号等级"), NUM_PRE_FEE("05",
                "预付费产品编码"), NUM_RANGE("06", "查询号码范围");
        public String ID = "";

        public String NAME = "";

        FilterType(String id, String name) {
            this.ID = id;
            this.NAME = name;
        }
    }

    public static String getStorePath(Context context, String path) {

        // 获取SdCard状态

        String state = Environment.getExternalStorageState();

        // 判断SdCard是否存在并且是可用的

        if (Environment.MEDIA_MOUNTED.equals(state)) {

            if (Environment.getExternalStorageDirectory().canWrite()) {

                File file = new File(Environment.getExternalStorageDirectory().getPath() + path);
                if (!file.exists()) {
                    file.mkdirs();
                }
                return file.getAbsolutePath();

            }

        }

        return context.getFilesDir().getAbsolutePath();

    }

    public static String getRandomOrdersId() {
        String orderIds = "";
        StringBuilder builder = new StringBuilder();
        Random random = new Random();// 指定种子数字
        for (int i = 0; i < 3; i++) {
            builder.append(String.valueOf(random.nextInt(9)));
        }

        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String retStrFormatNowDate = sdFormatter.format(nowTime);
        orderIds = retStrFormatNowDate + builder.toString();
        return orderIds;
    }

    public static String getOccupyTime() {
//      Date nowTime = new Date(System.currentTimeMillis());
      Calendar  calendar = Calendar.getInstance();
      calendar.add(Calendar.MINUTE, 30);
      Date nowTime = calendar.getTime();
      SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
      String occupyTime = sdFormatter.format(nowTime);
      return occupyTime;
  }

    public static void saveGesturePwd(Context context, boolean isCreat) {
        SharedPreferences pre = context.getSharedPreferences(preGesture, Context.MODE_PRIVATE);
        Editor edit = pre.edit();
        edit.putBoolean("isCreatGesture", isCreat);
        edit.commit();
    }

    public static boolean getGestureFlag(Context context) {
        SharedPreferences pre = context.getSharedPreferences(preGesture, Context.MODE_PRIVATE);
        boolean isCreat = pre.getBoolean("isCreatGesture", false);
        return isCreat;
    }

    // 返回数据保存到文件, 调试测试数据用,后期删除
    public static void saveData(int type, byte[] data) {
        try {
            // Context context = MainApp.getContext();
            // FileOutputStream outputStream
            // =context.openFileOutput("login.txt", Context.MODE_PRIVATE);
            // outputStream.write(data);
            String responseData = "";
            try {
                responseData = new String(data,
                        "UTF-8"/*
                                * HttpHeaderParser.parseCharset(response.
                                * headers)
                                */);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            LogUtil.v("Heaven", "response---" + responseData);
            FileOutputStream fileOut = null;
            String path = Environment.getExternalStorageDirectory().getPath() + "/SxAcceptSystem/";
            File dir = new File(path);
            if (!(dir.exists() && dir.isDirectory())) {
                dir.mkdir();
            }
            if (dir.exists() && dir.isDirectory()) {
                File fileLog = null;
                if (type == 0) {
                    fileLog = new File(path, "respone.txt");
                } else {
                    fileLog = new File(path, "request.txt");
                }
                if (!(fileLog.exists() && fileLog.isFile())) {
                    fileLog.createNewFile();
                }
                fileOut = new FileOutputStream(fileLog);
                fileOut.write(data);
                fileOut.flush();
                fileOut.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 返回数据保存到文件, 调试测试数据用,后期删除
    public static void saveData(InputStream in) {
        try {
            // Context context = MainApp.getContext();
            // FileOutputStream outputStream
            // =context.openFileOutput("login.txt", Context.MODE_PRIVATE);
            // outputStream.write(data);
            byte[] res = new byte[4 * 1024];
            in.read(res, 0, 1024);
            String responseData = new String(res,
                    "UTF-8"/* HttpHeaderParser.parseCharset(response.headers) */);
            FileOutputStream fileOut = null;
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SxAcceptSystem/";
            File dir = new File(path);
            if (!(dir.exists() && dir.isDirectory())) {
                dir.mkdir();
            }
            if (dir.exists() && dir.isDirectory()) {
                File fileLog = null;
                fileLog = new File(path, "preOpenAccountRespone.txt");
                if (!(fileLog.exists() && fileLog.isFile())) {
                    fileLog.createNewFile();
                }
                fileOut = new FileOutputStream(fileLog);
                // fileOut.write(data);
                fileOut.flush();
                fileOut.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String urlEncoder(String param) {
        String str = param;
        try {
            str = URLEncoder.encode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    // 数据有效检查
    public static boolean checkValidValue(String value) {
        boolean isValid = true;
        if (value == null || "".equals(value)) {
            isValid = false;
        }
        return isValid;
    }
    
 // 数据有效检查
    public static boolean checkValidValue(String value1, String value2) {
        boolean isValid = false;
        if (value1 != null && value2 != null) {
            String tempValue1 = value1.trim();
            String tempValue2 = value2.trim();
            if (tempValue1.equals(tempValue2)) {
                isValid = true;
            }
        }
        return isValid;
    }

    // 流量类型判断
    public static int judge4GflowType() {
        int type = -1;
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        if (day >= 1 && day <= 15) {
            type = 0;
        } else if (day >= 16 && day <= 25) {
            type = 1;
        } else {
            type = 2;
        }
        return type;
    }

    public static Bitmap Bytes2Bimap(String src) {
        Bitmap bitmap = null;
        if (src != null) {
            byte[] key=Base64.decode(src.getBytes(), Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(key, 0, key.length);
        }
        return bitmap;
    }
}
