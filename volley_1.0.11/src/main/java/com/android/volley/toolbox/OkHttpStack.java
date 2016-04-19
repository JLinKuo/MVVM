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
public class OkHttpStack extends HurlStack {
    private OkHttpClient mOkHttpClent = null;

    public OkHttpStack() {
        this(new OkHttpClient());
    }

    public OkHttpStack(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            throw new NullPointerException("Client must not be null.");
        }
        this.mOkHttpClent = okHttpClient;
    }

    protected HttpURLConnection createConnection(URL url) {
        OkUrlFactory okUrlFactory = new OkUrlFactory(mOkHttpClent);
        return okUrlFactory.open(url);
    }
}
