package com.zhwilson.bitmaphandle;

import android.graphics.Bitmap;
import android.util.LruCache;

public interface BuildInImageCache {
    public void cacheBitmap(String key, Bitmap bitmap);
    public Bitmap getBitmapFromCache(String key);
}
