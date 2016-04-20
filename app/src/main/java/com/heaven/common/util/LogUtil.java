package com.heaven.common.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.Gravity;
import android.widget.Toast;

import com.heaven.common.MainApp;

import base.core.heaven.param.AppInfo;

public class LogUtil {
    // log日志路径
    public static final String LOG_PATH = "/SxPreAccept/log/";

    // 日志 文件名
    public static final String LOG_FILE = "exception.log.txt";

    /** 网络返回的调试开关，服务端控制 */
    private static boolean forDebugfromNetwork = true;

    /**
     * 本地写死的调试开关,在发布时关掉
     */
    public static boolean hardDebugFlag = true;

    /**
     * 内测开关，在启动时会有登录框档在前面,内测版本打开
     */
    public static boolean bInnerTestFlag = false;

    /** 是否在软件列表里显示自已本身 */
    public static final boolean SHOWSELF = false;

    public static final int sShouldShowToastLevel = 0;

    public static final String TAGOPT = "TAG_FOR_OPTIMIZE";

    public static final String NET_LOG = "net_log_";

    public static final String SERVER_ERROR_LOG = "serv_error_log_";

    private Callback cb = null;

    private static Handler uiHandler = null;

    private static final int MSG_PROCOTOL_ERROR = 77;

    private static final int MSG_DEBUG_TOAST = 78;

    private static LogUtil instance = null;

    public static LogUtil getInstance() {
        if (instance == null) {
            instance = new LogUtil();
        }
        return instance;
    }

    public static boolean isForDebug() {
        if (hardDebugFlag) {
            return hardDebugFlag;
        } else {
            return forDebugfromNetwork;
        }
    }

    public static void v(String t, String m) {
        if (isForDebug()) {
            if (m == null) {
                m = "............";
            }
            android.util.Log.v(t, m);
        }
    }

    public static void e(String t, String m) {
        if (isForDebug()) {
            android.util.Log.e(t, m);
        }
    }

    public static void w(String m) {
        w("", m);
        v("", m);
    }

    public static void w(String t, String m) {
        if (isForDebug()) {
            v(t, m);
            try {
                AppInfo appInfo = MainApp.getAppConfig();
                // 创建崩溃日志
                BufferedWriter bos = new BufferedWriter(new FileWriter(Util.getStorePath(MainApp.getContext(), LOG_PATH)
                        + NET_LOG + new Date(System.currentTimeMillis()).getDate() + ".txt", true));
                bos.write("\t\n" + t + "**********************\t\n");
                if (appInfo != null) {
                    bos.write("APP_VERSION:" + appInfo.verCode + "\t\n");
                    bos.write("PHONE_MODEL:" + appInfo.model + "\t\n");
                    bos.write("ANDROID_VERSION:" + appInfo.release + ":" + appInfo.sdk + "\t\n");
                }
                android.text.format.Time tmtxt = new android.text.format.Time();
                tmtxt.setToNow();
                bos.write(tmtxt.format("%Y-%m-%d %H:%M:%S") + "\n");
                bos.write(m);
                bos.write("\t\n");
                bos.flush();
                bos.close();
            } catch (Exception ebos) {
                // ebos.printStackTrace();
            }
        }
    }

    private static void s(String t, String m) {
        if (isForDebug()) {
             v(t, m);
            try {
                // create crash log append into
                // /data/data/com.tencent.qq/exception.stack
                BufferedWriter bos = new BufferedWriter(new FileWriter(Util.getStorePath(MainApp.getContext(), LOG_PATH)
                        + "/" + "file_list" + new Date(System.currentTimeMillis()).getDate() + ".txt", true));
                bos.write("\t\n" + t + "**********************\t\n");
                // // bos.write("APP_VERSION:" + ExceptionHandler. G.APP_VERSION
                // + "\t\n");
                // // bos.write("PHONE_MODEL:" + ExceptionHandler. G.PHONE_MODEL
                // + "\t\n");
                // // bos.write("ANDROID_VERSION:" + ExceptionHandler.
                // G.ANDROID_VERSION + "\t\n");
                android.text.format.Time tmtxt = new android.text.format.Time();
                tmtxt.setToNow();
                bos.write(tmtxt.format("%Y-%m-%d %H:%M:%S") + "\n");
                bos.write(m);
                bos.write("\t\n");
                bos.flush();
                bos.close();
            } catch (Exception ebos) {
                // ebos.printStackTrace();
            }
        }
    }

