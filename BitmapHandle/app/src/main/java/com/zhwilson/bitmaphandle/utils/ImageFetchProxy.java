package com.zhwilson.bitmaphandle.utils;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

public class ImageFetchProxy {
    public static void imageFetch(String imageUrl){
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        } catch (Exception e) {
            Log.e("zhwilson", "image fetch error!");
        }

    }
}
