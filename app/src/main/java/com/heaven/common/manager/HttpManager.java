package com.heaven.common.manager;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.heaven.common.BuildConfig;
import com.heaven.common.MainApp;
import com.heaven.common.R;
import com.heaven.common.datamodel.BaseReqDataModel;
import com.heaven.common.http.BitmapLruCache;
import com.heaven.common.http.HttpExtendRequest;
import com.heaven.common.http.HttpTask;
import com.heaven.common.http.INetListener;
import com.heaven.common.http.NetConstant;
import com.heaven.common.http.OkHttpSSLStack;
import com.heaven.common.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.Hashtable;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by heaven on 2016/4/16.
 */
public class HttpManager {
    private static final String TAG = "HttpManager";
    private static HttpManager sInstance;
    private Map<String,SSLSocketFactory> mSSLMap;

    public RequestQueue mRequestQueue;
    private BitmapLruCache mLruCache;
    private ImageLoader mImageLoader;
    private DiskBasedCache mDiskCache;

    public static HttpManager getInstance() {
        if (sInstance == null) {
            sInstance = new HttpManager();
        }
        return sInstance;
    }

    private HttpManager() {
        Context context = MainApp.getContext();
        int MEM_CACHE_SIZE = 1024 * 1024
                * ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() / 3;
        mLruCache = new BitmapLruCache(MEM_CACHE_SIZE);
        mRequestQueue = newRequestQueue(context.getApplicationContext());
        mImageLoader = new ImageLoader(mRequestQueue, mLruCache);
        mDiskCache = (DiskBasedCache) mRequestQueue.getCache();
    }

    private RequestQueue newRequestQueue(Context context) {
        RequestQueue requestQueue;
//        try {
            String[] hosts = {"kyfw.12306.cn"};
            int[] certRes = {R.raw.kyfw};
            String[] certPass = {"asdfqaz"};
            mSSLMap = new Hashtable<String, SSLSocketFactory>(hosts.length);

            for (int i = 0; i < certRes.length; i++) {
                int res = certRes[i];
                String password = certPass[i];
                SSLSocketFactory sslSocketFactory = null;
                try {
                    sslSocketFactory = createSSLSocketFactory(context, res, password);
                } catch (CertificateException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                } catch (KeyManagementException e) {
                    e.printStackTrace();
                }
                mSSLMap.put(hosts[i], sslSocketFactory);
            }

            HurlStack stack = new OkHttpSSLStack(mSSLMap);

            requestQueue = Volley.newRequestQueue(context, stack);
            requestQueue.start();
//        } catch (KeyStoreException
//                | CertificateException
//                | NoSuchAlgorithmException
//                | KeyManagementException
//                | IOException e) {
//            throw new RuntimeException(e);
//        }
        return requestQueue;
    }

    private SSLSocketFactory createSSLSocketFactory(Context context, int res, String password)
            throws CertificateException,
            NoSuchAlgorithmException,
            IOException,
            KeyStoreException,
            KeyManagementException {
        InputStream inputStream = context.getResources().openRawResource(res);
        KeyStore keyStore = KeyStore.getInstance("BKS");
        keyStore.load(inputStream, password.toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());
        return sslContext.getSocketFactory();
    }

    /**
     * put the request task to request queue
     * @param request http request param
     * @param clazz the response generic
     * @param listener the response listener
     * @param <T> generic
     */
    public <T> void addRequestQueue(BaseReqDataModel request, Class<T> clazz, INetListener listener) {
        HttpTask<T> task = OrganizeRequest(request, clazz, listener);
        if (checkRequestOption(task)) {
            HttpExtendRequest<T> commonRequest = new HttpExtendRequest<T>(task);
            if (request != null) {
                commonRequest.setTag(request.action);
            }
            mRequestQueue.add(commonRequest);
        }
    }

    // 组装请求信息
    private <T> HttpTask<T> OrganizeRequest(BaseReqDataModel request, Class<T> clazz, INetListener listener) {
        HttpTask<T> task = new HttpTask<T>(request);
        task.requestUrl = NetConstant.WEB_HOST + request.action;
        task.listener = listener;
        task.clazz = clazz;
        return task;
    }

    /**
     * check http request option
     * @param task http task
     * @param <T> generic type
     * @return check result
     */
    private <T> boolean checkRequestOption(HttpTask<T> task) {
        boolean isOk = true;
        if (TextUtils.isEmpty(task.requestUrl) || TextUtils.isEmpty(task.reqAction)) {
            isOk = false;
            LogUtil.w(TAG, "please make sure url and action are not null");
        }
        return isOk;
    }

    public void addRequest(Request request, Object tag) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "Add request:" + request.toString());
        }
        if (tag != null) {
            request.setTag(tag);
        }
        mRequestQueue.add(request);
    }

    protected void cancelAll(Object tag) {
        mRequestQueue.cancelAll(tag);
    }

    protected File getCachedImageFile(String url) {
        return mDiskCache.getFileForKey(url);
    }

    protected Bitmap getMemoryBitmap(String key) {
        return mLruCache.get(key);
    }

    protected ImageLoader.ImageContainer loadImage(String requestUrl,
                                                ImageLoader.ImageListener imageListener) {
        return loadImage(requestUrl, imageListener, 0, 0);
    }

    protected ImageLoader.ImageContainer loadImage(String requestUrl,
                                                ImageLoader.ImageListener imageListener,
                                                int maxWidth,
                                                int maxHeight) {

        return mImageLoader.get(requestUrl, imageListener, maxWidth, maxHeight);
    }
}
