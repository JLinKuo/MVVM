package com.heaven.common.http;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;
import com.heaven.common.util.ImageUtils;


public class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    public BitmapLruCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, Bitmap bitmap) {
        return ImageUtils.getBitmapSize(bitmap);
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
