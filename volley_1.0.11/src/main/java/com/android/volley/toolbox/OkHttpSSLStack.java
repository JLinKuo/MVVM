/**
 * Project Name:Volley
 * File Name:OkHttpStack.java
 * Package Name:com.android.volley.toolbox
 * Date:2016年4月7日下午2:33:34
 * Copyright (c) 2016
 */

package com.android.volley.toolbox;




import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/**
 * ClassName:OkHttpStack <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年4月7日 下午2:33:34 <br/>
 *
 * @author neusoft liu.hongtao
 * @version
 * @since JDK 1.6
 */
public class OkHttpSSLStack extends HurlStack {
    private OkHttpClient mOkHttpClent = null;

    private Map<String,SSLSocketFactory> mSSLSocketFactoryMap;

    public OkHttpSSLStack(Map<String,SSLSocketFactory> factoryMap) {
        this(new OkHttpClient(),factoryMap);
    }

    public OkHttpSSLStack(OkHttpClient okHttpClient,Map<String,SSLSocketFactory> factoryMap) {
        if (okHttpClient == null) {
            throw new NullPointerException("Client must not be null.");
        }
        this.mOkHttpClent = okHttpClient;
        this.mSSLSocketFactoryMap = factoryMap;
    }

    protected HttpURLConnection createConnection(URL url) {
        HttpURLConnection connection = new OkUrlFactory(mOkHttpClent).open(url);
        if ("https".equals(url.getProtocol()) && mSSLSocketFactoryMap.containsKey(url.getHost())) {
            ((HttpsURLConnection)connection).setSSLSocketFactory(mSSLSocketFactoryMap.get(url.getHost()));
        }
        return connection;
    }
}
