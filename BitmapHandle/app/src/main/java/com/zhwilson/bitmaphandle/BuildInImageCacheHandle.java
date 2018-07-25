package com.zhwilson.bitmaphandle;

import android.graphics.Bitmap;
import android.util.LruCache;

public class BuildInImageCacheHandle implements BuildInImageCache {
    private LruCache<String, Bitmap> bitmapCache = null;

    /**
     *
     * @param cacheSize unit:kb
     *                  大小设定：1、系统分配给应用使用内存所剩的可用内存
     *                            2、应用显示一屏bitmap需要的内存以及即将要显示的bitmap需要的内存
     *                            3、bitmap显示的概率，如：部分bitmap显示的概率远远大于其他图片显示的概率，此时，这些图片可能需要单独cache
     *                            4、衡量cache bitmap是数量为主还是质量为主，有时我们可能需要cache大量低质量的图片，而通过其他task下载显示高质量的图片
     *                            5、手机size和density，大的size和density意味着显示图片可能需要更多的内存，因此为其设置的cache也应该更大
     */
    public BuildInImageCacheHandle(int cacheSize) {
        bitmapCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount()/1000;
            }
        };
    }
    @Override
    public void cacheBitmap(String key, Bitmap bitmap) {
        if (getBitmapFromCache(key) == null) bitmapCache.put(key, bitmap);
    }

    @Override
    public Bitmap getBitmapFromCache(String key) {
        if (bitmapCache != null) return bitmapCache.get(key);
        return null;
    }
}
