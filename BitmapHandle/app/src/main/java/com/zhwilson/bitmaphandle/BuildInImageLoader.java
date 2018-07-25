package com.zhwilson.bitmaphandle;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

public class BuildInImageLoader {
    /**
     * 获取合适size的bitmap（一般是放置bitmap的控件的大小）
     * @param resources
     * @param resId
     * @param w 控件/需要的宽度
     * @param h 控件/需要的高度
     * @return
     */
    public static Bitmap handleOptions(Resources resources, int resId, int w, int h){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//设置在decode时只解析图片bounds，而不为图片alloc内存，防止申请内存时导致的oom
        BitmapFactory.decodeResource(resources, resId);

        //进行图片size的缩放
        options.inSampleSize = handleSampleSize(options, w, h);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(resources, resId, options);
    }

    public static Bitmap handleOptions(String path, int w, int h){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//设置在decode时只解析图片bounds，而不为图片alloc内存，防止申请内存时导致的oom
        BitmapFactory.decodeFile(path, options);

        //进行图片size的缩放
        options.inSampleSize = handleSampleSize(options, w, h);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap handleOptions(InputStream in, int w, int h){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//设置在decode时只解析图片bounds，而不为图片alloc内存，防止申请内存时导致的oom
        BitmapFactory.decodeStream(in, null, options);

        //进行图片size的缩放
        options.inSampleSize = handleSampleSize(options, w, h);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeStream(in, null, options);
    }

    private static int handleSampleSize(BitmapFactory.Options options, int w, int h){
        //获取图片的大小
        int bitmapW = options.outWidth;
        int bitmapH = options.outHeight;

        int inSampleSize = 1;
        if (bitmapW > w || bitmapH > h) {
            int halfBitmapW = bitmapW/2;
            int halfBitmapH = bitmapH/2;
            //get the largest inSampleSize which is the power of 2 and keep both width and height of the bitmap larger than the request width and height
            while ((halfBitmapW/inSampleSize) >= w && (halfBitmapH/inSampleSize) >= h) inSampleSize *= 2;
        }

        return inSampleSize;
    }
}
