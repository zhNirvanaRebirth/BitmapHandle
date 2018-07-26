package com.zhwilson.bitmaphandle;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;

import com.bumptech.glide.disklrucache.DiskLruCache;
import com.zhwilson.bitmaphandle.utils.AppExecutor;

import java.io.File;

public class BuildInImageCacheHandle implements BuildInImageCache {
    private static final String CACHE_DIR = "thumbnails";
    private static final int DISK_CACHE_SIZE = 1024*1024*10;
    private Context context;
    private LruCache<String, Bitmap> bitmapCache = null;
    private DiskLruCache mDiskLruCache;
    private Object diskCacheLock = new Object();
    private boolean diskCacheStarting = true;

    private AppExecutor appExecutor;

    /**
     *
     * @param cacheSize unit:kb
     *                  大小设定：1、系统分配给应用使用内存所剩的可用内存
     *                            2、应用显示一屏bitmap需要的内存以及即将要显示的bitmap需要的内存
     *                            3、bitmap显示的概率，如：部分bitmap显示的概率远远大于其他图片显示的概率，此时，这些图片可能需要单独cache
     *                            4、衡量cache bitmap是数量为主还是质量为主，有时我们可能需要cache大量低质量的图片，而通过其他task下载显示高质量的图片
     *                            5、手机size和density，大的size和density意味着显示图片可能需要更多的内存，因此为其设置的cache也应该更大
     */
    public BuildInImageCacheHandle(Context ctx, int cacheSize) {
        this.context = ctx;
        appExecutor = new AppExecutor();
        bitmapCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount()/1000;
            }
        };
        appExecutor.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                synchronized (diskCacheLock) {
                    try {
                        mDiskLruCache = DiskLruCache.open(getDiskCachePath(context, CACHE_DIR), context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode, Integer.MAX_VALUE, DISK_CACHE_SIZE);
                        diskCacheStarting = false;
                        diskCacheLock.notifyAll();
                    } catch (Exception e) {
                        Log.e("zhwilson", "package name get error!");
                    }
                }
            }
        });
    }
    @Override
    public void cacheBitmap(String key, Bitmap bitmap) {
        if (getBitmapFromCache(key) == null) bitmapCache.put(key, bitmap);

        synchronized (diskCacheLock) {
        }
    }

    @Override
    public Bitmap getBitmapFromCache(String key) {
        if (bitmapCache != null) return bitmapCache.get(key);
        return null;
    }

    private static File getDiskCachePath(Context context, String uniqueName){
        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable() ? context.getApplicationContext().getExternalCacheDir().getPath() : context.getApplicationContext().getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    private Bitmap getBitmapFormDiskCache(String key) {
        synchronized (diskCacheLock) {
            while (diskCacheStarting) {
                try {
                    diskCacheLock.wait();
                } catch (Exception e) {
                    Log.e("zhwilson", "disk cache wait error!");
                }
            }
            try {
//                if (mDiskLruCache != null) return mDiskLruCache.get(key);
            }catch (Exception e) {
                Log.e("zhwilson", "get bitmap from disk cache error!");
            }
        }
        return null;
    }
}