    private static void protocolError(String url, String funName, String s, final String resHeader, int cmd) {
        if (isForDebug()) {
            url = (url == null ? "" : url);
            funName = (funName == null ? "" : funName);
            String alert = s + "\n" + "[server]" + "\n" + "url:" + url + "\n" + "funName:" + funName + "\n" +
                    // " svrcode:" + (resHeader == null ? "" :
                    // resHeader.svrcode) + "\n"
                    // + " busicode:" + (resHeader == null ? "" :
                    // resHeader.busicode) + "\n"
                    // + " msg:" + (resHeader == null ? "" : resHeader.msg) +
                    // "\n" +
            "[client] cmd:" + cmd + "\n";

            if (uiHandler != null) {
                Message msg = Message.obtain();
                msg.what = MSG_PROCOTOL_ERROR;
                msg.obj = alert;
                uiHandler.sendMessage(msg);
            }

            try {
                BufferedWriter bos = new BufferedWriter(new FileWriter(Util.getStorePath(MainApp.getContext(), LOG_PATH)
                        + "/" + SERVER_ERROR_LOG + new Date(System.currentTimeMillis()).getDate() + ".txt", true));
                bos.write("\t\n**********************\t\n");
                android.text.format.Time tmtxt = new android.text.format.Time();
                tmtxt.setToNow();
                bos.write(tmtxt.format("%Y-%m-%d %H:%M:%S") + "\n");
                bos.write(alert);
                bos.write("\t\n");
                bos.flush();
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void debugToast(String text) {
        if (isForDebug()) {
            if (uiHandler != null) {
                Message msg = Message.obtain();
                msg.what = MSG_DEBUG_TOAST;
                msg.obj = text;
                uiHandler.sendMessage(msg);
            }
        }
    }

    public void createCallback() {
        if (isForDebug()) {
            if (cb == null) {
                cb = new Callback() {

                    @Override
                    public boolean handleMessage(Message msg) {
                        switch (msg.what) {
                        case MSG_PROCOTOL_ERROR: {
                            String alert = (String) msg.obj;
                            Toast t = Toast.makeText(MainApp.getContext(), "/log/serv_error_log_.txt \n\n" + alert,
                                    Toast.LENGTH_LONG);
                            t.setGravity(Gravity.CENTER, 0, 0);
                            t.show();
                        }
                            break;
                        case MSG_DEBUG_TOAST: {
                            String alert = (String) msg.obj;
                            Toast t = Toast.makeText(MainApp.getContext(), alert, Toast.LENGTH_LONG);
                            t.setGravity(Gravity.CENTER, 0, 0);
                            t.show();
                        }
                            break;
                        default:
                            break;
                        }
                        return true;
                    }
                };

                uiHandler = new Handler(cb);
            }
        }
    }

    public void destory() {
        cb = null;
        uiHandler = null;
        instance = null;
    }

    public static void setForDebug(boolean forDebug) {
        LogUtil.forDebugfromNetwork = forDebug;
    }

    private static long t1;

    private static long t2;

    public static void start() {
        t1 = System.currentTimeMillis();
    }

    public static void endPrint(String tag) {
        t2 = System.currentTimeMillis();
        LogUtil.v("time", tag + " " + (t2 - t1));
    }

    class PrintStruct {
        String s = "";

        Long time = null;
    }

    private static HashMap<String, ArrayList<String>> useTimeStringList = new HashMap<String, ArrayList<String>>();

    private static HashMap<String, ArrayList<Long>> useTimeLongList = new HashMap<String, ArrayList<Long>>();

    public static void time(String logPoint) {
        time("UseTime", logPoint, false);
    }

    public static void time(String logPoint, boolean print) {
        time("UseTime", logPoint, print);
    }

    public static void time(String tag, String logPoint) {
        time(tag, logPoint, false);
    }

    public static void time(String tag, String logPoint, boolean print) {
        if (!isForDebug()) {
            return;
        }
        // Log.v(tag, logPoint);

        ArrayList<String> sList = useTimeStringList.get(tag);
        if (sList == null) {
            sList = new ArrayList<String>();
            useTimeStringList.put(tag, sList);
        }
        sList.add(logPoint);

        ArrayList<Long> lList = useTimeLongList.get(tag);
        if (lList == null) {
            lList = new ArrayList<Long>();
            useTimeLongList.put(tag, lList);
        }
        lList.add(System.currentTimeMillis());

        if (print) {
            StringBuffer sb = new StringBuffer();
            long lastT = lList.get(0);
            sb.append("total time:");
            sb.append(lList.get(lList.size() - 1) - lastT);
            sb.append(" ");
            for (int i = 0; i < sList.size(); i++) {
                sb.append(lList.get(i) - lastT);
                lastT = lList.get(i);
                sb.append(" ");
                sb.append(sList.get(i));
                sb.append(" ");
            }
            LogUtil.v(tag, sb.toString());

            sList.clear();
            lList.clear();
        }
    }
}
